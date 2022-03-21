<HTML>
<HEAD>
<SCRIPT LANGUAGE="JAVASCRIPT">
function doZoomback(sourceobj, destinationObj)
{
  
destinationObj.value=sourceobj.value;
window.close();
}
function doZoom(obj)
{
window.open("Zoomin.jsp?txttest=" + obj.value)
}
</SCRIPT>
</HEAD>
<BODY>
  
<TEXTAREA name="txtTest"><%=request.getParameter("txttest")%></TEXTAREA>
<input type="submit" name="cmdOK" value="OK" on click="parent.doZoomback(txtTest,opener.document.all.txttest)">
</BODY>
</HTML>