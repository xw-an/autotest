<%--
  Created by IntelliJ IDEA.
  User: xuewei
  Date: 2018/4/2
  Time: 12:56
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String basePath=request.getContextPath();
%>
<html>
<head>
    <title>自动化测试平台</title>
    <link href="<%=basePath%>/static/plugins/bootstrap/css/bootstrap.min.css" rel="stylesheet">
    <link href="<%=basePath%>/static/plugins/bootstraptable/bootstrap-editable.css" rel="stylesheet">
    <link href="<%=basePath%>/static/plugins/bootstraptable/bootstrap-table.min.css" rel="stylesheet">
    <script type="text/javascript" src="<%=basePath%>/static/plugins/jquery/jquery-3.2.1.min.js"></script>
    <script type="text/javascript" src="<%=basePath%>/static/plugins/jquery/jquery.validate.min.js"></script>
    <script type="text/javascript" src="<%=basePath%>/static/plugins/jquery/messages_zh.js"></script>
    <script type="text/javascript" src="<%=basePath%>/static/plugins/bootstrap/js/bootstrap.min.js"></script>
    <script type="text/javascript" src="<%=basePath%>/static/plugins/bootstraptable/bootstrap-editable.js"></script>
    <script type="text/javascript" src="<%=basePath%>/static/plugins/bootstraptable/bootstrap-table.min.js"></script>
    <script type="text/javascript" src="<%=basePath%>/static/plugins/bootstraptable/bootstrap-table-editable.js"></script>
</head>
<body>
    <!--导航栏-->
    <nav class="navbar navbar-inverse">
        <div class="container">
            <div class="navbar-header">
                <a class="navbar-brand" href="#">自动化测试平台</a>
            </div>
            <div class="navbar-collapse collapse">
                <ul class="nav navbar-nav">
                    <li class="active"><a href="#">用例管理</a></li>
                    <li><a href="#">执行日志</a></li>
                    <li><a href="#">工具集</a></li>
                </ul>
            </div>
        </div>
    </nav>
</body>
</html>
