<%--
  Created by IntelliJ IDEA.
  User: xuewei
  Date: 2018/4/3
  Time: 15:47
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String basePath=request.getContextPath();
%>
<html>
<head>
    <script type="text/javascript" src="<%=basePath%>/static/plugins/jquery/jquery-3.2.1.min.js"></script>
    <script type="text/javascript" src="<%=basePath%>/static/js/TestStepManage.js"></script>
    <title>配置用例步骤</title>
</head>
<body>
    <jsp:include page="commonNavbars.jsp"></jsp:include>

    <!--查询筛选框-->
    <div class="container-fluid">
        <div class="row">
            <form class="form-inline">
                <div class="col-xs-12 col-xs-offset-4">
                    <div class="form-group">
                        <label for="caseId">用例id:</label>
                        <input type="text" class="form-control" id="caseId" placeholder="示例:1">
                        <button class="btn btn-default" onclick="searchStep()">查询</button>
                    </div>
                </div>
            </form>
        </div>
    </div>

    <!--操作按钮项-->
    <div class="container-fluid">
        <div class="row">
            <div class="btn-group" role="group">
                <button type="button" class="btn btn-success" onclick="saveSteps()">保存步骤</button>
                <button type="button" class="btn btn-danger" onclick="showAddStep()">新增步骤</button>
            </div>
        </div>
    </div>


    <!-- 表格展示所有测试用例 -->
    <div class="container-fluid" style="margin-top: 50px;">
        <table id="caseStepsTable"></table>
    </div>
</body>
</html>
