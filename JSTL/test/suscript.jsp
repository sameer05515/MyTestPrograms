<%@taglib prefix="test" uri="/WEB-INF/SubstrDescriptor.tld"%>
<%@taglib prefix="prem" uri="/WEB-INF/FileResolvor.tld"%>
<html>
<head>
    <title>JSP Custom Taglib example: Substr function</title>
	<link rel="stylesheet" href="http://127.0.0.1:8080/bce/struts2/link.css">
	<!-- -->
	<style type="text/css">
		.fileClass:hover,.folderClass:hover,.fileClass:focus,.folderClass:focus{
			FONT: 18px Verdana, Arial, Helvetica, sans-serif;
		}
	</style>
	<link rel="stylesheet" href="link.css">
		<script src="http://127.0.0.1:8080/bce/jquery/jquery-2.0.3.min.js" type="text/javascript"></script>
		
		<script>
		var currenturl="";
		function showGame(whichgame){
		  var source=whichgame.getAttribute("href");
			//-------------
			var idAttrValue=whichgame.getAttribute("id");
			var placeHolder="myanch";
			var res=idAttrValue.substr(idAttrValue.indexOf(placeHolder) + placeHolder.length);
			res=parseInt(res);
			selectedVal=res;
			//--- To set focus of selected element
			whichgame.focus();
			//alert("selected id == "+res);
			//-------------------
		  source="http://127.0.0.1:8080/bce/fileWriter?documentId="+source;
		  currenturl=source;
		  var game=document.getElementById("gameHolder");
		  var clone=game.cloneNode(true);
		  clone.setAttribute('src',source);
		  game.parentNode.replaceChild(clone,game);
		  document.getElementById("selectedItemName").innerHTML='Currently Selected : '+whichgame.innerHTML;
		}
		
		window.onload=function() {
			  var aTags=document.getElementsByTagName('a');
			  for (var i=aTags.length-1; i>=0; i--) {
				
				aTags[i].onclick = function() {
					showGame(this);					
					return false;
				  };				
			  }
			  
			}
		</script>
		
		<script>
		var selectedVal=0;
		$(document).ready(function(){
			//-----------------------------------
			var optionTexts = [];
			
			var totalValLength=0;
			//$("ul li").each(function() { optionTexts.push($(this).text()) });
			//$("ul li").each(function() { optionTexts.push(this); });
			//$("ul li").each(function() {  
				//$(this).children('a').each(function () {
					//optionTexts.push($(this).attr('href'));
				//});
			//});
			
			var idCounter=0;
			$("ul li").each(function() {  
				$(this).children('a').each(function () {
				
					if($(this).hasClass('fileClass')==false){
						return;
					}
					
					$(this).attr("id","myanch"+idCounter);
					optionTexts.push(this);
					idCounter=idCounter+1;
				});
			});
			
			$("#nextPDF").click(function() {
				//alert(selectedVal+"");
				var nextValue=(selectedVal + (optionTexts.length) + 1)%(optionTexts.length);
				//totalValLength=optionTexts.length;
				//var nextValue=(selectedVal + totalValLength  ) +1;
				//nextValue=nextValue%totalValLength;
				
				nextValue=parseInt(nextValue);
				selectedVal=nextValue;
				
				
				showGame(optionTexts[nextValue]);
			});
			
			$("#prevPDF").click(function() {
				//alert(selectedVal+"");
				var nextValue=(selectedVal + (optionTexts.length) - 1)%(optionTexts.length);
				//totalValLength=optionTexts.length;
				//var nextValue=(selectedVal + totalValLength  ) +1;
				//nextValue=nextValue%totalValLength;
				
				nextValue=parseInt(nextValue);
				selectedVal=nextValue;
				
				
				showGame(optionTexts[nextValue]);
			});
			
			$("#repeatPDF").click( function() {
				jumppTo(1-1);
			});
			
			function jumppTo(steppVal) {				
				var nextValue=(selectedVal + (optionTexts.length) +steppVal)%(optionTexts.length);
				//totalValLength=optionTexts.length;
				//var nextValue=(selectedVal + totalValLength  ) +1;
				//nextValue=nextValue%totalValLength;				
				nextValue=parseInt(nextValue);
				selectedVal=nextValue;				
				showGame(optionTexts[nextValue]);
			}
			
			var event2key = {'97':'a', '98':'b', '99':'c', '100':'d', '101':'e', '102':'f', '103':'g', '104':'h', '105':'i', '106':'j', '107':'k', '108':'l', '109':'m', '110':'n', '111':'o', '112':'p', '113':'q', '114':'r', '115':'s', '116':'t', '117':'u', '118':'v', '119':'w', '120':'x', '121':'y', '122':'z', '37':'left', '39':'right', '38':'up', '40':'down', '13':'enter'};
			
			$('body').keypress(function(event) {			
					if(String.fromCharCode(event.charCode)=='n'){
					 jumppTo(1);
					 }
					 if(String.fromCharCode(event.charCode)=='p'){
					 jumppTo(-1);
					 }
					if(String.fromCharCode(event.charCode)=='r'){
					 jumppTo(1-1);
					 }
				});
			//----------------------------------

			var state = true;
			$( "#btnListOfCategories" ).text("Hide");
			$( "#btnListOfCategories" ).click(function() {
				if ( state ) {
					
					$( "#btnListOfCategories" ).text("Show");
					$('#rowSecond').attr('colspan','2');
				} else {
					
					$( "#btnListOfCategories" ).text("Hide");
					$('#rowSecond').attr('colspan','1');
				}
				state = !state;
				//$("#textSectionListOfCategories").toggle("slow");
				//$("#rowFirst").toggle("slow");
				$("#rowFirst").toggle();
			});
			
			$("#btnShowDocsInFullScreen").toggle();
			$("#btnShowDocsInFullScreen").click(function(){
				//$(".iframe").colorbox({iframe:true, width:"100%", height:"100%"}).call();
			});
		});
		</script>
		
		<style>
			#selectedItemName{
					font:15px/1.0 times new roman;
					color:white;
					background-color:#66cc55;
					padding:2px 2px 2px;
					word-break: break-all;
					white-space: normal;
				}
				
			body {
					text-decoration: deeppink;
					background-color: #d2b31c;
					background-image: url(http://127.0.0.1:8080/bce/css/prem.jpg);
					border: 1px dotted gray;
					padding: 1px 1px 1px 1px;					
					border-style: dashed;
					border-color: red;
					/*------------------*/
					::-webkit-scrollbar { width: 3px; height: 3px;}
					::-webkit-scrollbar-button {  background-color: #666; }
					::-webkit-scrollbar-track {  background-color: #999;}
					::-webkit-scrollbar-track-piece { background-color: #ffffff;}
					::-webkit-scrollbar-thumb { height: 50px; background-color: #666; border-radius: 3px;}
					::-webkit-scrollbar-corner { background-color: #999;}}
					::-webkit-resizer { background-color: #666;}
				}
				
				a{
					word-break: break-all;
					white-space: normal;

				}
		</style>

	<!-- -->
</head>
<body>
    SUBSTR(GOODMORNING, 1, 6) is 
    <!--
	<font color="blue">
    <test:substring input="GOODMORNING" start="1" end="6"/>
	</font>
	-->
	
	<br/>
	<table border="3" width="100%" height="100%" >
			<tr>
				<td colspan="2" height="20">
					<!--
					<marquee direction="right" loop="20"><p id="selectedItemName" style="text-align: center">Currently Selected : None</p></marquee>
					-->
					<span>
						<button id="btnListOfCategories">show</button>
						<button id="btnShowDocsInFullScreen">show Docs in full screen</button>
					</span>
					<span  style='float: left;' id="prevPDF"><img src="http://127.0.0.1:8080/bce/images/prev_to_summary.png" width="35" height="15"/></span>
					
					<span  style='float: center;' id="repeatPDF"><img src="http://127.0.0.1:8080//bce/images/icon_refresh.png" width="35" height="15"/></span>
					
					<span  style='float: right;' id="nextPDF"><img src="http://127.0.0.1:8080/bce/images/next_to_summary.png" width="35" height="15"/></span>
					
					<span id="selectedItemName" style="text-align: center">Currently Selected : None</span>
				</td>
			</tr>
			<tr>
				<td width="10%" valign="top" id="rowFirst">
					<div id="textSectionListOfCategories" style=" height:100%; width:100%; overflow:scroll;">
						<table>
						
						<!--
						<tr><td>
						<prem:fileResolve base="d:/ebooks/db" traverseSubFolder="true" allowedExtentions=".pdf"/>
						</td></tr>
						
						<tr><td>
						<test:fileResolve base="d:/ebooks/AI" traverseSubFolder="true" allowedExtentions=".pdf"/>
						</td></tr>
						-->
						
						<%
						  String myBasee=request.getParameter("myBasee");
						  String myTraverseSubFolder="true";
						  String myAllowedExtentions=request.getParameter("myAllowedExtentions");
						%>
						
						<tr><td>
						<test:fileResolve base="<%=myBasee%>" traverseSubFolder="<%=myTraverseSubFolder%>" allowedExtentions="<%=myAllowedExtentions%>"/>
						</td></tr>
						
						<!--
						<tr><td>
						<test:fileResolve base="C:/APPLN_SERVERS/apache-tomcat-6.0.35/apache-tomcat-6.0.35/webapps/bce/struts2/basic"
										  traverseSubFolder="true" 
										  allowedExtentions=".pdf"/>
						</td></tr>
						
						<tr><td>
						<test:fileResolve base="C:/Users/VINU/Desktop/comics"
										  traverseSubFolder="true" 
										  allowedExtentions=".pdf"/>
						</td></tr>
						-->
							
						</table >
					</div>
				</td>
				
				<td width="90%" valign="top" id="rowSecond">
					<embed id="gameHolder" src="" allowfullscreen="true" width="100%" height="100%" >
				</td>
			</tr>
		</table >
	
</body>
</html>