package com.p.xml.bo;

import java.util.ArrayList;
import java.util.List;

public class LinkGroup {

	private String name;
	private List<Link> links = new ArrayList<Link>();

	public LinkGroup(String name, List<Link> links) {
		super();
		this.name = name;
		this.links = links;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Link> getLinks() {
		return links;
	}

	public void setLinks(List<Link> links) {
		this.links = links;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		StringBuffer sb = new StringBuffer();
		sb.append("[LinkGroup name=" + name + "\nlinks = " + "\n");
		sb.append("{");
		for (Link l : links) {
			sb.append(l + "\n");
		}
		sb.append("}");

		return sb.toString();
	}

}
