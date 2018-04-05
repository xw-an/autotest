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

    <!--操作按钮项-->
    <div class="container-fluid">
        <div class="row">
            <div class="col-xs-12 col-xs-offset-4">
                <label for="caseId">用例ID:</label>&nbsp;&nbsp;
                <input type="text" id="caseId" placeholder="示例:1">
                <button class="btn btn-default" onclick="searchStep()">查询</button>
            </div>
        </div>
        <div class="row">
            <div class="btn-group" role="group" style="padding-left: 15px;">
                <button type="button" class="btn btn-success" onclick="saveSteps()">保存步骤</button>
                <button type="button" class="btn btn-danger" onclick="showAddStep()">新增步骤</button>
            </div>
        </div>
        <div class="row">
            <!-- 表格展示所有测试用例 -->
            <div class="container-fluid" style="margin-top: 50px;">
                <table id="caseStepsTable" style="table-layout:fixed;word-break:break-all; word-wrap:break-all;"></table>
            </div>
        </div>
    </div>

    <!--添加步骤模态框-->
    <div class="modal fade" id="addStepModal" tabindex="-1" role="dialog">
        <div class="modal-dialog" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                    <h4 class="modal-title" id="addTitle">添加步骤</h4>
                </div>
                <div class="modal-body">
                    <form id="addStepForm">
                        <div class="form-group" id="stepNameGroup">
                            <label>步骤名称</label>
                            <input type="text" class="form-control" name="stepName" id="stepName" placeholder="步骤名称">
                        </div>
                        <div class="form-group" id="actionTypeGroup">
                            <label>动作类型</label>
                            <select class="form-control" id="actionType" onchange="addActionMap()">
                                <option value="请选择">请选择</option>
                                <option value="db">数据库</option>
                                <option value="callInterface">接口</option>
                                <option value="checkValue">验证</option>
                                <option value="setParameter">设置变量</option>
                            </select>
                        </div>
                    </form>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal" onclick="clearActionMap('add')">取消</button>
                    <button type="button" class="btn btn-primary" id="addStepButton">新增</button>
                </div>
            </div>
        </div>
    </div>

    <!--修改步骤模态框-->
    <div class="modal fade" id="updateStepModal" tabindex="-1" role="dialog">
        <div class="modal-dialog" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                    <h4 class="modal-title" id="updateTitle">修改步骤</h4>
                </div>
                <div class="modal-body">
                    <form id="updateStepForm">
                        <div class="form-group" id="updateNameGroup">
                            <label>步骤名称</label>
                            <input type="text" class="form-control" name="updateStepName" id="updateStepName">
                        </div>
                        <div class="form-group" id="updateActionTypeGroup">
                            <label>动作类型</label>
                            <select class="form-control" id="updateActionType" onchange="updateActionMap()">
                                <option value="请选择">请选择</option>
                                <option value="db">数据库</option>
                                <option value="callInterface">接口</option>
                                <option value="checkValue">验证</option>
                                <option value="setParameter">设置变量</option>
                            </select>
                        </div>
                    </form>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal" onclick="clearActionMap('update')">取消</button>
                    <button type="button" class="btn btn-primary" id="updateButton">修改</button>
                </div>
            </div>
        </div>
    </div>


</body>
</html>
