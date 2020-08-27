package com.p.html.code.generator;

public class Main {

	public static void main(String[] args) {
//		HtmlCodeGenerator.generateLinksHtmlFiles(LINKS_TEXT_ARRAY, "D:\\Prem\\CUST-INST\\apache-tomcat-8.5.56-windows-x86\\webapps\\my-pages\\other-sample-application\\Menu-Examples");
		HtmlCodeGenerator.generateLinksHtmlText(LINKS_TEXT_ARRAY);
	}

	private static final String[] LINKS_TEXT_ARRAY = { "Icon-Bar", "Menu-Icon", "Accordion", "Tabs", "Vertical-Tabs",
			"Tab-Headers", "Full-Page-Tabs", "Hover-Tabs", "Top-Navigation", "Responsive-Topnav", "Navbar-with-Icons",
			"Search-Menu", "Search-Bar", "Fixed-Sidebar", "Side-Navigation", "Responsive-Sidebar",
			"Fullscreen-Navigation", "Off-Canvas-Menu", "Hover-Sidenav-Buttons", "Sidebar-with-Icons",
			"Horizontal-Scroll-Menu", "Vertical-Menu", "Bottom-Navigation", "Responsive-Bottom-Nav",
			"Bottom-Border-Nav-Links", "Right-Aligned-Menu-Links", "Centered-Menu-Link", "Equal-Width-Menu-Links",
			"Fixed-Menu", "Slide-Down-Bar-on-Scroll", "Hide-Navbar-on-Scroll", "Shrink-Navbar-on-Scroll",
			"Sticky-Navbar", "Navbar-on-Image", "Hover-Dropdowns", "Click-Dropdowns", "Dropdown-in-Topnav",
			"Dropdown-in-Sidenav", "Resp-Navbar-Dropdown", "Subnavigation-Menu", "Dropup", "Mega-Menu", "Mobile-Menu",
			"Curtain-Menu", "Collapsed-Sidebar", "Collapsed-Sidepanel", "Pagination", "Breadcrumbs", "Button-Group",
			"Vertical-Button-Group", "Sticky-Social-Bar", "Pill-Navigation", "Responsive-Header" };

}
