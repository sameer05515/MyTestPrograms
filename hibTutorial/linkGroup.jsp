<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page import="com.p.xml.*"%>
<%@page import="com.p.xml.bo.*"%>
<%@page import="java.io.*"%>
<%@page import="java.util.*"%>
<%@page import="javax.xml.parsers.*"%>
<%@page import="org.w3c.dom.*"%>
<%@page import="org.xml.sax.*"%>

<!--
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.p.xml.bo.Link;
import com.p.xml.bo.LinkGroup;
-->
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Link Group Explorer</title>

<style type="text/css">
body {
font-family: verdana; font-size: 11px;
}

.btn {

FONT: 12px Verdana, Arial, Helvetica, sans-serif;
BORDER-TOP:solid 1px #000000;
BORDER-BOTTOM:solid 1px #000000;
BORDER-LEFT:solid 1px #000000;
BORDER-RIGHT:solid 1px #000000;
BACKGROUND-COLOR:#E8ECF0;

}

.linkGroup {

/**
FONT: 12px Verdana, Arial, Helvetica, sans-serif;
BORDER-TOP:solid 1px #000000;
BORDER-BOTTOM:solid 1px #000000;
BORDER-LEFT:solid 1px #000000;
BORDER-RIGHT:solid 1px #000000;
overflow-x:auto;
*/
overflow:scroll;

BACKGROUND-COLOR:#E8ECF0;

}

.leftmenu a:link {
	background: url("images/arrowbullet.png") no-repeat 0px 0px;
	font: 13px/21px verdana, helvetica, arial, sans-serif; 
	width: 100%; 
	color: rgb(0, 0, 0); 
	padding-left: 15px; 
	text-decoration: none; 
	/**margin-top: 2px; **/
	margin-left: 14px; 
	float: left; 
	display: block; 
	font-size-adjust: none; 
	font-stretch: normal;
}
.leftmenu a:visited {
	background: url("images/arrowbullet.png") no-repeat 0px 4px; 
	font: 13px/21px verdana, helvetica, arial, sans-serif; width: 100%; color: rgb(0, 0, 0); padding-left: 15px; text-decoration: none; margin-top: 2px; margin-left: 14px; float: left; display: block; font-size-adjust: none; font-stretch: normal;
}
.leftmenu a:active {
	background: url("images/arrowbullet.png") no-repeat 0px 4px; 
	font: 13px/21px verdana, helvetica, arial, sans-serif; 
	width: 100%; 
	color: green; 
	padding-left: 15px; 
	text-decoration: underline; 
	margin-top: 2px; 
	margin-left: 14px; 
	float: left; 
	display: block; 
	font-size-adjust: none; 
	font-stretch: normal;
}
.leftmenu a:hover {
	background: url("images/arrowbullet.png") no-repeat 0px 4px; 
	font: 13px/21px verdana, helvetica, arial, sans-serif; 
	width: 100%; 
	color: green; 
	padding-left: 15px; text-decoration: underline; margin-top: 2px; margin-left: 14px; float: left; display: block; font-size-adjust: none; font-stretch: normal;
}
</style>

<script>
var delay = 300;
function winClose() {
	window.close();
}

function runTimer() {
	if(delay == 0){
		myFunction();
		//delay = 10;
		}else {
		delay--;
		//document.getElementById('btnclose').value = 'Prem('+delay+')';
		document.getElementById('btnclose').innerText = 'Prem('+delay+')';
		setTimeout('runTimer()', 1000);
	}
}
function myFunction() {
    location.reload();
}
</script>


</head>
<body onload="runTimer()">
	<!--
	<button onclick="myFunction()">Reload page</button>
	<input class="btn" id="btnclose" type="button" value="close(10)" onclick="myFunction()">
	-->
	<%=request.getContextPath() %>
	
	
	<div class="btn" id="btnclose" width="100" height="100"></div>

	<!--<div class="linkGroup" id="linkGroupContainer" width="200" height="300" >-->
	<%
	String[] filePaths={"/xml/links.xml","/xml/links1.1.xml"};
	String filePath="";
	String[] version={"1.0","1.1"};
	String currentVersion=version[1];
	String real="";
		try {
			// create an instance
			
			DomParserVersion dpe=null;
			if(currentVersion!=null&&currentVersion.trim().equalsIgnoreCase(version[0])){
				filePath=filePaths[0];
				real=request.getSession().getServletContext().getRealPath(filePath);
				out.println(real);
				System.out.println(real);
				dpe = new DomParserExample(real);
				
				
			}else if(currentVersion!=null&&currentVersion.trim().equalsIgnoreCase(version[1])){
				filePath=filePaths[1];
				real=request.getSession().getServletContext().getRealPath(filePath);
				out.println(real);
				System.out.println(real);
				dpe = new DomParserExampleNewVersion(real);
				
			}
			
			out.println("DomParserVersion : "+dpe.getVersion()+" : version "+dpe.getVersion().getVersionNumber());

			// call run example
			List<LinkGroup> linkGroups = dpe.fetchAllLinkGroups();

			for (LinkGroup lg : linkGroups) {
			%>
			<ul>
				<li><a><%=lg.getName() %></a>
					<% if(lg.getLinks()!=null && lg.getLinks().size()>0){ %>
					<ul>
						<% for(Link l:lg.getLinks()){ %>
						<li>
							<div class="leftmenu">
								<a href="<%=l.getLink()%>"><%=l.getLink()%></a>
							</div>
						</li>
						<%} %>
					
					</ul>
					<%} %>				
				</li>			
			</ul>			
			<%
				//out.println(lg);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	%>
	<!--</div>-->
</body>
</html>