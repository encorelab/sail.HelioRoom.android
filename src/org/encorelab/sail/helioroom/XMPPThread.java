/*
 * Created Jun 15, 2010
 */
package org.encorelab.sail.helioroom;

import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.ConnectionConfiguration.SecurityMode;
import org.jivesoftware.smack.PacketCollector;
import org.jivesoftware.smack.Roster;
import org.jivesoftware.smack.RosterEntry;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smackx.muc.MultiUserChat;

/**
 * Manages all the XMPP connections, incoming and outgoing messages. 
 * This class is a singleton meaning there is only one instance per phenomenaPod.
 *
 * @author Gugo
 */
public class XMPPThread extends Thread {
	// Connection
	protected XMPPConnection connection = null;
	private Roster roster = null;
	// Packet collector
	private PacketCollector pc = null;
	// Credentials
	private String myId = null;
	private String password = null;
	private String resource = null;
	// Chat room credentials
	private String conferenceRoom = null;
	private String conferenceId = null;
	// Data
	private XMPPThreadObserver data = null;
	private MultiUserChat room = null;
	

	public XMPPThread(String id, String password, String resource, String conference, String confId) {
		XMPPConnection.DEBUG_ENABLED=true;
		this.myId = id;
		this.password = password;
		this.resource = resource;
		this.conferenceRoom = conference;
		this.conferenceId = confId;
	}
	
	
	@Override
	public void run() {
		if (connect()) {
			listen();
		}
	}
	

	/**
	 * Creates a new XMPP connection associated to a particular phenomena.
	 * The default resource identifier is used.
	 *
	 * @param username It's the unique identifier of the phenomena
	 * @param password 
	 */
	public boolean connect() {
		if (connection!=null) {
			return false;
		}
		ConnectionConfiguration config = 
			new ConnectionConfiguration("proto.encorelab.org", Integer.parseInt("5222"));
		//config.setSASLAuthenticationEnabled(false);
		config.setSecurityMode(SecurityMode.disabled);
		connection = new XMPPConnection(config);
		try {
			// Connect
			connection.connect();
			connection.login(myId, password, resource);
		} catch (XMPPException e) {
			System.err.println("Impossible to connect to the XMPP server.");
			connection = null;
			return false;
		}
		
		room = new MultiUserChat(connection, conferenceRoom);
		try {
			room.join(conferenceId);				//might this be useful for group id?
		} catch (XMPPException e) {
			e.printStackTrace();
			return false;
		}

		roster = connection.getRoster();
		return true;
	}


	/**
	 * Disconnects a particular phenomena instance.
	 * 
	 * @param myId Unique identifier of the phenomena.
	 */
	public boolean disconnect() {
		if (room!=null) {
			room.leave();
		}
		
		if (connection==null) {
			return false;
		}
		connection.disconnect();
		connection=null;
		return true;
	}
	
	
	public void listen() {
		pc = connection.createPacketCollector(null);
		String currentPacket = null;
		while(!Thread.currentThread().isInterrupted()) {
			currentPacket = pc.nextResult().toXML();
			if(currentPacket!=null && data!=null)
				data.parse(currentPacket);
		}
	}


	/**
	 * Used to send a message to a particular client.
	 *
	 * @param dest JID of the client that needs to receive the message 
	 * @param message Message to be sent
	 */
	public void sendTo(String dest, String message) {
		if (connection==null || !isConnected()){
			System.err.println("Impossible to send message: this device isn't connected anymore!");
			return;
		}
		Message m = new Message();
		m.setFrom(myId);
		m.setTo(dest);
		m.setBody(message);
		connection.sendPacket(m);
	}
	

	/**
	 * Used to send a message to the room.
	 *
	 * @param dest JID of the client that needs to receive the message 
	 * @param message Message to be sent
	 */
	public void sendGroupChat(String message) {
		if (room==null || !isConnected()){
			System.err.println("Impossible to send message: this device isn't connected anymore!");
			return;
		}
		try {
			room.sendMessage(message);
		}
		catch(XMPPException e) {
			e.printStackTrace();
		}
//		Message m = new Message();
//		m.setFrom(myId);
//		m.setTo(dest);
//		m.setBody(message);
//		m.setType(Message.Type.groupchat);   
//		connection.sendPacket(m);
		//room.sendMessage(m);
	}

	
	/**
	 * Used to broadcast a message to all contacts in the roster
	 *
	 * @param message
	 */
	public void broadcast(String message) {
		if (connection==null || !isConnected()){
			System.err.println("Impossible to send message: this device isn't connected anymore!");
			return;
		}
		roster = connection.getRoster();
		if (roster == null) {
			System.err.println("Impossible to broadcast message: roaster is null!");
			return;
		}
		Message m = new Message();
		m.setFrom(myId);
		m.setBody(message);
		for (RosterEntry re : roster.getEntries()) {
			m.setTo(re.getUser());
			connection.sendPacket(m);
		}
	}
	
	
	public boolean isConnected() {
		return connection.isConnected() && connection.isAuthenticated();
	}


	public void addObserver(XMPPThreadObserver hr) {
		this.data  = hr;
	}
}
