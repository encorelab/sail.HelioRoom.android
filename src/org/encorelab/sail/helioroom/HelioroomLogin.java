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
        	groupId = "Group 1";
            break;  
        case R.id.button2:  
        	groupId = "Group 2";
            break;
        case R.id.button3:  
        	groupId = "Group 3";
            break;  
        case R.id.button4:  
        	groupId = "Group 4";
            break;  
        case R.id.button5:  
        	groupId = "Group 5";
            break;  
        case R.id.button6:  
        	groupId = "Group 6";
            break;  
        case R.id.button7:  
        	groupId = "Teacher";
            break; 
    	}  

        startActivity(intent);
  		finish();
    }
}