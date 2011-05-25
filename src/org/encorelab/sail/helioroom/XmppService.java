/**
 *
 */
package org.encorelab.sail.helioroom;

import org.jivesoftware.smack.XMPPConnection;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

/**
 * @author armin - largely copied from tebemis
 *
 */
public class XmppService extends Service {
	private final IBinder localBinder = new LocalBinder();
	private XMPPThread xmpp = null;

	@Override
	public void onCreate() {
		super.onCreate();
		xmpp = new XMPPThread(
				"test2",
				"109f4b3c50d7b0df729d299bc6f8e9ef9066971f", "MattAndroid",
				"s3@conference.proto.encorelab.org", "Heliotest1");
		xmpp.start();
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		xmpp.disconnect();
		xmpp.interrupt();
	}
	

	public IBinder onBind(Intent i) {
		return localBinder;
	}

	public class LocalBinder extends Binder {
		XmppService getService() {
			return XmppService.this;
		}
	}

	public void sendMessage(String dest, String message) {
		xmpp.sendTo(dest, message);
	}
	
	public void sendGroupChat(String message) {
		xmpp.sendGroupChat(message);
	}

	public void addObserver(Hypothesis hypo) {
		xmpp.addObserver(hypo);
	}
	
	public XMPPConnection getConnection() {
		return xmpp.connection;
	}
}
