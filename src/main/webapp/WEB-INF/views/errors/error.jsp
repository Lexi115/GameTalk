<%--
  Created by IntelliJ IDEA.
  User: lexi
  Date: 05/02/25
  Time: 22:47
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" isErrorPage="true" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
    errore <%=response.getStatus() %>
    ${requestScope.errorMessage}
</body>
</html>
