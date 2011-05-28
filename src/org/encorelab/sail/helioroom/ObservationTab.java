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
	int preDigit = 0;
	int postDigit = 0;
	TextView colourIsText = null;
	
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
				preDigit = 1;
				break;
			case R.id.orangePre:
				preDigit = 2;
				break;
			case R.id.yellowPre:
				preDigit = 3;
				break;
			case R.id.greenPre:
				preDigit = 4;
				break;
			case R.id.bluePre:
				preDigit = 5;
				break;
			case R.id.purplePre:
				preDigit = 6;
				break;
			case R.id.brownPre:
				preDigit = 7;
				break;
			case R.id.grayPre:
				preDigit = 8;
				break;
			case R.id.pinkPre:
				preDigit = 9;
				break;
			}
			switch (post.getCheckedRadioButtonId()) {
			case R.id.redPost:
				postDigit = 1;
				break;
			case R.id.orangePost:
				postDigit = 2;
				break;
			case R.id.yellowPost:
				postDigit = 3;
				break;
			case R.id.greenPost:
				postDigit = 4;
				break;
			case R.id.bluePost:
				postDigit = 5;
				break;
			case R.id.purplePost:
				postDigit = 6;
				break;
			case R.id.brownPost:
				postDigit = 7;
				break;
			case R.id.grayPost:
				postDigit = 8;
				break;
			case R.id.pinkPost:
				postDigit = 9;
				break;
			}

//should we error check here for unclicked buttons (will show up as 0s)... or let Matt do it 
			int relation = (preDigit*10) + postDigit;
			o.setObsContent(relation);
			
			Event ev = new Event("submit_observation", o);
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
