package com.p.xml.bo;

public class Link {

	private String link;

	public Link(String link) {
		this.link = link;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		StringBuffer sb = new StringBuffer();

		sb.append("[ Link url=\"" + link + "\"]");

		return sb.toString();
	}

}
