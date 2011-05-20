package org.encorelab.sail.helioroom;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class ObservationTab extends Activity {
	 public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        TextView textview = new TextView(this);
	        textview.setText("This is the Observation tab");
	        setContentView(textview);
	    }
}