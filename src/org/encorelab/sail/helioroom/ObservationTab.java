package org.encorelab.sail.helioroom;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import android.app.Activity;
import android.app.TabActivity;
import android.os.Bundle;
import android.os.Handler;
//import android.view.*;
import android.util.Log;
import android.view.*;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.widget.*;

import org.encorelab.sail.Event;
import org.encorelab.sail.android.EventListener;
import org.encorelab.sail.android.EventResponder;
import org.encorelab.sail.helioroom.R;
import org.jivesoftware.smack.*;
import org.jivesoftware.smack.ConnectionConfiguration.SecurityMode;
import org.jivesoftware.smackx.*;
import org.jivesoftware.smackx.muc.*;
import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.TextView;

public class ObservationTab extends Activity implements OnClickListener {

	//observation vars
	RadioGroup pre = null;
	RadioGroup post = null;
	private RadioButton pre1, pre2, pre3, pre4, pre5, pre6, pre7, pre8, pre9;
	private RadioButton post1, post2, post3, post4, post5, post6, post7, post8, post9;
	String preString = null;
	String postString = null;
	Button coButton = null;
	Button clButton = null;
	
	//table vars
	TextView colourIsText = null;
	TextView tableText11 = null;
	TextView tableText12 = null;
	TextView tableText13 = null;
	TextView tableText14 = null;
	TextView tableText15 = null;
	TextView tableText16 = null;
	TextView tableText17 = null;
	TextView tableText18 = null;
	TextView tableText19 = null;	
	TextView tableText21 = null;
	TextView tableText22 = null;
	TextView tableText23 = null;
	TextView tableText24 = null;
	TextView tableText25 = null;
	TextView tableText26 = null;
	TextView tableText27 = null;
	TextView tableText28 = null;
	TextView tableText29 = null;
	
	int[][] tableArray = new int[9][9];

	//to unlock contrib button
    boolean preFlag = false;
    boolean postFlag = false;

	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		Handler xmppHandler = new Handler();
		EventListener listener = new EventListener(xmppHandler);

		listener.addResponder("observation_submitted", new EventResponder() {
			@Override
			public void respond(Event ev) {

				Log.d(Helioroom.TAG, "Got observation!");
				Observation o = (Observation) ev.getPayload(Observation.class);
				
				HashMap<String,Integer> hm = new HashMap<String,Integer>();
				hm.put("red", new Integer(0));
				hm.put("orange", new Integer(1));
				hm.put("yellow", new Integer(2));
				hm.put("green", new Integer(3));
				hm.put("blue", new Integer(4));
				hm.put("purple", new Integer(5));
				hm.put("brown", new Integer(6));
				hm.put("gray", new Integer(7));
				hm.put("pink", new Integer(8));
				
				int x = hm.get(o.getPre());
				int y = hm.get(o.getPost());
				
				tableArray[x][y]++;
				
			}
		});
		
		Helioroom.xmpp.addEventListener(listener);

		
		setContentView(R.layout.observation);

		pre = (RadioGroup) findViewById(R.id.preButtons);
		post = (RadioGroup) findViewById(R.id.postButtons);

		RadioButton pre1 = (RadioButton) findViewById(R.id.redPre);
		pre1.setOnClickListener(onPre1);
		RadioButton pre2 = (RadioButton) findViewById(R.id.orangePre);
		pre2.setOnClickListener(onPre2);
		RadioButton pre3 = (RadioButton) findViewById(R.id.yellowPre);
		pre3.setOnClickListener(onPre3);
		RadioButton pre4 = (RadioButton) findViewById(R.id.greenPre);
		pre4.setOnClickListener(onPre4);
		RadioButton pre5 = (RadioButton) findViewById(R.id.bluePre);
		pre5.setOnClickListener(onPre5);
		RadioButton pre6 = (RadioButton) findViewById(R.id.purplePre);
		pre6.setOnClickListener(onPre6);
		RadioButton pre7 = (RadioButton) findViewById(R.id.brownPre);
		pre7.setOnClickListener(onPre7);
		RadioButton pre8 = (RadioButton) findViewById(R.id.grayPre);
		pre8.setOnClickListener(onPre8);
		RadioButton pre9 = (RadioButton) findViewById(R.id.pinkPre);
		pre9.setOnClickListener(onPre9);

		RadioButton post1 = (RadioButton) findViewById(R.id.redPost);
		post1.setOnClickListener(this);
		RadioButton post2 = (RadioButton) findViewById(R.id.orangePost);
		post2.setOnClickListener(this);
		RadioButton post3 = (RadioButton) findViewById(R.id.yellowPost);
		post3.setOnClickListener(this);
		RadioButton post4 = (RadioButton) findViewById(R.id.greenPost);
		post4.setOnClickListener(this);
		RadioButton post5 = (RadioButton) findViewById(R.id.bluePost);
		post5.setOnClickListener(this);
		RadioButton post6 = (RadioButton) findViewById(R.id.purplePost);
		post6.setOnClickListener(this);
		RadioButton post7 = (RadioButton) findViewById(R.id.brownPost);
		post7.setOnClickListener(this);
		RadioButton post8 = (RadioButton) findViewById(R.id.grayPost);
		post8.setOnClickListener(this);
		RadioButton post9 = (RadioButton) findViewById(R.id.pinkPost);
		post9.setOnClickListener(this);
		
		clButton = (Button) findViewById(R.id.clearButton);
		clButton.setOnClickListener(onClear);
		coButton = (Button) findViewById(R.id.contribButton);
		coButton.setOnClickListener(onContribute);
		coButton.setEnabled(false);

		colourIsText = (TextView) findViewById(R.id.selectedText);
		tableText11 = (TextView) findViewById(R.id.table11);
		tableText12 = (TextView) findViewById(R.id.table12);
		tableText13 = (TextView) findViewById(R.id.table13);
		tableText14 = (TextView) findViewById(R.id.table14);
		tableText15 = (TextView) findViewById(R.id.table15);
		tableText16 = (TextView) findViewById(R.id.table16);
		tableText17 = (TextView) findViewById(R.id.table17);
		tableText18 = (TextView) findViewById(R.id.table18);
		tableText19 = (TextView) findViewById(R.id.table19);
		tableText21 = (TextView) findViewById(R.id.table21);
		tableText22 = (TextView) findViewById(R.id.table22);
		tableText23 = (TextView) findViewById(R.id.table23);
		tableText24 = (TextView) findViewById(R.id.table24);
		tableText25 = (TextView) findViewById(R.id.table25);
		tableText26 = (TextView) findViewById(R.id.table26);
		tableText27 = (TextView) findViewById(R.id.table27);
		tableText28 = (TextView) findViewById(R.id.table28);
		tableText29 = (TextView) findViewById(R.id.table29);
		
		for (int i=0; i<tableArray.length; i++) {
		     for (int j=0; j<tableArray.length; j++) {
		         tableArray[i][j] = 0;
		     }
		     //tableArray[i][j] = 0;
		 }
			
	}

//	private View.OnClickListener activeCon = new View.OnClickListener() {
//		public void onClick(View v) {
//			if ((RadioGroup) findViewById(R.id.preButtons)).
//			if post.
//		}
//	};
	
	private View.OnClickListener onClear = new View.OnClickListener() {
		public void onClick(View v) {
			((RadioGroup) findViewById(R.id.preButtons)).clearCheck();
			((RadioGroup) findViewById(R.id.postButtons)).clearCheck();
		}
	};

	private View.OnClickListener onContribute = new View.OnClickListener() {
		public void onClick(View v) {
			Observation o = new Observation();

			switch (pre.getCheckedRadioButtonId()) {
			case R.id.redPre:
				preString = "red";
				break;
			case R.id.orangePre:
				preString = "orange";
				break;
			case R.id.yellowPre:
				preString = "yellow";
				break;
			case R.id.greenPre:
				preString = "green";
				break;
			case R.id.bluePre:
				preString = "blue";
				break;
			case R.id.purplePre:
				preString = "purple";
				break;
			case R.id.brownPre:
				preString = "brown";
				break;
			case R.id.grayPre:
				preString = "gray";
				break;
			case R.id.pinkPre:
				preString = "pink";
				break;
			}
			switch (post.getCheckedRadioButtonId()) {
			case R.id.redPost:
				postString = "red";
				break;
			case R.id.orangePost:
				postString = "orange";
				break;
			case R.id.yellowPost:
				postString = "yellow";
				break;
			case R.id.greenPost:
				postString = "green";
				break;
			case R.id.bluePost:
				postString = "blue";
				break;
			case R.id.purplePost:
				postString = "purple";
				break;
			case R.id.brownPost:
				postString = "brown";
				break;
			case R.id.grayPost:
				postString = "gray";
				break;
			case R.id.pinkPost:
				postString = "pink";
				break;
			}

			o.setPre(preString);
			o.setPost(postString);
			
			Event ev = new Event("observation_submitted", o);

			Helioroom.xmpp.sendEvent(ev);

			((RadioGroup) findViewById(R.id.preButtons)).clearCheck();
			((RadioGroup) findViewById(R.id.postButtons)).clearCheck();
			coButton.setEnabled(false);
			preFlag = false;
			postFlag = false;
		}
	};
	
    // show text based on events from the RadioGroup (super AWK)
	private View.OnClickListener onPre1 = new RadioGroup.OnClickListener() {
		public void onClick(View v) {
			colourIsText.setText("Red is");
			tableText11.setText(Integer.toString(tableArray[0][0]));
			tableText12.setText(Integer.toString(tableArray[0][1]));
			tableText13.setText(Integer.toString(tableArray[0][2]));
			tableText14.setText(Integer.toString(tableArray[0][3]));
			tableText15.setText(Integer.toString(tableArray[0][4]));
			tableText16.setText(Integer.toString(tableArray[0][5]));
			tableText17.setText(Integer.toString(tableArray[0][6]));
			tableText18.setText(Integer.toString(tableArray[0][7]));
			tableText19.setText(Integer.toString(tableArray[0][8]));
			tableText21.setText(Integer.toString(tableArray[0][0]));
			tableText22.setText(Integer.toString(tableArray[1][0]));
			tableText23.setText(Integer.toString(tableArray[2][0]));
			tableText24.setText(Integer.toString(tableArray[3][0]));
			tableText25.setText(Integer.toString(tableArray[4][0]));
			tableText26.setText(Integer.toString(tableArray[5][0]));
			tableText27.setText(Integer.toString(tableArray[6][0]));
			tableText28.setText(Integer.toString(tableArray[7][0]));
			tableText29.setText(Integer.toString(tableArray[8][0]));
			preFlag = true;
		}
	};
	private View.OnClickListener onPre2 = new RadioGroup.OnClickListener() {
		public void onClick(View v) {
			colourIsText.setText("Orange is");
			tableText11.setText(Integer.toString(tableArray[1][0]));
			tableText12.setText(Integer.toString(tableArray[1][1]));
			tableText13.setText(Integer.toString(tableArray[1][2]));
			tableText14.setText(Integer.toString(tableArray[1][3]));
			tableText15.setText(Integer.toString(tableArray[1][4]));
			tableText16.setText(Integer.toString(tableArray[1][5]));
			tableText17.setText(Integer.toString(tableArray[1][6]));
			tableText18.setText(Integer.toString(tableArray[1][7]));
			tableText19.setText(Integer.toString(tableArray[1][8]));
			tableText21.setText(Integer.toString(tableArray[0][1]));
			tableText22.setText(Integer.toString(tableArray[1][1]));
			tableText23.setText(Integer.toString(tableArray[2][1]));
			tableText24.setText(Integer.toString(tableArray[3][1]));
			tableText25.setText(Integer.toString(tableArray[4][1]));
			tableText26.setText(Integer.toString(tableArray[5][1]));
			tableText27.setText(Integer.toString(tableArray[6][1]));
			tableText28.setText(Integer.toString(tableArray[7][1]));
			tableText29.setText(Integer.toString(tableArray[8][1]));
			preFlag = true;
		}
	};
	private View.OnClickListener onPre3 = new RadioGroup.OnClickListener() {
		public void onClick(View v) {
			colourIsText.setText("Yellow is");
			tableText11.setText(Integer.toString(tableArray[2][0]));
			tableText12.setText(Integer.toString(tableArray[2][1]));
			tableText13.setText(Integer.toString(tableArray[2][2]));
			tableText14.setText(Integer.toString(tableArray[2][3]));
			tableText15.setText(Integer.toString(tableArray[2][4]));
			tableText16.setText(Integer.toString(tableArray[2][5]));
			tableText17.setText(Integer.toString(tableArray[2][6]));
			tableText18.setText(Integer.toString(tableArray[2][7]));
			tableText19.setText(Integer.toString(tableArray[2][8]));
			tableText21.setText(Integer.toString(tableArray[0][2]));
			tableText22.setText(Integer.toString(tableArray[1][2]));
			tableText23.setText(Integer.toString(tableArray[2][2]));
			tableText24.setText(Integer.toString(tableArray[3][2]));
			tableText25.setText(Integer.toString(tableArray[4][2]));
			tableText26.setText(Integer.toString(tableArray[5][2]));
			tableText27.setText(Integer.toString(tableArray[6][2]));
			tableText28.setText(Integer.toString(tableArray[7][2]));
			tableText29.setText(Integer.toString(tableArray[8][2]));
			preFlag = true;
		}
	};
	private View.OnClickListener onPre4 = new RadioGroup.OnClickListener() {
		public void onClick(View v) {
			colourIsText.setText("Green is");
			tableText11.setText(Integer.toString(tableArray[3][0]));
			tableText12.setText(Integer.toString(tableArray[3][1]));
			tableText13.setText(Integer.toString(tableArray[3][2]));
			tableText14.setText(Integer.toString(tableArray[3][3]));
			tableText15.setText(Integer.toString(tableArray[3][4]));
			tableText16.setText(Integer.toString(tableArray[3][5]));
			tableText17.setText(Integer.toString(tableArray[3][6]));
			tableText18.setText(Integer.toString(tableArray[3][7]));
			tableText19.setText(Integer.toString(tableArray[3][8]));
			tableText21.setText(Integer.toString(tableArray[0][3]));
			tableText22.setText(Integer.toString(tableArray[1][3]));
			tableText23.setText(Integer.toString(tableArray[2][3]));
			tableText24.setText(Integer.toString(tableArray[3][3]));
			tableText25.setText(Integer.toString(tableArray[4][3]));
			tableText26.setText(Integer.toString(tableArray[5][3]));
			tableText27.setText(Integer.toString(tableArray[6][3]));
			tableText28.setText(Integer.toString(tableArray[7][3]));
			tableText29.setText(Integer.toString(tableArray[8][3]));
			preFlag = true;
		}
	};
	private View.OnClickListener onPre5 = new RadioGroup.OnClickListener() {
		public void onClick(View v) {
			colourIsText.setText("Blue is");
			tableText11.setText(Integer.toString(tableArray[4][0]));
			tableText12.setText(Integer.toString(tableArray[4][1]));
			tableText13.setText(Integer.toString(tableArray[4][2]));
			tableText14.setText(Integer.toString(tableArray[4][3]));
			tableText15.setText(Integer.toString(tableArray[4][4]));
			tableText16.setText(Integer.toString(tableArray[4][5]));
			tableText17.setText(Integer.toString(tableArray[4][6]));
			tableText18.setText(Integer.toString(tableArray[4][7]));
			tableText19.setText(Integer.toString(tableArray[4][8]));
			tableText21.setText(Integer.toString(tableArray[0][4]));
			tableText22.setText(Integer.toString(tableArray[1][4]));
			tableText23.setText(Integer.toString(tableArray[2][4]));
			tableText24.setText(Integer.toString(tableArray[3][4]));
			tableText25.setText(Integer.toString(tableArray[4][4]));
			tableText26.setText(Integer.toString(tableArray[5][4]));
			tableText27.setText(Integer.toString(tableArray[6][4]));
			tableText28.setText(Integer.toString(tableArray[7][4]));
			tableText29.setText(Integer.toString(tableArray[8][4]));
			preFlag = true;
		}
	};
	private View.OnClickListener onPre6 = new RadioGroup.OnClickListener() {
		public void onClick(View v) {
			colourIsText.setText("Purple is");
			tableText11.setText(Integer.toString(tableArray[5][0]));
			tableText12.setText(Integer.toString(tableArray[5][1]));
			tableText13.setText(Integer.toString(tableArray[5][2]));
			tableText14.setText(Integer.toString(tableArray[5][3]));
			tableText15.setText(Integer.toString(tableArray[5][4]));
			tableText16.setText(Integer.toString(tableArray[5][5]));
			tableText17.setText(Integer.toString(tableArray[5][6]));
			tableText18.setText(Integer.toString(tableArray[5][7]));
			tableText19.setText(Integer.toString(tableArray[5][8]));
			tableText21.setText(Integer.toString(tableArray[0][5]));
			tableText22.setText(Integer.toString(tableArray[1][5]));
			tableText23.setText(Integer.toString(tableArray[2][5]));
			tableText24.setText(Integer.toString(tableArray[3][5]));
			tableText25.setText(Integer.toString(tableArray[4][5]));
			tableText26.setText(Integer.toString(tableArray[5][5]));
			tableText27.setText(Integer.toString(tableArray[6][5]));
			tableText28.setText(Integer.toString(tableArray[7][5]));
			tableText29.setText(Integer.toString(tableArray[8][5]));
			preFlag = true;
		}
	};
	private View.OnClickListener onPre7 = new RadioGroup.OnClickListener() {
		public void onClick(View v) {
			colourIsText.setText("Brown is");
			tableText11.setText(Integer.toString(tableArray[6][0]));
			tableText12.setText(Integer.toString(tableArray[6][1]));
			tableText13.setText(Integer.toString(tableArray[6][2]));
			tableText14.setText(Integer.toString(tableArray[6][3]));
			tableText15.setText(Integer.toString(tableArray[6][4]));
			tableText16.setText(Integer.toString(tableArray[6][5]));
			tableText17.setText(Integer.toString(tableArray[6][6]));
			tableText18.setText(Integer.toString(tableArray[6][7]));
			tableText19.setText(Integer.toString(tableArray[6][8]));
			tableText21.setText(Integer.toString(tableArray[0][6]));
			tableText22.setText(Integer.toString(tableArray[1][6]));
			tableText23.setText(Integer.toString(tableArray[2][6]));
			tableText24.setText(Integer.toString(tableArray[3][6]));
			tableText25.setText(Integer.toString(tableArray[4][6]));
			tableText26.setText(Integer.toString(tableArray[5][6]));
			tableText27.setText(Integer.toString(tableArray[6][6]));
			tableText28.setText(Integer.toString(tableArray[7][6]));
			tableText29.setText(Integer.toString(tableArray[8][6]));
			preFlag = true;
		}
	};
	private View.OnClickListener onPre8 = new RadioGroup.OnClickListener() {
		public void onClick(View v) {
			colourIsText.setText("Gray is");
			tableText11.setText(Integer.toString(tableArray[7][0]));
			tableText12.setText(Integer.toString(tableArray[7][1]));
			tableText13.setText(Integer.toString(tableArray[7][2]));
			tableText14.setText(Integer.toString(tableArray[7][3]));
			tableText15.setText(Integer.toString(tableArray[7][4]));
			tableText16.setText(Integer.toString(tableArray[7][5]));
			tableText17.setText(Integer.toString(tableArray[7][6]));
			tableText18.setText(Integer.toString(tableArray[7][7]));
			tableText19.setText(Integer.toString(tableArray[7][8]));
			tableText21.setText(Integer.toString(tableArray[0][7]));
			tableText22.setText(Integer.toString(tableArray[1][7]));
			tableText23.setText(Integer.toString(tableArray[2][7]));
			tableText24.setText(Integer.toString(tableArray[3][7]));
			tableText25.setText(Integer.toString(tableArray[4][7]));
			tableText26.setText(Integer.toString(tableArray[5][7]));
			tableText27.setText(Integer.toString(tableArray[6][7]));
			tableText28.setText(Integer.toString(tableArray[7][7]));
			tableText29.setText(Integer.toString(tableArray[8][7]));
			preFlag = true;
		}
	};
	private View.OnClickListener onPre9 = new RadioGroup.OnClickListener() {
		public void onClick(View v) {
			colourIsText.setText("Pink is");
			tableText11.setText(Integer.toString(tableArray[8][0]));
			tableText12.setText(Integer.toString(tableArray[8][1]));
			tableText13.setText(Integer.toString(tableArray[8][2]));
			tableText14.setText(Integer.toString(tableArray[8][3]));
			tableText15.setText(Integer.toString(tableArray[8][4]));
			tableText16.setText(Integer.toString(tableArray[8][5]));
			tableText17.setText(Integer.toString(tableArray[8][6]));
			tableText18.setText(Integer.toString(tableArray[8][7]));
			tableText19.setText(Integer.toString(tableArray[8][8]));
			tableText21.setText(Integer.toString(tableArray[0][8]));
			tableText22.setText(Integer.toString(tableArray[1][8]));
			tableText23.setText(Integer.toString(tableArray[2][8]));
			tableText24.setText(Integer.toString(tableArray[3][8]));
			tableText25.setText(Integer.toString(tableArray[4][8]));
			tableText26.setText(Integer.toString(tableArray[5][8]));
			tableText27.setText(Integer.toString(tableArray[6][8]));
			tableText28.setText(Integer.toString(tableArray[7][8]));
			tableText29.setText(Integer.toString(tableArray[8][8]));
			preFlag = true;
		}
	};

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
	    
		switch(v.getId()){  
	    case R.id.redPost:
	    	postFlag = true;
	        break;  
	    case R.id.orangePost:
	    	postFlag = true;
	    	break;
	    case R.id.yellowPost:
	    	postFlag = true;
	    	break;  
	    case R.id.greenPost:
	    	postFlag = true;
	    	break;  
	    case R.id.bluePost:
	    	postFlag = true;
	    	break;  
	    case R.id.purplePost:
	    	postFlag = true;
	    	break;  
	    case R.id.brownPost:
	    	postFlag = true;
	    	break;  
	    case R.id.grayPost:
	    	postFlag = true;
	    	break;  
	    case R.id.pinkPost:
	    	postFlag = true;
	    	break;  

	    }
		
		if (preFlag == true && postFlag == true) {
			coButton.setEnabled(true);
		}
	}
}