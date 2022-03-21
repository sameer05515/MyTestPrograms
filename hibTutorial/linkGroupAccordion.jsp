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

	<link href="jquery-ui.css" rel="stylesheet">
	
	<script src="external/jquery/jquery.js"></script>
<script src="jquery-ui.js"></script>
<script>

$( "#accordion" ).accordion();



/*var availableTags = [
	"ActionScript",
	"AppleScript",
	"Asp",
	"BASIC",
	"C",
	"C++",
	"Clojure",
	"COBOL",
	"ColdFusion",
	"Erlang",
	"Fortran",
	"Groovy",
	"Haskell",
	"Java",
	"JavaScript",
	"Lisp",
	"Perl",
	"PHP",
	"Python",
	"Ruby",
	"Scala",
	"Scheme"
];
$( "#autocomplete" ).autocomplete({
	source: availableTags
});



$( "#button" ).button();
$( "#radioset" ).buttonset();



$( "#tabs" ).tabs();



$( "#dialog" ).dialog({
	autoOpen: false,
	width: 400,
	buttons: [
		{
			text: "Ok",
			click: function() {
				$( this ).dialog( "close" );
			}
		},
		{
			text: "Cancel",
			click: function() {
				$( this ).dialog( "close" );
			}
		}
	]
});*/

// Link to open the dialog
/*$( "#dialog-link" ).click(function( event ) {
	$( "#dialog" ).dialog( "open" );
	event.preventDefault();
});



$( "#datepicker" ).datepicker({
	inline: true
});



$( "#slider" ).slider({
	range: true,
	values: [ 17, 67 ]
});



$( "#progressbar" ).progressbar({
	value: 20
});



$( "#spinner" ).spinner();



$( "#menu" ).menu();



$( "#tooltip" ).tooltip();



$( "#selectmenu" ).selectmenu();*/


// Hover states on the static widgets
/*$( "#dialog-link, #icons li" ).hover(
	function() {
		$( this ).addClass( "ui-state-hover" );
	},
	function() {
		$( this ).removeClass( "ui-state-hover" );
	}
);*/
</script>

	
	<style>
	body{
		font: 62.5% "Trebuchet MS", sans-serif;
		margin: 50px;
	}
	.demoHeaders {
		margin-top: 2em;
	}
	#dialog-link {
		padding: .4em 1em .4em 20px;
		text-decoration: none;
		position: relative;
	}
	#dialog-link span.ui-icon {
		margin: 0 5px 0 0;
		position: absolute;
		left: .2em;
		top: 50%;
		margin-top: -8px;
	}
	#icons {
		margin: 0;
		padding: 0;
	}
	#icons li {
		margin: 2px;
		position: relative;
		padding: 4px 0;
		cursor: pointer;
		float: left;
		list-style: none;
	}
	#icons span.ui-icon {
		float: left;
		margin: 0 4px;
	}
	.fakewindowcontain .ui-widget-overlay {
		position: absolute;
	}
	select {
		width: 200px;
	}
	</style>

<script>
var delay = 10;
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
	<div class="btn" id="btnclose" width="100" height="100"></div>

	<!--<div class="linkGroup" id="linkGroupContainer" width="200" height="300" >-->
	<h2 class="demoHeaders">Accordion</h2>
	<div id="accordion">

	<%
		try {
			// create an instance
			DomParserExample dpe = new DomParserExample();

			// call run example
			List<LinkGroup> linkGroups = dpe.fetchAllLinkGroups();

			for (LinkGroup lg : linkGroups) {
			%>
			
				<h3><a><%=lg.getName() %></a></h3>
					<% if(lg.getLinks()!=null && lg.getLinks().size()>0){ %>
					<div>
					<ul>
						<% for(Link l:lg.getLinks()){ %>
						<li>
							
							<a href="<%=l.getLink()%>"><%=l.getLink()%></a>
							
						</li>
						<%} %>
					
					</ul>
					</div>
					<%} %>				
					
						
			<%
				//out.println(lg);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	%>
	</div>

	<!--</div>-->
</body>
</html>