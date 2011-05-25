package org.encorelab.sail.helioroom;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class HelioroomLogin extends Activity implements OnClickListener {

	static String groupId = "";
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);

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
		intent.setClass(HelioroomLogin.this, Helioroom.class);

    	switch(v.getId()){  
        case R.id.button1:  
        	groupId = "Group1";
            break;  
        case R.id.button2:  
        	groupId = "Group2";
            break;
        case R.id.button3:  
        	groupId = "Group3";
            break;  
        case R.id.button4:  
        	groupId = "Group4";
            break;  
        case R.id.button5:  
        	groupId = "Group5";
            break;  
        case R.id.button6:  
        	groupId = "Group6";
            break;  
        case R.id.button7:  
        	groupId = "Teacher";
            break; 
    	}  

        startActivity(intent);
  		finish();
    }
}