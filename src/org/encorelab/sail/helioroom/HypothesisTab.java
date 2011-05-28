package org.encorelab.sail.helioroom;

import java.util.Observable;
import java.util.Observer;

import org.encorelab.sail.Event;
import org.encorelab.sail.helioroom.R;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;


public class HypothesisTab extends Activity implements Observer {
	// FIXME For some reason I can't set hypo in update and then
	// access it if I click Refresh
	static Hypothesis hypo = new Hypothesis();
	
	private EditText sendChat;
	private TextView textRec;
	private Button submitButton;
	private Button refreshButton;
    private boolean mBound = false;
    private ListView mConversationView;
    
    // Array adapter for the conversation thread
    private ArrayAdapter<String> mConversationArrayAdapter;

    
    //static String chatMessage;


	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		
        
		// set up the view
		setContentView(R.layout.hypothesis);
		
		// Let this Activity become a observer of Hypothesis
		hypo.addObserver(this);

		// TextView
		textRec = (TextView) findViewById(R.id.textRec);
		// EditText
		sendChat = (EditText) findViewById(R.id.sendChat);
		
		// Button
		submitButton = (Button) findViewById(R.id.submit);
		submitButton.setOnClickListener(onSubmit);
		
		// Button
		refreshButton = (Button) findViewById(R.id.refresh);
		refreshButton.setOnClickListener(onRefresh);
		
		// Initialize the array adapter for the conversation thread
        mConversationArrayAdapter = new ArrayAdapter<String>(this, R.layout.hypothesis);
        mConversationView = (ListView) findViewById(R.id.chatIn);
        mConversationView.setAdapter(mConversationArrayAdapter);

		
		//hypo.addObserver(this);
	}


	// Called when the user clicks submit
	// FIXME need to add push out on XMPP
	private View.OnClickListener onSubmit = new View.OnClickListener() {
		public void onClick(View v) {			
			// sending message to the chat room
			//HelioroomTab.nt.sendGroupChat(sendChat.getText().toString());
			
			// FIXME adding observer in onCreate fails, here it is not
			// the right place either :/
			//HelioroomTab.nt.addObserver(hypo);
			
			Event ev = new Event("submitHypothesis", sendChat.getText().toString());
			
			Helioroom.xmpp.sendEvent(ev);

			// Clears the text fields
			sendChat.setText("");
			
			textRec.setText("Listening my friend...");
		}
	};
	
	// Called when the user clicks submit
	// FIXME need to add push out on XMPP
	private View.OnClickListener onRefresh = new View.OnClickListener() {
		public void onClick(View v) {			
			String h = HypothesisTab.hypo.getMessage();
			textRec.setText(h);
		}
	};

	@Override
	public void update(Observable observable, Object hypothesis) {
		// TODO Auto-generated method stub
		HypothesisTab.hypo = ((Hypothesis) hypothesis);
				
		Message chatMessage = new Message();
		chatMessage.obj = ((Hypothesis) hypothesis).getMessage();
		mHandler.handleMessage(chatMessage);
	}
	
	// The Handler that gets information back from the XMPP chat
    private final Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
        	String line = (String) msg.obj;
        	// FIXME the line below crashes the application ?????
        	// mConversationArrayAdapter.add(line);
        }
    };

}