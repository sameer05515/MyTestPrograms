<script>
function winOpen()
{
    window.open("linkGroup.jsp");
}
</script>

<a href="javascript:;" onclick="winOpen()">Pop Up</a>


<link rel="stylesheet" href="http://code.jquery.com/ui/1.10.2/themes/smoothness/jquery-ui.css" />
<script src="http://code.jquery.com/jquery-1.9.1.js"></script>
<script src="http://code.jquery.com/ui/1.10.2/jquery-ui.js"></script>
<link rel="stylesheet" href="/resources/demos/style.css" />
<script>
//$(function() {
//$( "#dialog" ).dialog();
//});
$(function() {
$( "#dialog" ).hide();
});
function openAnother() {
$( "#dialog" ).dialog();
}
</script>

<div id="dialog" title="Basic dialog">
//your form layout
<%@include file="linkGroup.jsp" %>
</div>

<a href="javascript:;" onclick="openAnother()">Pop Up</a>


<a href='#' onclick='javascript:window.open("linkGroup.jsp", "_blank", "scrollbars=1,resizable=1,height=300,width=450");' title='Pop Up'>Pop Up</a>



 <script type="text/javascript" lnaguage="javascript">
 $(function()
  {
  $("#datepicker").datepicker(
  {
  showOn:"both",
  buttonImage:"image.jpg",
  dateFormat:"yy-mm-dd",
  buttonImageOnly:false,
  //minDate:+0, //you do not want to show previous date.
  //maxDate:+0   // you do not want to show next day.
  });
  });
 </script>
 
 
 <br/><input type="text" name="calendar" id="datepicker">
 