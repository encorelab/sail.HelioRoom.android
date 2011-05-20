package org.encorelab.sail.helioroom;

import org.encorelab.sail.*;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;


public class Hypothesis extends java.util.Observable implements
		XMPPThreadObserver {
	private String message = "";

	@Override
	public void parse(String xml) {
		// TODO the xml string contains the whole xml message
		// we want to get access to the body and do some JSON magic on it
		Document doc = null;
		Element body = null;
		// filter presence / message
		try {
			// convert the xml string into a dom4j Document to parse it
			doc = DocumentHelper.parseText(StringUtilities.toJava(xml));
			// if the root node is message we try to retrieve the body
			if (doc.getRootElement().getName().equals("message")) {
				// retrieve the body element which is the actual message
				body = (Element) doc.getRootElement().element("body");
				
				// store the message
				
				Event ev = Event.fromJson(body.getText());
				
				setMessage(ev.getPayloadAsString());
				
				/*instanceId = firstBodyEl.getName();
				configureWindows(firstBodyEl.element("windows").asXML());
				configure(firstBodyEl.element("config").asXML());*/
				
				// update trigger for observers
				this.setChanged();
				this.notifyObservers(this);
			}
		} catch (DocumentException e) {
			e.printStackTrace();
		}

		//setMessage(xml);

		// I saw this in gugo's code and it seems to
		// be the message that triggers observers' update(...) function
		//this.setChanged();
		//this.notifyObservers(this);
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

}
