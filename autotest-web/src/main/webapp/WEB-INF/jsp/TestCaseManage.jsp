<%@ page import="com.autotest.core.model.TestUser" %><%--
  Created by IntelliJ IDEA.
  User: xuewei
  Date: 2018/3/30
  Time: 17:02
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String basePath=request.getContextPath();
%>
<html>
<head>
    <script type="text/javascript" src="<%=basePath%>/static/plugins/jquery/jquery-3.2.1.min.js"></script>
    <script type="text/javascript" src="<%=basePath%>/static/js/TestCaseManage.js"></script>
    <title>测试用例管理</title>
</head>
<body>
    <jsp:include page="commonNavbars.jsp"></jsp:include>

    <div class="container-fluid">
        <div class="row">
            <div class="btn-group" role="group" style="padding-left: 15px;">
                <button type="button" class="btn btn-primary" onclick="showAddCase()">新增用例</button>
                <div class="btn-group" role="group">
                    <button type="button" class="btn btn-success dropdown-toggle" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                        运行用例
                        <span class="caret"></span>
                    </button>
                    <ul class="dropdown-menu">
                        <li><a href="#" onclick="runCaseByType(1)">单线程模式</a></li>
                        <li><a href="#" onclick="runCaseByType(2)">多线程模式</a></li>
                    </ul>
                </div>
            </div>
        </div>
    </div>

    <!-- 新增测试用例模态框 -->
    <div class="modal fade" id="addCaseModal" tabindex="-1" role="dialog">
        <div class="modal-dialog" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                    <h4 class="modal-title" id="addTitle">新增测试用例</h4>
                </div>
                <div class="modal-body">
                    <form id="addCaseForm">
                        <div class="form-group" id="testScenariosNameGroup">
                            <label>用例名称</label>
                            <input type="text" class="form-control" name="testScenariosName" id="testScenariosName" placeholder="用例名称">
                        </div>
                        <div class="form-group" id="testScenariosTypeGroup">
                            <label>用例类型</label>
                            <input type="text" class="form-control" name="testScenariosType" id="testScenariosType" placeholder="用例类型">
                        </div>
                        <div class="form-group" id="testContentGroup">
                            <label>用例内容</label>
                            <textarea class="form-control" name="testContent" id="testContent" rows="5" placeholder="测试内容"></textarea>
                        </div>
                        <div class="form-group" id="remarkGroup">
                            <label>备注</label>
                            <input type="text" class="form-control" name="remark" id="remark" placeholder="备注">
                        </div>
                        <%--<div class="form-group" id="userIdGroup">
                            <label>创建人</label>
                            <input type="text" class="form-control" name="userId" id="userId" placeholder="创建人">
                        </div>--%>
                    </form>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                    <button type="button" class="btn btn-primary" onclick="addCase()">新增</button>
                </div>
            </div>
        </div>
    </div>

    <!-- 删除测试用例模态框 -->
    <div class="modal fade" id="deleteCaseModal" tabindex="-1" role="dialog">
        <div class="modal-dialog" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                    <h4 class="modal-title" id="deleteTitle">删除测试用例</h4>
                </div>
                <div class="modal-body">
                    <input type="hidden" id="deleteId">确定要删除该条测试用例吗？
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                    <button type="button" class="btn btn-primary" onclick="deleteCase()">确定</button>
                </div>
            </div>
        </div>
    </div>

    <!-- 编辑测试用例模态框 -->
    <div class="modal fade" id="updateCaseModal" tabindex="-1" role="dialog">
        <div class="modal-dialog" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                    <h4 class="modal-title" id="updateTitle">修改测试用例</h4>
                </div>
                <div class="modal-body">
                    <form id="updateCaseForm">
                        <input type="hidden" id="updateId">
                        <div class="form-group" id="updateTestScenariosNameGroup">
                            <label>用例名称</label>
                            <input type="text" class="form-control" name="updateTestScenariosName" id="updateTestScenariosName" placeholder="用例名称">
                        </div>
                        <div class="form-group" id="updateTestScenariosTypeGroup">
                            <label>用例类型</label>
                            <input type="text" class="form-control" name="updateTestScenariosType" id="updateTestScenariosType" placeholder="用例类型">
                        </div>
                        <div class="form-group" id="updateTestContentGroup">
                            <label>用例内容</label>
                            <textarea class="form-control" name="updateTestContent" id="updateTestContent" rows="5" placeholder="测试内容"></textarea>
                        </div>
                        <div class="form-group" id="updateRemarkGroup">
                            <label>备注</label>
                            <input type="text" class="form-control" name="updateRemark" id="updateRemark" placeholder="备注">
                        </div>
                        <div class="form-group" id="updateUserIdGroup">
                            <label>创建人</label>
                            <input type="text" class="form-control" name="updateUserId" id="updateUserId" placeholder="创建人">
                        </div>
                    </form>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                    <button type="button" class="btn btn-primary" onclick="updateCase()">保存</button>
                </div>
            </div>
        </div>
    </div>

    <!-- 表格展示所有测试用例 -->
    <div class="container-fluid">
        <table id="testCaseTable"></table>
    </div>
</body>
</html>
