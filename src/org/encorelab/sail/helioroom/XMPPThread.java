/*
 * Created Jun 15, 2010
 */
package org.encorelab.sail.helioroom;

import org.encorelab.sail.Event;
import org.encorelab.sail.EventListener;
import org.encorelab.sail.EventResponder;
import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.ConnectionConfiguration.SecurityMode;
import org.jivesoftware.smack.Roster;
import org.jivesoftware.smack.RosterEntry;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.filter.PacketTypeFilter;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.packet.Presence;

import android.content.Context;
import android.util.Log;

/**
 * Manages all the XMPP connections, incoming and outgoing messages. 
 * This class is a singleton meaning there is only one instance per phenomenaPod.
 *
 * @author Gugo
 */
public class XMPPThread extends Thread {
	private Context app;
	
	// Connection
	protected XMPPConnection connection = null;
	private static final String TAG = "HelioRoom";
	private Roster roster = null;
	// Packet collector
	
	// Credentials
	private String myId = null;
	private String password = null;
	private String resource = null;
	// Chat room credentials
	private String conferenceRoom = null;
	private String conferenceUsername = null;
	private String conferenceJid = null;
	// Data
	private XMPPThreadObserver data = null;

	public XMPPThread(String id, String password, String resource, String conference, String confId) {
		XMPPConnection.DEBUG_ENABLED = true;
		this.myId = id;
		this.password = password;
		this.resource = resource;
		this.conferenceRoom = conference;
		this.conferenceUsername = confId;
		this.conferenceJid = this.conferenceRoom+"/"+this.conferenceUsername+"-"+this.getId();
	}
	
	
	@Override
	public void run() {
		connect();
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
		config.setSASLAuthenticationEnabled(true);
		config.setSecurityMode(SecurityMode.disabled);
		connection = new XMPPConnection(config);
		
		try {
			// Connect
			Log.d(TAG, "Connecting to XMPP...");
			connection.connect();
			connection.login(myId, password, resource);
		} catch (XMPPException e) {
			Log.e(TAG, "Impossible to connect to the XMPP server.");
			connection = null;
			return false;
		}
		
		
		joinGroupChat();
		sendGroupChat("Here!");


		roster = connection.getRoster();
		return true;
	}


	/**
	 * Disconnects a particular phenomena instance.
	 * 
	 * @param myId Unique identifier of the phenomena.
	 */
	public boolean disconnect() {
		if (connection==null) {
			return false;
		}
		connection.disconnect();
		connection = null;
		return true;
	}
	
	
	public void joinGroupChat() {
		Presence p = new Presence(Presence.Type.available);
		p.setStatus("chat");
		p.setTo(this.conferenceJid);
		connection.sendPacket(p);
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
		if (connection==null || !isConnected()){
			System.err.println("Impossible to send message: this device isn't connected anymore!");
			return;
		}
		
		Message m = new Message(message, Message.Type.groupchat);
		m.setTo(this.conferenceRoom);
		m.setBody(message);
		connection.sendPacket(m);


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
