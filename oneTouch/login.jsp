<%-- 
    Document   : login
    Created on : Oct 27, 2012, 9:32:48 AM
    Author     : PREMENDRA
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <link rel="stylesheet" type="text/css;" href=lipstick.css>
        <title>One Touch Employee Record Maintenance Application</title>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    </head>
    <body bgcolor="#CCFFFF">
    <center>
        <h1 id="title">
            <u>One Touch Employee Record Maintenance Application</u>
        </h1>
    </center> 
    <script language="javascript">
      function validate(){
          if(document.frm.UserName.value==""||document.frm.Password.value==""){
              alert("UserName or Password cannot be blank!!");
              return;
          }
          
          document.frm.action="Validate.jsp";
          document.frm.submit();
      }  
    </script>
    <form name="frm" method="POST">
        <p align="center"><font size="5"><b>Login Form</b></font></p> 
        <table align="center">
            <tr>
                <td>
                    <b>UserName : </b>
                </td>
                <td>
                    <input type="text" name="UserName" size="20" tabindex="1">
                </td>
            </tr>
            <tr>
                <td>
                    <b>Password : </b>
                </td>
                <td>
                    <input type="password" name="Password" size="20" tabindex="2">
                </td>
            </tr>
            <tr align="center">
                <td colspan=2>
                    <input type="button" value=" Login " name="B1" onclick="validate()" tabindex="3"><!--validate()-->
                </td>
            </tr>
            <tr>
                <td><font size="4">New User?</font></td>
                <td>
                    <a href="NewUser.jsp">
                        <font size="4">Register</font>
                    </a>   
                </td>
            </tr>
        </table>
    </form>
    </body>
</html>
