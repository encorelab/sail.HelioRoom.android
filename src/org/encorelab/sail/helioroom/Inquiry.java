package org.encorelab.sail.helioroom;

public class Inquiry {
	private int inqId;
	private String inqType = "";
	private String inqGroup = "";
	private String inqTitle = "";
	private String inqContent = "";
	private String inqComment = "";
	
	public void setInqId(int inqId) {
		this.inqId = inqId;
	}
	public int getInqId() {
		return inqId;
	}
	public void setInqType(String inqType) {
		this.inqType = inqType;
	}
	public String getInqType() {
		return inqType;
	}
	public void setInqGroup(String inqGroup) {
		this.inqGroup = inqGroup;
	}
	public String getInqGroup() {
		return inqGroup;
	}
	public void setInqTitle(String inqTitle) {
		this.inqTitle = inqTitle;
	}
	public String getInqTitle() {
		return inqTitle;
	}
	public void setInqContent(String inqContent) {
		this.inqContent = inqContent;
	}
	public String getInqContent() {
		return inqContent;
	}
	public void addInqComment(String comment) {
//		this.inqComment += this.inqGroup + ":  " + comment + "\n";
		this.inqComment += comment + "\n";
	}
	public String getInqComments() {
		return inqComment;
	}

}
