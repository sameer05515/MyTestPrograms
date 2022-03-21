<HTML>
<BODY>
<!--#########################################################################################-->
<%
    // This scriptlet declares and initializes "date"
    System.out.println( "Evaluating date now" );
    java.util.Date date = new java.util.Date();
%>
Hello!  The time is now
<%
    out.println( date );
    out.println( "<BR>Your machine's address is " );
    out.println( request.getRemoteHost());
%>
<!--#########################################################################################-->
<!--TABLE BORDER-->
<hr/>
<TABLE BORDER=2>
<%
	int n=20;
    for ( int i = 0; i < n; i++ ) {
        %>
        <TR>
        <TD>Number</TD>
        <TD><%= i+1 %></TD>
        </TR>
        <%
    }
%>
</TABLE>
<!--#########################################################################################-->
<!--CONDITIONALS-->
<HR/>
<%
	boolean hello=true;
    if ( hello ) {
        %>
        <P>Hello, world</P>
        <%
    } else {
        %>
        <P>Goodbye, world</P>
        <%
    }
%>
<!--#########################################################################################-->
</BODY>
</HTML>