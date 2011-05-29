package org.encorelab.sail.helioroom;


import java.util.ArrayList;
import java.util.List;

import org.encorelab.sail.android.xmpp.XMPPThread;

import android.app.Application;
import android.util.Log;

public class Helioroom extends Application {
	
	public static final String TAG = "HelioRoom";

	public static XMPPThread xmpp;

	public static List<Inquiry> inqList = new ArrayList<Inquiry>();
	public static List<Inquiry> discList = new ArrayList<Inquiry>();
	public static int[][] tableArray = new int[9][9];

	@Override
	public void onCreate() {
		super.onCreate();
		
		xmpp = new XMPPThread();
		xmpp.configure(getString(R.string.xmpp_host), 5222);
		xmpp.start();
	}
}
