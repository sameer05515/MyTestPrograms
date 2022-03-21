<%@ page import="util.contants.ProjectContants" %>

<%
   String name = request.getParameter( ProjectContants.REQUEST_PARAMETER_USERNAME );
   session.setAttribute( ProjectContants.SESSION_ATTRIBUTE_THENAME, name );
%>
<HTML>
<BODY>
<A HREF="NextPage.jsp">Continue</A>
</BODY>
</HTML>