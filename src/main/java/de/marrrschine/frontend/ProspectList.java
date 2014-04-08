package de.marrrschine.frontend;

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ProspectList {
	private ArrayList<Prospect> prospectList;

	public ProspectList() {
		super();
		this.prospectList = new ArrayList<Prospect>();
	}

	public ProspectList(ArrayList<Prospect> prospectList) {
		super();
		this.prospectList = prospectList;
	}

	public ArrayList<Prospect> getProspectList() {
		return prospectList;
	}

	public void setProspectList(ArrayList<Prospect> prospectList) {
		this.prospectList = prospectList;
	}
	
	public void addProspect(Prospect prospect){
		this.prospectList.add(prospect);
	}
}
