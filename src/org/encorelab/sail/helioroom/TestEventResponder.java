package org.encorelab.sail.helioroom;

import org.encorelab.sail.helioroom.R;
import org.encorelab.sail.Event;
import org.encorelab.sail.EventResponder;

import android.util.Log;

public class TestEventResponder extends EventResponder {
	public TestEventResponder() {
		
		this.eventType = "submit_hypothesis";
	}
	@Override
	public void triggered(Event ev) {
		// TODO Auto-generated method stub
		Log.d("HelioRoom", ev.getPayloadAsString());
	}

}
