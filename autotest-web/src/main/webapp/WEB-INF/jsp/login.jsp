<%--
  Created by IntelliJ IDEA.
  User: xuewei
  Date: 2018/4/9
  Time: 16:49
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String basePath=request.getContextPath();
%>
<html>
<head>
    <title>登陆</title>
    <link href="<%=basePath%>/static/plugins/bootstrap/css/bootstrap.min.css" rel="stylesheet">
    <link href="<%=basePath%>/static/css/login.css" rel="stylesheet">
    <script type="text/javascript" src="<%=basePath%>/static/plugins/jquery/jquery-3.2.1.min.js"></script>
    <script type="text/javascript" src="<%=basePath%>/static/plugins/bootstrap/js/bootstrap.min.js"></script>
</head>
<body>
    <div class="container">
        <form class="form-signin" method="post" action="loginUser">
            <h2 class="form-signin-heading">Please login</h2>
            <label for="name" class="sr-only">Username/UserId</label>
            <input type="text" name="name" id="name" class="form-control" placeholder="Username/UserId" required autofocus>

            <label for="password" class="sr-only">Password</label>
            <input type="password" name="password" id="password" class="form-control" placeholder="Password" required>
            <div class="checkbox">
                <label>
                    <input type="checkbox" name="isSaveCookie" checked="true"> Remember me for a week
                </label>
            </div>
            <em style="color:red;">${errorMsg}</em>
            <button class="btn btn-lg btn-primary btn-block" type="submit">login</button>
        </form>
    </div>
</body>
</html>
