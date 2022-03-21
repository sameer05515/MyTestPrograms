package com.p.xml;

public interface BasicXMLMethodRequired {

	public static enum Type {
		NODE(), ATTRIBUTE(), CDATA();
	}

	public String getName();

	public Type getType();
}
