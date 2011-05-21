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



public class InquiryTab extends Activity {

	EditText qTitle = null;
	EditText qContent = null;
	EditText dTitle = null;
	EditText dContent = null;
	TextView vTitle = null;
	TextView vNote = null;
	TextView vComment = null;
	EditText vEdit = null;
	
	List<Inquiry> inqList = new ArrayList<Inquiry>();
	List<Inquiry> discList = new ArrayList<Inquiry>();
	
	InquiryAdapter qAdapter = null;
	DiscussionAdapter dAdapter = null;

	Inquiry currentInq = null;			//used for Inq Disc Viewer
	
//	int inqId = 0; This would need to be updated on Matt's end
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
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
		//Get this working with Json out
		//Do a little general formatting cleanup		
		//Lock down contrib if there are no i objects
		//
		//
		//JAVA
		//Lock the contribute button?
		//
		//
		//XML
		//Increase font size of question/ideas list items
		//Add boxes around the three lists (LOW PRIORITY)
		//Make contribute active (when title+note OR text entered in viewer comment)
		//Add pencil icons (LOW PRIORITY) drawableleft in xml
		//Visible scroll bar (LOW PRIORITY)
		//Change color BLUE to PURPLE (LOW PRIORITY)
		//Color the text/background of the viewer comments?
		//
		//EITHER/BOTH
		//Highlight Q/D (one in viewer) should be colored as hex FF99FF, also highlight title in Viewer
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
					//i.setInqId(some int inqId);
					i.setInqType("question");
					// i.setGroup(inqGroup.getText().toString());
					i.setInqTitle(qTitle.getText().toString());
					i.setInqContent(qContent.getText().toString());
					// i.setParent(inqParent.getText().toString());
					qAdapter.add(i);
					//send to chat room (change to referencing the qList) FIXME
					//Helioroom.nt.sendGroupChat(qTitle.getText().toString()+","+qContent.getText().toString());
			}
			// contrib for Disc
			else if (qTitle.getText().toString().equals("") && qContent.getText().toString().equals("") && vEdit.getText().toString().equals("")
				&& !dTitle.getText().toString().equals("") && !dContent.getText().toString().equals("")) {
					Inquiry i = new Inquiry();
					//i.setInqId(some int inqId);
					i.setInqType("discussion");				
					// i.setGroup(inqGroup.getText().toString());
					i.setInqTitle(dTitle.getText().toString());
					i.setInqContent(dContent.getText().toString());
					// i.setParent(inqParent.getText().toString());
					dAdapter.add(i);
					//send to chat room (change to referencing the dList) FIXME
					//Helioroom.nt.sendGroupChat(dTitle.getText().toString()+","+dContent.getText().toString());
			}
			// contrib for Viewer (god this is ugly)
			// FIXME need to lock this one down so that it cant be accessed when there are no i objects
			else if (qTitle.getText().toString().equals("") && qContent.getText().toString().equals("") &&
				dTitle.getText().toString().equals("") && dContent.getText().toString().equals("") &&
				!vEdit.getText().toString().equals("")) {
					// i.setId(inqId.getText().toString());
					currentInq.setInqType("inquiry with comments");			
					// i.setGroup(inqGroup.getText().toString());
					currentInq.addInqComment(vEdit.getText().toString());
					vComment.setText(currentInq.getInqComments());
			}

			else {
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

			Inquiry i = inqList.get(position);
			vTitle.setText(i.getInqTitle());
			vNote.setText(i.getInqContent());
			vComment.setText(i.getInqComments());
			currentInq = i;
//			vTitle.setTextColor(Color.BLUE);
//			parent.getChildAt(position).setBackgroundColor(Color.BLUE); //this needs to reset the colors on all the other ones
		}
	};

	private AdapterView.OnItemClickListener onListClickDisc = new AdapterView.OnItemClickListener() {

		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {

			Inquiry i = discList.get(position);
			vTitle.setText(i.getInqTitle());
			vNote.setText(i.getInqContent());
			vComment.setText(i.getInqComments());
			currentInq = i;
//			vTitle.setTextColor(Color.BLUE);	//maybe better to do text color
//			parent.getChildAt(position).setBackgroundColor(Color.BLUE);
		}
	};
	
	class InquiryAdapter extends ArrayAdapter<Inquiry> {
		InquiryAdapter() {
			super(InquiryTab.this, R.layout.row, inqList); // questionable
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

}
