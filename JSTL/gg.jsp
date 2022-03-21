<html>
     <head>
     <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
     <title>JSP Page</title>
     <script type="text/javascript">

      function loadXMLDoc()
      {
        var xmlhttp;
        var config=document.getElementById('configselect').value;
        //var url="http://127.0.0.1:8080/bce/struts2/my.jsp?allowedExtentions=.pdf&&travSubFolder=true&&catId=d:/ebooks/db";
		var url="Subscript.jsp";
        if (window.XMLHttpRequest)
        {
            xmlhttp=new XMLHttpRequest();
        }
        else
        {
            xmlhttp=new ActiveXObject("Microsoft.XMLHTTP");
        }
        xmlhttp.onreadystatechange=function()
        {
            if (xmlhttp.readyState==4 && xmlhttp.status==200)
            {
                document.getElementById("myDiv").innerHTML=xmlhttp.responseText;
            }
        }

        xmlhttp.open("GET", url, true);
        xmlhttp.send();
}
</script>        

</head>

<body>
  <h2 align="center">Saved Configurations</h2>
   Choose a configuration to run: 
  <select name="configselect" width="10">
    <option selected value="select">select</option>
    <option value="Config1">config1</option>
    <option value="Config2">config2</option>
    <option value="Config3">config3</option>
  </select> 
  <button type="button" onclick='loadXMLDoc()'> Submit </button>
  <div id="myDiv">
    <h4>Get data here</h4>
  </div>
 </body>
</html>