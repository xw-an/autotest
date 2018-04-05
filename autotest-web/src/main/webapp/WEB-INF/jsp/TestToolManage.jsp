<%--
  Created by IntelliJ IDEA.
  User: xuewei
  Date: 2018/4/5
  Time: 15:11
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String basePath=request.getContextPath();
%>
<html>
<head>
    <script type="text/javascript" src="<%=basePath%>/static/plugins/jquery/jquery-3.2.1.min.js"></script>
    <script type="text/javascript" src="<%=basePath%>/static/js/TestToolManage.js"></script>
    <title>工具集</title>
</head>
<body>
    <jsp:include page="commonNavbars.jsp"></jsp:include>
    <div style="width: 100%">
        <div id="main-container">
            <!-- 左侧菜单栏 -->
            <div id="sidebar" class="col-xs-2 column">
                <!-- 创建菜单树 -->
                <div class="col-xs-12">
                    <ul id="systemSetting" class="nav nav-list" style="height: 0px;">
                        <li><a href="#" onclick="menuClick('<%=basePath%>/TestToolManage/packageTool')"><i class="glyphicon glyphicon-th"></i>&nbsp;测试工具1</a></li>
                        <li><a href="#" onclick="menuClick('<%=basePath%>/TestToolManage/packageTool')"><i class="glyphicon glyphicon-th"></i>&nbsp;测试工具2</a></li>
                    </ul>
                </div>
            </div>
            <div class="col-xs-10 column">
                <div class="breadcrumbs" id="breadcrumbs">
                    <!-- 面包屑导航 -->
                    <ul class="breadcrumb">
                        <li>
                            <a href="<%=basePath%>/TestToolManage">工具集</a>
                        </li>
                        <li class="active" id="dashboard"></li>
                    </ul>
                </div>
                <!-- 内容展示页 -->
                <div>
                    <iframe id="iframe-page-content" src="<%=basePath%>/TestToolManage/packageTool" width="100%"  frameborder="no" border="0" marginwidth="0"
                            marginheight=" 0" scrolling="no" allowtransparency="yes"></iframe>
                </div>
            </div>
        </div>
    </div>

    <script type="text/javascript">
        $(function() {
            var height=document.documentElement.clientHeight;
            document.getElementById('iframe-page-content').style.height=height+'px';
        });
        var menuClick = function(menuUrl) {
            $('#iframe-page-content').attr('src',menuUrl);
        };

    </script>
</body>
</html>
