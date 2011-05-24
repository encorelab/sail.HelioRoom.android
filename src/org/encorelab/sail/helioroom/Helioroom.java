package org.encorelab.sail.helioroom;

import org.encorelab.sail.helioroom.R;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.*;

public class Helioroom extends TabActivity {
	// this is a class variable that all tabs can use to
	// send and receive XMPP messages
	//static XMPPThread nt = null;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		TabHost tabHost = getTabHost();
		TabHost.TabSpec spec;
		Intent intent;

		intent = new Intent().setClass(this, ObservationTab.class);
		spec = tabHost.newTabSpec("observation").setIndicator("Observation").setContent(intent);
		tabHost.addTab(spec);
		intent = new Intent().setClass(this, HypothesisTab.class);
		spec = tabHost.newTabSpec("hypothesis").setIndicator("Hypothesis").setContent(intent);
		tabHost.addTab(spec);
		intent = new Intent().setClass(this, InquiryTab.class);
		spec = tabHost.newTabSpec("inquiry").setIndicator("Inquiry Discussion").setContent(intent);
		tabHost.addTab(spec);

		getTabHost().setCurrentTab(2);

		// start XMPP connection
		/*Helioroom.nt = new XMPPThread("test2",
				"a94a8fe5ccb19ba61c4c0873d391e987982fbbd3", "textNotifier",
				"s3@conference.proto.encorelab.org", "Heliotest1");
		Helioroom.nt.start();*/
	}

	public void onDestroy() {
		super.onDestroy();
		//Helioroom.nt.disconnect();
		//Helioroom.nt.interrupt();
	}
}