
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
<form>
<input type="textbox" value="a very long text that i want to be displayed in text area" name="txttest">
<input type="submit" name="cmdOK" value="Zoom" on click="doZoom(txttest)">
</form>
</BODY>
</HTML>