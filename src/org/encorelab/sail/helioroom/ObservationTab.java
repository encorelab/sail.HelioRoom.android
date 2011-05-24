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
	RadioButton pre1, pre2, pre3, pre4, pre5, pre6, pre7, pre8, pre9;
	RadioButton post1, post2, post3, post4, post5, post6, post7, post8, post9;
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.observation);

		pre = (RadioGroup) findViewById(R.id.preButtons);
		post = (RadioGroup) findViewById(R.id.postButtons);
		RadioButton pre1 = (RadioButton) findViewById(R.id.redPre);
		RadioButton pre2 = (RadioButton) findViewById(R.id.orangePre);
		RadioButton pre3 = (RadioButton) findViewById(R.id.yellowPre);
		RadioButton pre4 = (RadioButton) findViewById(R.id.greenPre);
		RadioButton pre5 = (RadioButton) findViewById(R.id.bluePre);
		RadioButton pre6 = (RadioButton) findViewById(R.id.purplePre);
		RadioButton pre7 = (RadioButton) findViewById(R.id.brownPre);
		RadioButton pre8 = (RadioButton) findViewById(R.id.grayPre);
		RadioButton pre9 = (RadioButton) findViewById(R.id.pinkPre);
		RadioButton post1 = (RadioButton) findViewById(R.id.redPost);
		RadioButton post2 = (RadioButton) findViewById(R.id.orangePost);
		RadioButton post3 = (RadioButton) findViewById(R.id.yellowPost);
		RadioButton post4 = (RadioButton) findViewById(R.id.greenPost);
		RadioButton post5 = (RadioButton) findViewById(R.id.bluePost);
		RadioButton post6 = (RadioButton) findViewById(R.id.purplePost);
		RadioButton post7 = (RadioButton) findViewById(R.id.brownPost);
		RadioButton post8 = (RadioButton) findViewById(R.id.grayPost);
		RadioButton post9 = (RadioButton) findViewById(R.id.pinkPost);
		
		Button clButton = (Button) findViewById(R.id.clearButton);
		clButton.setOnClickListener(onClear);
		Button coButton = (Button) findViewById(R.id.contribButton);
		coButton.setOnClickListener(onContribute);
		
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
			int preDigit = 0;
			int postDigit = 0;
			
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
			
			int relation = (preDigit*10) + postDigit;
			o.setObsContent(relation);
			((RadioGroup) findViewById(R.id.preButtons)).clearCheck();
			((RadioGroup) findViewById(R.id.postButtons)).clearCheck();			
		}
	};
		
			
}