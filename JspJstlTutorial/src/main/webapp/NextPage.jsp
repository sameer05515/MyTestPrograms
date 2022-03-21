<%@ page import="util.contants.ProjectContants" %>
<HTML>
<BODY>
Hello, <%= session.getAttribute( ProjectContants.SESSION_ATTRIBUTE_THENAME ) %>
</BODY>
</HTML>