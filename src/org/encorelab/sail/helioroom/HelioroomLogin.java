package org.encorelab.sail.helioroom;

import org.encorelab.sail.android.xmpp.XMPPService;
import org.encorelab.sail.android.xmpp.XMPPServiceIntent;
import org.encorelab.sail.android.xmpp.XMPPService.LocalBinder;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class HelioroomLogin extends Activity implements OnClickListener {

	static String groupId = "";
	public XMPPService xmppService;
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);
		
		Helioroom.bindToXMPPService(this, xmppServiceConnection);

//we should be logging in here, we may want to fix this later	
//		XMPPThread nt = null;
//		nt = new XMPPThread("test2",
//		"a94a8fe5ccb19ba61c4c0873d391e987982fbbd3", "textNotifier",
//		"s3@conference.proto.encorelab.org", "Heliotest1");
//		nt.run();

		
		Button group1 = (Button) findViewById(R.id.button1);
		group1.setOnClickListener(this);
		Button group2 = (Button) findViewById(R.id.button2);
		group2.setOnClickListener(this);
		Button group3 = (Button) findViewById(R.id.button3);
		group3.setOnClickListener(this);
		Button group4 = (Button) findViewById(R.id.button4);
		group4.setOnClickListener(this);
		Button group5 = (Button) findViewById(R.id.button5);
		group5.setOnClickListener(this);
		Button group6 = (Button) findViewById(R.id.button6);
		group6.setOnClickListener(this);
		Button teacher = (Button) findViewById(R.id.button7);
		teacher.setOnClickListener(this);
	}

    @Override  
    public void onClick(View v) {  
		Intent intent = new Intent();
		intent.setClass(HelioroomLogin.this, HelioroomTab.class);

    	switch(v.getId()){  
        case R.id.button1:  
        	groupId = "helio1";
            break;  
        case R.id.button2:  
        	groupId = "helio2";
            break;
        case R.id.button3:  
        	groupId = "helio3";
            break;  
        case R.id.button4:  
        	groupId = "helio4";
            break;  
        case R.id.button5:  
        	groupId = "helio5";
            break;  
        case R.id.button6:  
        	groupId = "helio6";
            break;  
        case R.id.button7:  
        	groupId = "helio-teacher";
            break; 
    	}  
    	
    	try {
    		xmppService.login(groupId, getString(R.string.xmpp_default_password), groupId);
    	} catch (Exception e) {
    		Log.e(Helioroom.TAG, "XMPP login failed: "+e.getMessage(), e);
    	}
    	
    	xmppService.joinConference("s3@proto.encorelab.org/Test");
    	
        startActivity(intent);
  		finish();
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