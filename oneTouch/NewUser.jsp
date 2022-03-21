<!--
To change this template, choose Tools | Templates
and open the template in the editor.
-->
<!DOCTYPE html>
<html>
    <head>
        <link rel="stylesheet" type="text/css" href=lipstick.css>
        <title>New User Registration</title>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    </head>
    <body bgcolor="#ccffff">
    <center>
        <h1>
            <u>One Touch Employee Record Maintenance Application</u>
        </h1>
    </center> 


    <a href="login.jsp">
        <font size="4" >
        Click here to login
        </font>
    </a>


    <form name="form1" method="POST">
        <script language="JavaScript">
            function validateNewUser(){
                if(document.form1.UserName.value==""){
                    alert("UserName cannot be blanked");
                    return;
                }
                if(document.form1.Password.value==""){
                    alert("Password cannot be blanked");
                    return;
                }
                if(document.form1.Password.value!=document.form1.confPassword.value){
                    alert("The two Passwords do not match");
                    return;
                }
                if(document.form1.FirstName.value==""){
                    alert("FirstName cannot be blanked");
                    return;
                }
                if(document.form1.LastName.value==""){
                    alert("LastName cannot be blanked");
                    return;
                }
                if(document.form1.Address1.value==""){
                    alert("Address cannot be blanked");
                    return;
                }
                if(document.form1.City.value==""){
                    alert("City cannot be blanked");
                    return;
                }
                if(document.form1.State.value=="Select State"){
                    alert("Please select a state");
                    return;
                }
                if(document.form1.PinCode.value==""){
                    alert("PinCode cannot be blanked");
                    return;
                }
                if(document.form1.Email.value==""){
                    alert("Email cannot be blanked");
                    return;
                }
                if(document.form1.PhoneNumber.value==""){
                    alert("PhoneNumber cannot be blanked");
                    return;
                }
                document.forms[0].action="Sign.jsp";
                document.forms[0].submit();
            }
        </script>

        <p align="left">
            &nbsp;<b>
                <font color="#ff0000">
                (fields marked * are mandatory)
                </font>
                &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
            </b>
        </p>
        <div align="left">
            <table border="0" width="100%">

                <tr>
                    <td width="23%">
                        <b>User *: </b>
                    </td>
                    <td width="77%">
                        <b>
                            <input type="text" name="UserName" size="20" tabindex="1">
                        </b>
                    </td>
                </tr>  

                <tr>
                    <td width="23%">
                        <b>Password *: </b>
                    </td>
                    <td width="77%">
                        <b>
                            <input type="text" name="Password" size="20" tabindex="2">
                        </b>
                    </td>
                </tr>

                <tr>
                    <td width="23%">
                        <b>Confirm Password *: </b>
                    </td>
                    <td width="77%">
                        <b>
                            <input type="text" name="confPassword" size="20" tabindex="3">
                        </b>
                    </td>
                </tr>

                <tr>
                    <td width="23%">
                        <b>First Name *: </b>
                    </td>
                    <td width="77%">
                        <b>
                            <input type="text" name="FirstName" size="20" tabindex="4">
                        </b>
                    </td>
                </tr>

                <tr>
                    <td width="23%">
                        <b>Middle Name : </b>
                    </td>
                    <td width="77%">
                        <b>
                            <input type="text" name="MiddleName" size="20" tabindex="5">
                        </b>
                    </td>
                </tr>

                <tr>
                    <td width="23%">
                        <b>Last Name *: </b>
                    </td>
                    <td width="77%">
                        <b>
                            <input type="text" name="LastName" size="20" tabindex="6">
                        </b>
                    </td>
                </tr>
            </table>
        </div>

        <div align="center">
            <center>
                <table border="0" width="100%">

                    <tr>
                        <td width="23%" height="25">
                            <b>Address1 *:&nbsp;&nbsp;</b>
                        </td>
                        <td width="77%" height="25">
                            <b>
                                <input type="text" name="Address1" size="43" tabindex="7">
                            </b>
                        </td> 
                    </tr>

                    <tr>
                        <td width="23%" height="25">
                            <b>Address2 :&nbsp;&nbsp;</b>
                        </td>
                        <td width="77%" height="25">
                            <b>
                                <input type="text" name="Address2" size="43" tabindex="8">
                            </b>
                        </td> 
                    </tr>

                    <tr>
                        <td width="23%" height="25">
                            <b>City *:&nbsp;&nbsp;</b>
                        </td>
                        <td width="77%" height="25">
                            <b>
                                <input type="text" name="City" size="20" tabindex="9">
                            </b>
                        </td> 
                    </tr>

                    <tr>
                        <td width="23%" height="25">
                            <b>State *:&nbsp;&nbsp;</b>
                        </td>
                        <td width="77%" height="25">
                            <b>
                                <select size="1" name="State" tabindex="10">
                                    <option selected value="Select State">
                                        Select State
                                    </option>
                                    <option>Assam</option>
                                    <option>Bihar</option>
                                    <option>Uttar Pradesh</option>
                                    <option>Madhya Pradesh</option>
                                    <option>Delhi</option>
                                </select>
                            </b>
                        </td> 
                    </tr>

                    <tr>
                        <td width="23%" height="25">
                            <b>Pin Code *:&nbsp;&nbsp;</b>
                        </td>
                        <td width="77%" height="25">
                            <b>
                                <input type="text" name="PinCode" size="20" tabindex="11">
                            </b>
                        </td> 
                    </tr>

                    <tr>
                        <td width="23%" height="25">
                            <b>Email *:&nbsp;&nbsp;</b>
                        </td>
                        <td width="77%" height="25">
                            <b>
                                <input type="text" name="Email" size="20" tabindex="12">
                            </b>
                        </td> 
                    </tr>

                    <tr>
                        <td width="23%" height="25">
                            <b>Phone Number *:&nbsp;&nbsp;</b>
                        </td>
                        <td width="77%" height="25">
                            <b>
                                <input type="text" name="PhoneNumber" size="20" tabindex="13">
                            </b>
                        </td> 
                    </tr>

                    <tr>
                        <td width="23%" height="1"></td>
                        <td width="77%" height="1"> </td> 
                    </tr>

                    <tr>
                        <td width="23%" height="21"></td>
                        <td width="77%" height="21">
                            <b>
                                <input type="submit" value="Submit" name="B1" onclick="validateNewUser();" tabindex="13">
                            </b>
                        </td> 
                    </tr>

                    <tr>
                        <td width="23%" height="1"><font size="4">already Registered ?</font></td>
                        <td width="77%" height="1">
                            <a href="login.jsp">
                                <font size="4" >
                                Click here to login
                                </font>
                            </a>  
                        </td>
                    </tr>

                </table> 
            </center>  
        </div>
    </form>

</body>
</html>
