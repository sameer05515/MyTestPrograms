<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Name Capture Form</title>
</head>
<body>
<h1>Provide Your Name</h1>
<form method="post" action="${pageContext.request.contextPath}/forms/name">
    <label for="name-input">What&#39;s your name?</label>
    <input id="name-input" type="text" name="username" value="${param.username}" required>
    <button type="submit">Submit</button>
</form>
<c:if test="${not empty error}">
    <p style="color: darkred">${error}</p>
</c:if>
</body>
</html>
