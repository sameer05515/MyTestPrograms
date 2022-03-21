package com.p.xml;

import java.util.List;

import com.p.xml.bo.LinkGroup;

public interface DomParserVersion {

	public static enum Version {
		Tiger("1.0"), Dracula("1.1");
		private String versionNumber;

		Version(String versionNumber) {
			this.versionNumber = versionNumber;
		}

		public String getVersionNumber() {
			return versionNumber;
		}

	}

	public Version getVersion();

	public void runExample();
	
	public List<LinkGroup> fetchAllLinkGroups();
}
