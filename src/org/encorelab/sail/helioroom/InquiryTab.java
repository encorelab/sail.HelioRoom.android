package org.encorelab.sail.helioroom;


import org.encorelab.sail.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import android.app.Activity;
import android.app.TabActivity;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.util.Log;
import android.view.*;
import android.view.View.OnFocusChangeListener;
import android.widget.*;

import org.encorelab.sail.helioroom.R;
import org.encorelab.sail.helioroom.XmppService.LocalBinder;
import org.jivesoftware.smack.*;
import org.jivesoftware.smack.ConnectionConfiguration.SecurityMode;
import org.jivesoftware.smack.filter.PacketTypeFilter;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smackx.*;
import org.jivesoftware.smackx.muc.*;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.TextView;


public class InquiryTab extends Activity implements Observer {

	EditText qTitle = null;
	EditText qContent = null;
	EditText dTitle = null;
	EditText dContent = null;
	TextView vTitle = null;
	TextView vNote = null;
	TextView vComment = null;
	EditText vEdit = null;
	String groupId = HelioroomLogin.groupId;						//set at login screen
	//private XmppService service;
	private XMPPThread xmpp;
	private Handler xmppHandler;
	private XmppService service;
	
	
	List<Inquiry> inqList = new ArrayList<Inquiry>();
	List<Inquiry> discList = new ArrayList<Inquiry>();

	InquiryAdapter qAdapter = null;
	DiscussionAdapter dAdapter = null;

	Inquiry currentInq = null;			//used for Inq Disc Viewer
	int idCounter = 1;					//will this be reset every time this tab is opened (POTENTIAL BUG)
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
				
		Intent intent = new Intent(this, XmppService.class);
		getApplicationContext().bindService(intent, mConnection, Context.BIND_AUTO_CREATE);
		
		xmppHandler = new Handler() {
			public void handleMessage(android.os.Message msg) {
				if (msg.arg1 == 1) {
					Log.d("HelioRoom", "handling message");
					qAdapter.add((Inquiry) msg.obj);
					Log.d("HelioRoom", "notifying qAdapter of data set changed");
				}
				if (msg.arg1 == 2) {
					Log.d("HelioRoom", "handling message");
					dAdapter.add((Inquiry) msg.obj);
					Log.d("HelioRoom", "notifying dAdapter of data set changed");
				}
				if (msg.arg1 == 3) {
					Log.d("HelioRoom", "handling message");
					qAdapter.insert((Inquiry) msg.obj, msg.arg2);
					Log.d("HelioRoom", "notifying dAdapter of data set changed");
				}
				if (msg.arg1 == 4) {
					Log.d("HelioRoom", "handling message");
					dAdapter.insert((Inquiry) msg.obj, msg.arg2);
					Log.d("HelioRoom", "notifying dAdapter of data set changed");
				}


				qAdapter.notifyDataSetChanged();
			}
		};
		
		
		setContentView(R.layout.inquiry);

		qTitle = (EditText) findViewById(R.id.inqTitle);
		qContent = (EditText) findViewById(R.id.inqNote);
		dTitle = (EditText) findViewById(R.id.discTitle);
		dContent = (EditText) findViewById(R.id.discNote);
		vTitle = (TextView) findViewById(R.id.viewerTitle);
		vNote = (TextView) findViewById(R.id.viewerNote);
		vComment = (TextView) findViewById(R.id.viewerComment);
		vEdit = (EditText) findViewById(R.id.viewerEdit);
		ListView qList = (ListView) findViewById(R.id.inqList);
		ListView dList = (ListView) findViewById(R.id.discList);
		
		Button submit = (Button) findViewById(R.id.contribButton);
		submit.setOnClickListener(onInqSubmit);

		qAdapter = new InquiryAdapter();
		dAdapter = new DiscussionAdapter();
		qList.setAdapter(qAdapter);
		dList.setAdapter(dAdapter);

		qList.setOnItemClickListener(onListClickInq);
		dList.setOnItemClickListener(onListClickDisc);
		
		
		//TODO:
		//Get this working with Json out OBV rollcall/proto isnt working right now
		//Add a toast to let the idiots know theyve filled too many fields
		//
		//XML
		//Add boxes around the three lists (LOW PRIORITY)
		//Visible scroll bar (LOW PRIORITY)

	}

	@Override
	public void onDestroy() {
		super.onDestroy();
//		Helioroom.nt.disconnect();
//		Helioroom.nt.interrupt();
	}
	

	// Called when the user clicks contribute button
	private View.OnClickListener onInqSubmit = new View.OnClickListener() {
		public void onClick(View v) {
			
			// the following if loops exist to force the correct behaviour with the Contribute button
			// ie only allow a contrib if exactly Title and Note of one column are not null
			// contrib for Question
			if (dTitle.getText().toString().equals("") && dContent.getText().toString().equals("") && vEdit.getText().toString().equals("")
				&& !qTitle.getText().toString().equals("") && !qContent.getText().toString().equals("")) {
					
					Inquiry i = new Inquiry();
					i.setInqId(idCounter);
					i.setInqType("question");
					i.setInqGroup(groupId);
					i.setInqTitle(qTitle.getText().toString());
					i.setInqContent(qContent.getText().toString());

					//qAdapter.add(i);
					idCounter++;

					Event ev = new Event("submit_inquiry", i);
					ev.toJson();

					service.sendGroupChat(ev.toString());
					
			}
			// contrib for Disc
			else if (qTitle.getText().toString().equals("") && qContent.getText().toString().equals("") && vEdit.getText().toString().equals("")
				&& !dTitle.getText().toString().equals("") && !dContent.getText().toString().equals("")) {
					
					Inquiry i = new Inquiry();
					i.setInqId(idCounter);
					i.setInqType("discussion");				
					i.setInqGroup(groupId);
					i.setInqTitle(dTitle.getText().toString());
					i.setInqContent(dContent.getText().toString());

					//dAdapter.add(i);
					idCounter++;
								
					Event ev = new Event("submit_inquiry", i);
					ev.toJson();
					service.sendGroupChat(ev.toString());
			}
			// contrib for Viewer (god this is ugly)
			else if (qTitle.getText().toString().equals("") && qContent.getText().toString().equals("") &&
				dTitle.getText().toString().equals("") && dContent.getText().toString().equals("") &&
				!vEdit.getText().toString().equals("")) {
				if (!inqList.isEmpty() || !discList.isEmpty()) {		//locks contrib button if the lists are empty

					currentInq.setInqType("inquiry with comments");			
					currentInq.setInqGroup(groupId);
					currentInq.addInqComment(vEdit.getText().toString());
					vComment.setText(currentInq.getInqComments());
//					Event ev = new Event("update_inquiry", i);			//will this just create a new, or overwrite?
//					ev.toJson();
				}
			}

			else {
				Toast toast = Toast.makeText(InquiryTab.this, "Please make sure your question or comment contains both a title and a note", Toast.LENGTH_SHORT);
				toast.setGravity(Gravity.CENTER|Gravity.CENTER, 0, 0);
				toast.show();

				qTitle.setText("");
				qContent.setText("");
				dTitle.setText("");
				dContent.setText("");
				vEdit.setText("");
			}
			
			qTitle.setText("");
			qContent.setText("");
			dTitle.setText("");
			dContent.setText("");
			vEdit.setText("");
		}
	};
	
	
	// allows you to click on the inqList items to show them in the viewer
	private AdapterView.OnItemClickListener onListClickInq = new AdapterView.OnItemClickListener() {

		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {

			for (int x=0; x < inqList.size(); x++) {
				parent.getChildAt(x).setBackgroundColor(Color.argb(0,0,0,0));	//turn the background color off
//				discList(x).setBackgroundColor(Color.argb(0,0,0,0));		//how can I access the 'dom'?
			}
				
//			parent.getChildAt(selectedInqPos).setBackgroundColor(Color.argb(0,0,0,0));

			Inquiry i = inqList.get(position);
			vTitle.setText(i.getInqTitle());
			vNote.setText(i.getInqContent());
			vComment.setText(i.getInqComments());
			currentInq = i;
			vTitle.setBackgroundColor(Color.argb(255,200,0,200));
			parent.getChildAt(position).setBackgroundColor(Color.argb(255,200,0,200));
//			selectedInqPos = position;				//var to switch background color off on the next onclick
		}
	};

	private AdapterView.OnItemClickListener onListClickDisc = new AdapterView.OnItemClickListener() {

		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {

			for (int x=0; x < discList.size(); x++) {
				parent.getChildAt(x).setBackgroundColor(Color.argb(0,0,0,0));	//turn the background color off
			}
//			parent.getChildAt(selectedInqPos).setBackgroundColor(Color.argb(0,0,0,0));	//turn the background color off

			Inquiry i = discList.get(position);
			vTitle.setText(i.getInqTitle());
			vNote.setText(i.getInqContent());
			vComment.setText(i.getInqComments());
			currentInq = i;
			vTitle.setBackgroundColor(Color.argb(255,200,0,200));
			parent.getChildAt(position).setBackgroundColor(Color.argb(255,200,0,200));
//			selectedInqPos = position;	
		}
	};
	
	class InquiryAdapter extends ArrayAdapter<Inquiry> {
		InquiryAdapter() {
			super(InquiryTab.this, R.layout.row, inqList);
		}

		public View getView(int position, View convertView, ViewGroup parent) {
			View row = convertView;
			InquiryHolder holder = null;

			if (row == null) {
				LayoutInflater inflater = getLayoutInflater();
				row = inflater.inflate(R.layout.row, parent, false);
				holder = new InquiryHolder(row);
				row.setTag(holder);
			} else {
				holder = (InquiryHolder) row.getTag();
			}

			holder.populateFrom(inqList.get(position));

			return (row);
			
		}
	}

	class DiscussionAdapter extends ArrayAdapter<Inquiry> {
		DiscussionAdapter() {
			super(InquiryTab.this, R.layout.row, discList);
		}

		public View getView(int position, View convertView, ViewGroup parent) {
			View row = convertView;
			InquiryHolder holder = null;

			if (row == null) {
				LayoutInflater inflater = getLayoutInflater();
				row = inflater.inflate(R.layout.row, parent, false);
				holder = new InquiryHolder(row);
				row.setTag(holder);
			} else {
				holder = (InquiryHolder) row.getTag();
			}

			holder.populateFrom(discList.get(position));

			return (row);
		}
	}

	
	static class InquiryHolder {
		private TextView title = null;
//		private TextView content = null;
		private View row = null;

		InquiryHolder(View row) {
			this.row = row;
			title = (TextView) row.findViewById(R.id.titleRow);
//			content = (TextView) row.findViewById(R.id.contentRow);
		}

		void populateFrom(Inquiry i) {
			title.setText(i.getInqTitle());
//			content.setText(i.getInqContent());
		}
	}
	
	/** Defines callbacks for service binding, passed to bindService() */
    private ServiceConnection mConnection = new ServiceConnection() {

            @Override
            public void onServiceConnected(ComponentName className,
                            IBinder serv) {
                    LocalBinder binder = (LocalBinder) serv;
                    service = binder.getService();
                    
                    EventListener listener = new EventListener();
            		
            		listener.addResponder("submit_inquiry", new EventResponder() {
            			@Override
            			public void triggered(Event ev) {
            				//i.setInqId(some int inqId);
            				Log.d("HelioRoom", "Got inquiry!");
            				Inquiry i = (Inquiry) ev.getPayload(Inquiry.class);
            				android.os.Message msg = new android.os.Message();
            				
            			if (i.getInqType().equals("question")) {
        					//qAdapter.add(i);
            				msg.arg1 = 1;
            				msg.obj = i;
        				}
        				else if (i.getInqType().equals("discussion")) {
        					//dAdapter.add(i);
        					msg.arg1 = 2;
        					msg.obj = i;
        				}
        				else if (i.getInqType().equals("inquiry with comments")) {						//should this be the else?

        			        int listPos = 0;
        					//iterates through the inq list, checking for an Inquiry with matching inqId and inqType
        			        while (listPos < inqList.size() && i.getInqType().equals("question")) {
        						if ((inqList.get(listPos).getInqId() == i.getInqId()) && (inqList.get(listPos).getInqGroup().equals(i.getInqGroup()))) {
        							//qAdapter.insert(i, listPos);
        							msg.arg1 = 3;
        							msg.obj = i;
        							msg.arg2 = listPos;
        						}
        						listPos++;
        					}
        					listPos = 0;
        					//iterates through the disc list, checking for an Inquiry with matching inqId and inqType
        					while (listPos < discList.size() && i.getInqType().equals("discussion")) {
        						if ((inqList.get(listPos).getInqId() == i.getInqId()) && (inqList.get(listPos).getInqGroup().equals(i.getInqGroup()))) {
        							//dAdapter.insert(i, listPos);
        							msg.arg1 = 4;
        							msg.obj = i;
        							msg.arg2 = listPos;
        						}
        						listPos++;
        					}
        				}

        				xmppHandler.dispatchMessage(msg);
        				
        				qAdapter.notifyDataSetChanged();

            			
            			}
            		});
            		
            		service.getConnection().addPacketListener(listener, new PacketTypeFilter(Message.class));
                    
            }

            @Override
            public void onServiceDisconnected(ComponentName cn) {
                    
            }
    };

	@Override
	public void update(Observable observable, Object data) {
		
		Log.d("HelioRoom", "hit InquiryTabl.update()");
	}
}
