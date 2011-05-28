package org.encorelab.sail.helioroom;

//we're really only using obsContent right now
public class Observation {
	private int obsId;
	private String obsGroup = "";
	private int obsContent = 0;			//what's the best way to represent this? For now:
										//a pair of digits is better ie 25 = orange closer than blue
	public void setObsId(int obsId) {
		this.obsId = obsId;
	}
	public int getObsId() {
		return obsId;
	}
	public void setObsGroup(String obsGroup) {
		this.obsGroup = obsGroup;
	}
	public String getObsGroup() {
		return obsGroup;
	}
	public void setObsContent(int obsContent) {
		this.obsContent = obsContent;
	}
	public int getObsContent() {
		return obsContent;
	}

}
