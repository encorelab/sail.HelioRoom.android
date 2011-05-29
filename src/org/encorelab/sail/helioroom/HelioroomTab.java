package org.encorelab.sail.helioroom;

import android.app.TabActivity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.widget.*;


public class HelioroomTab extends TabActivity {
	// this is a class variable that all tabs can use to
	// send and receive XMPP messages
//	static XMPPThread nt = null;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);			//this replaces the orientation stuff in the manifest that wasn't working

//		 start XMPP connection
//		HelioroomTab.nt = new XMPPThread(
//				"test1",
//				"b444ac06613fc8d63795be9ad0beaf55011936ac", "textNotifier",
//				"s3@conference.proto.encorelab.org", HelioroomLogin.groupId);
//		HelioroomTab.nt.start();
	
		TabHost tabHost = getTabHost();
		TabHost.TabSpec spec;
		Intent intent;

		intent = new Intent().setClass(this, ObservationTab.class);
		spec = tabHost.newTabSpec("observation").setIndicator("Observations").setContent(intent);
		tabHost.addTab(spec);
//		intent = new Intent().setClass(this, HypothesisTab.class);
//		spec = tabHost.newTabSpec("hypothesis").setIndicator("Hypothesis").setContent(intent);
//		tabHost.addTab(spec);
		intent = new Intent().setClass(this, InquiryTab.class);
		spec = tabHost.newTabSpec("inquiry").setIndicator("Hypotheses").setContent(intent);
		tabHost.addTab(spec);
		
		TextView groupIdViewer = (TextView) findViewById(R.id.group_viewer);
		groupIdViewer.setText(HelioroomLogin.groupId);

		// To receive messages in the inquiry tab we set it current
		// and then switch to the observation tab
		getTabHost().setCurrentTab(1);
		getTabHost().setCurrentTab(0);

	}

	public void onDestroy() {
		super.onDestroy();
		//HelioroomTab.nt.disconnect();
		//HelioroomTab.nt.interrupt();
	}
}