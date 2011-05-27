package org.encorelab.sail.helioroom;

import org.encorelab.sail.Event;
import org.encorelab.sail.EventListener;
import org.encorelab.sail.EventResponder;
import org.encorelab.sail.android.xmpp.XMPPService;
import org.encorelab.sail.android.xmpp.XMPPService.LocalBinder;
import org.encorelab.sail.android.xmpp.XMPPServiceIntent;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.filter.PacketTypeFilter;
import org.jivesoftware.smack.packet.Message;

import android.app.Application;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.util.Log;

public class Helioroom extends Application {
	
	public static final String TAG = "HelioRoom";

	public static XMPPThread xmpp;

	@Override
	public void onCreate() {
		super.onCreate();
		
		Helioroom.bindToXMPPService(this, xmppServiceConnection);
	}
	
	
	public static void bindToXMPPService(Context context, ServiceConnection bindTo) {
		Log.d(Helioroom.TAG, "Binding "+context.toString()+" to XMPPService...");
		
		XMPPServiceIntent intent = new XMPPServiceIntent(context,
				context.getString(R.string.xmpp_host));
		
		context.getApplicationContext().bindService(intent, bindTo,
				Context.BIND_AUTO_CREATE);
	}
	

	private ServiceConnection xmppServiceConnection = new ServiceConnection() {

		@Override
		public void onServiceConnected(ComponentName className, IBinder serv) {
			Log.d(Helioroom.TAG, "Connecting to XMPPService in "+className.toString());
			LocalBinder binder = (LocalBinder) serv;
			xmppService = binder.getService();
		}

		@Override
		public void onServiceDisconnected(ComponentName cn) {

		}
	};
}
