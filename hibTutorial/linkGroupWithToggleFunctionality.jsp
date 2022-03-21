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
<script type="text/javascript" src="./a/jquery-1.3.2.min.js"></script>
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
	background: url("images/arrowbullet.png") no-repeat 0px 4px;
	font: 13px/21px verdana, helvetica, arial, sans-serif; 
	width: 100%; 
	color: rgb(0, 0, 0); 
	padding-left: 15px; 
	text-decoration: none; 
	margin-top: 2px; 
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

<style type="text/css">
	.htmlClass{
		padding:8px;
		border:1px solid blue;
		margin-bottom:8px;
	}
</style>

<script type="text/javascript">
	$("div").each(function() {
		//$( this ).toggleClass( "example" );
		if($(this).hasClass('prem')==false){
			return;
		}
		
		var divKiID=$(this).attr('id');
		
		var spanKiID='span'+divKiID.substr('div'.length);
		//$('#'+divKiID).live('click',function () {		
		  //$(this).toggle();		  
		//});
		
		$('#'+spanKiID).live('click',function () {		
		  $('#'+divKiID).toggle();		  
		});
	  });
</script>

<script>
var delay = 300;
function winClose() {
	window.close();
}

function runTimer() {
	if(delay == 0)
	myFunction();
	//delay = 10;
	else {
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
	String filePath="/xml/links.xml";
	String real=request.getSession().getServletContext().getRealPath(filePath);
	out.println(real);
	System.out.println(real);
		try {
			// create an instance
			DomParserExample dpe = new DomParserExample(real);

			// call run example
			List<LinkGroup> linkGroups = dpe.fetchAllLinkGroups();

			int count=1;
			for (LinkGroup lg : linkGroups) {
			%>
			<ul>
				<li>
					<div>
					<br><span id="<%="span"+count%>"><%=lg.getName() %></span>
					<% if(lg.getLinks()!=null && lg.getLinks().size()>0){ %>
					<div id="<%="div"+count%>" class="htmlClass leftmenu1 prem">
					<ul>
					
						<% for(Link l:lg.getLinks()){ %>
						<li>
							
								<a href="<%=l.getLink()%>"><%=l.getLink()%></a>
							
						</li>
						<%} %>
					
					</ul>
					</div>
					<%} %>	
					</div>
				</li>			
			</ul>			
			<%
				//out.println(lg);
				count++;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	%>
	<!--</div>-->
</body>
</html>