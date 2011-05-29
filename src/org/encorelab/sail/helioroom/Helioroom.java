package org.encorelab.sail.helioroom;


import org.encorelab.sail.android.xmpp.XMPPThread;

import android.app.Application;
import android.util.Log;

public class Helioroom extends Application {
	
	public static final String TAG = "HelioRoom";

	public static XMPPThread xmpp;

	public static int[][] tableArray = new int[9][9];

	@Override
	public void onCreate() {
		super.onCreate();
		
		xmpp = new XMPPThread();
		xmpp.configure(getString(R.string.xmpp_host), 5222);
		xmpp.start();
	}
}
