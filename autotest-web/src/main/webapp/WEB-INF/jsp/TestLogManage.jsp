<%--
  Created by IntelliJ IDEA.
  User: xuewei
  Date: 2018/4/5
  Time: 15:35
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String basePath=request.getContextPath();
%>
<html>
<head>
    <link href="<%=basePath%>/static/plugins/bootstrap-datetimepicker/css/bootstrap-datetimepicker.min.css" rel="stylesheet">
    <script type="text/javascript" src="<%=basePath%>/static/plugins/jquery/jquery-3.2.1.min.js"></script>
    <script type="text/javascript" src="<%=basePath%>/static/js/TestLogManage.js"></script>
    <title>执行日志</title>
</head>
<body>
    <jsp:include page="commonNavbars.jsp"></jsp:include>

    <!--日志筛选框-->
    <div class="container">
        <form id="testLogForm">
            <div class="row">
                <div class="col-xs-3  col-xs-offset-2">
                    <div class="form-group">
                        <label for="runStartTime">执行开始时间</label>
                        <div class="input-group date form_datetime" data-date="1979-09-16T05:25:07Z" data-date-format="yyyy-mm-dd HH:ii" data-link-field="runStartTime">
                            <input class="form-control" type="text" value="" >
                            <span class="input-group-addon"><span class="glyphicon glyphicon-remove"></span></span>
                            <span class="input-group-addon"><span class="glyphicon glyphicon-th"></span></span>
                        </div>
                        <input type="hidden" id="runStartTime" value="" />
                    </div>
                </div>
                <div class="col-xs-3">
                    <div class="form-group">
                        <label for="runEndTime">执行结束时间</label>
                        <div class="input-group date form_datetime" data-date="1979-09-16T05:25:07Z" data-date-format="yyyy-mm-dd HH:ii" data-link-field="runEndTime">
                            <input class="form-control" type="text" value="" >
                            <span class="input-group-addon"><span class="glyphicon glyphicon-remove"></span></span>
                            <span class="input-group-addon"><span class="glyphicon glyphicon-th"></span></span>
                        </div>
                        <input type="hidden" id="runEndTime" value="" />
                    </div>
                </div>
                <div class="col-xs-3">
                    <div class="form-group">
                        <label for="runUserId">执行人</label>
                        <select class="form-control" id="runUserId">
                            <option value="All">全部</option>
                            <option value="xw">薛伟</option>
                            <option value="admin">管理员</option>
                            <option value="test">匿名</option>
                        </select>
                    </div>
                </div>
            </div>
            <div class="row">
                <div class="col-xs-3  col-xs-offset-2">
                    <div class="form-group">
                        <label for="runCaseId">用例Id</label>
                        <input type="text" class="form-control" id="runCaseId">
                    </div>
                </div>
                <div class="col-xs-3">
                    <div class="form-group">
                        <label for="runCaseName">用例名</label>
                        <input type="text" class="form-control" id="runCaseName">
                    </div>
                </div>
                <div class="col-xs-3">
                    <div class="form-group">
                        <label for="runCaseType">用例类型</label>
                        <input type="text" class="form-control" id="runCaseType">
                    </div>
                </div>
            </div>
            <div class="row">
                <div class="col-xs-3 col-xs-offset-2">
                    <div class="form-group">
                        <label for="runCaseResult">运行结果</label>
                        <select class="form-control" id="runCaseResult">
                            <option value="All">全部</option>
                            <option value="Run">Run</option>
                            <option value="Success">Success</option>
                            <option value="Fail">Fail</option>
                        </select>
                    </div>
                </div>
            </div>
            <div class="row">
                <div class="col-xs-12 col-xs-offset-6">
                    <button type="button" class="btn btn-default" onclick="searchLog()">查询</button>
                </div>
            </div>
        </form>
    </div>

    <!-- 日志内容表格 -->
    <div class="container-fluid">
        <div class="row">
            <div class="col-xs-12">
                <table id="testLogTable" style="table-layout:fixed;word-break:break-all;"></table>
            </div>
        </div>
    </div>

    <script type="text/javascript" src="<%=basePath%>/static/plugins/bootstrap-datetimepicker/js/bootstrap-datetimepicker.min.js"></script>
    <script type="text/javascript">
        $(".form_datetime").datetimepicker({
            autoclose: true,
            todayBtn: true,
            startDate: "2018-04-05 10:00",
            minuteStep: 1
        });
    </script>

</body>
</html>
