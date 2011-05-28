package org.encorelab.sail.helioroom;

import java.util.ArrayList;
import java.util.List;
import android.app.Activity;
import android.app.TabActivity;
import android.os.Bundle;
//import android.view.*;
import android.view.*;
import android.view.View.OnFocusChangeListener;
import android.widget.*;

import org.encorelab.sail.Event;
import org.encorelab.sail.helioroom.R;
import org.jivesoftware.smack.*;
import org.jivesoftware.smack.ConnectionConfiguration.SecurityMode;
import org.jivesoftware.smackx.*;
import org.jivesoftware.smackx.muc.*;
import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.TextView;

public class ObservationTab extends Activity {

	RadioGroup pre = null;
	RadioGroup post = null;
	private RadioButton pre1, pre2, pre3, pre4, pre5, pre6, pre7, pre8, pre9;
	private RadioButton post1, post2, post3, post4, post5, post6, post7, post8, post9;
	String preString = null;
	String postString = null;
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
//	tableArray[0][0] = 1;
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
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

		Button clButton = (Button) findViewById(R.id.clearButton);
		clButton.setOnClickListener(onClear);
		Button coButton = (Button) findViewById(R.id.contribButton);
		coButton.setOnClickListener(onContribute);

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
		
	}
	
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

//should we error check here for unclicked buttons (will show up as 0s)... or let Matt do it 

			o.setPre(preString);
			o.setPost(postString);
			
			Event ev = new Event("observation_submitted", o);
			ev.toJson();

//			service.sendGroupChat(ev.toString());
			
			((RadioGroup) findViewById(R.id.preButtons)).clearCheck();
			((RadioGroup) findViewById(R.id.postButtons)).clearCheck();			
		}
	};

    // show text based on events from the RadioGroup
	private View.OnClickListener onPre1 = new RadioGroup.OnClickListener() {
		public void onClick(View v) {
			colourIsText.setText("Red is");
			tableText11.setText(tableArray[0][0]);
		}
	};
	private View.OnClickListener onPre2 = new RadioGroup.OnClickListener() {
		public void onClick(View v) {
			colourIsText.setText("Orange is");
		}
	};
	private View.OnClickListener onPre3 = new RadioGroup.OnClickListener() {
		public void onClick(View v) {
			colourIsText.setText("Yellow is");
		}
	};
	private View.OnClickListener onPre4 = new RadioGroup.OnClickListener() {
		public void onClick(View v) {
			colourIsText.setText("Green is");
		}
	};
	private View.OnClickListener onPre5 = new RadioGroup.OnClickListener() {
		public void onClick(View v) {
			colourIsText.setText("Blue is");
		}
	};
	private View.OnClickListener onPre6 = new RadioGroup.OnClickListener() {
		public void onClick(View v) {
			colourIsText.setText("Purple is");
		}
	};
	private View.OnClickListener onPre7 = new RadioGroup.OnClickListener() {
		public void onClick(View v) {
			colourIsText.setText("Brown is");
		}
	};
	private View.OnClickListener onPre8 = new RadioGroup.OnClickListener() {
		public void onClick(View v) {
			colourIsText.setText("Gray is");
		}
	};
	private View.OnClickListener onPre9 = new RadioGroup.OnClickListener() {
		public void onClick(View v) {
			colourIsText.setText("Pink is");
		}
	};
			
}


//RadioButton post1 = (RadioButton) findViewById(R.id.redPost);
//RadioButton post2 = (RadioButton) findViewById(R.id.orangePost);
//RadioButton post3 = (RadioButton) findViewById(R.id.yellowPost);
//RadioButton post4 = (RadioButton) findViewById(R.id.greenPost);
//RadioButton post5 = (RadioButton) findViewById(R.id.bluePost);
//RadioButton post6 = (RadioButton) findViewById(R.id.purplePost);
//RadioButton post7 = (RadioButton) findViewById(R.id.brownPost);
//RadioButton post8 = (RadioButton) findViewById(R.id.grayPost);
//RadioButton post9 = (RadioButton) findViewById(R.id.pinkPost);