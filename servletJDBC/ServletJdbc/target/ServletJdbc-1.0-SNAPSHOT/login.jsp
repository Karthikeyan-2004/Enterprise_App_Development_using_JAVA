<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>Login</title>
</head>
<body>
    <h2>Login</h2>
    <% if (request.getParameter("error") != null) { %>
        <p style="color: red;">Invalid username or password</p>
    <% } %>
    <form action="${pageContext.request.contextPath}/login" method="post">
        Username: <input type="text" name="username"><br>
        Password: <input type="password" name="password"><br>
        <input type="submit" value="Login">
    </form>
</body>
</html>
