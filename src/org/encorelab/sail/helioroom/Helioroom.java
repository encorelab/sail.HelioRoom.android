package org.encorelab.sail.helioroom;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.*;


public class Helioroom extends TabActivity {
	// this is a class variable that all tabs can use to
	// send and receive XMPP messages
	static XMPPThread nt = null;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		
//		XMPPThread nt = null;

//		 start XMPP connection
		Helioroom.nt = new XMPPThread(
				"test1",
				"b444ac06613fc8d63795be9ad0beaf55011936ac", "textNotifier",
				"s3@conference.proto.encorelab.org", HelioroomLogin.groupId);
		Helioroom.nt.start();
	
		TabHost tabHost = getTabHost();
		TabHost.TabSpec spec;
		Intent intent;

//		intent = new Intent().setClass(this, ObservationTab.class);
//		spec = tabHost.newTabSpec("observation").setIndicator("Observation").setContent(intent);
//		tabHost.addTab(spec);
//		intent = new Intent().setClass(this, HypothesisTab.class);
//		spec = tabHost.newTabSpec("hypothesis").setIndicator("Hypothesis").setContent(intent);
//		tabHost.addTab(spec);
		intent = new Intent().setClass(this, InquiryTab.class);
		spec = tabHost.newTabSpec("inquiry").setIndicator("Inquiry Discussion").setContent(intent);
		tabHost.addTab(spec);

		getTabHost().setCurrentTab(0);

	}

	public void onDestroy() {
		super.onDestroy();
		//Helioroom.nt.disconnect();
		//Helioroom.nt.interrupt();
	}
}