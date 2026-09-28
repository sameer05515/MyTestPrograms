<!--
To change this template, choose Tools | Templates
and open the template in the editor.
-->
<!DOCTYPE html>
<html>
    <head>
        <link rel="stylesheet" type="text/css;" href="http://127.0.0.1:8080/MyShopiingCart/lipstick.css" />
        <title>Shopping Cart Application</title>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    </head>
    <body bgcolor="#CCFFFF">
    <center>
        <h1 id="title">
            <u>Shopping Cart Application</u>
        </h1>
    </center> 
    <script language="javascript">
      function validate(){
	  var myBasee1=document.frm.myBasee.value;
	  var myAllowedExtentions1=document.frm.myAllowedExtentions.value;
          if(document.frm.myBasee.value==""||document.frm.myAllowedExtentions.value==""){
              alert("myBasee or myAllowedExtentions cannot be blank!!");
              return;
          }
          
          document.frm.action="colorbox.jsp";
          document.frm.submit();
		  
			//ajaxcall(myBasee1,myAllowedExtentions1);
		  
      }  
	  
	  function ajaxcall(myBasee1,myAllowedExtentions1){
		var xreq;
			if(myBasee1=="" || myAllowedExtentions1=="")
			{
			document.getElementById("showtext").innerHTML="";
			return;
			}
			if(window.XMLHttpRequest)
			{
			xreq=new XMLHttpRequest();
			}
			else
			{
			xreq=new ActiveXObject("Microsoft.XMLHTTP");
			}
			xreq.onreadystatechange=function ()
			{
			if( (xreq.readyState==4) && (xreq.status==200) )
			{
			document.getElementById("showtext").innerHTML 
											   =xreq.responseText;

			}
			}
			xreq.open("post","suscript.jsp?myBasee="+myBasee1+"&myAllowedExtentions="+myAllowedExtentions1,"true");//getUser.jsp
			//xreq.open("get","http://127.0.0.1:8888/ajax/getUser.jsp?q="+str,"true");
			xreq.send();
	  }
    </script>
    <form name="frm" method="POST">
        <p align="center"><font size="5"><b>Login Form</b></font></p> 
        <table align="center">
            <tr>
                <td>
                    <b>myBasee : </b>
                </td>
                <td>
                    <input type="text" name="myBasee" size="20" tabindex="1">
                </td>
            </tr>
            <tr>
                <td>
                    <b>myAllowedExtentions : </b>
                </td>
                <td>
                    <input type="text" name="myAllowedExtentions" size="20" tabindex="2">
                </td>
            </tr>
            <tr align="center">
                <td colspan=2>
                    <input type="button" value=" Show Content " name="B1" onclick="validate()" tabindex="3"><!--validate()-->
                </td>
            </tr>
            <!--
			<tr>
                <td><font size="4">New User?</font></td>
                <td>
                    <a href="NewUser.html">
                        <font size="4">Register</font>
                    </a>   
                </td>
            </tr>
			-->
        </table>
    </form>
	<div id="showtext">The response will come here</div>
    </body>
</html>
