<%--
  Created by IntelliJ IDEA.
  User: xuewei
  Date: 2018/4/5
  Time: 15:23
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String basePath=request.getContextPath();
%>
<html>
<head>
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

    <title>时间转换工具</title>
</head>
<body>
    <div class="row">
        <div class="col-xs-3">
            <div class="form-group">
                <label>输入时间戳(例如:1522373424000)</label>
                <input class="form-control" type="text" id="timeStamp1">
            </div>
        </div>
        <div class="col-xs-2 col-xs-offset-1">
            <button type="button" class="btn btn-default" style="margin-top: 25px;margin-left: 20px;" onclick="changeTime(1)">转换</button>
        </div>
        <div class="col-xs-3">
            <label>结果(例如:yyyy-MM-dd HH:mm:ss)</label>
            <input class="form-control" type="text" id="time1">
        </div>
    </div>

    <div class="row">
        <div class="col-xs-3">
            <div class="form-group">
                <label>输入时间(例如:2018-5-16 11:30:24)</label>
                <input class="form-control" type="text" id="time2">
            </div>
        </div>
        <div class="col-xs-2 col-xs-offset-1">
            <button type="button" class="btn btn-default" style="margin-top: 25px;margin-left: 20px;" onclick="changeTime(2)">转换</button>
        </div>
        <div class="col-xs-3">
            <label>结果(例如:1526441424000)</label>
            <input class="form-control" type="text" id="timeStamp2">
        </div>
    </div>
</body>
<script type="text/javascript">
    function changeTime(type){
        var time="";
        if(type==1){
            time=$('#timeStamp1').val();
        }else if(type==2){
            time=$('#time2').val();
        }
        if(time!=""){
            $.ajax({
                type:"post",
                url:"/TestToolManage/changeTime",
                data:{type:type,time:time},
                dataType:"json",
                success: function(data) {//ajax请求成功后触发的方法
                    if(type==1){
                        $('#time1').val(data.result);
                    }else if(type==2){
                        $('#timeStamp2').val(data.result);
                    }
                },
                error: function(data) {//ajax请求失败后触发的方法
                    alert(data.errorMsg); //弹出错误信息
                }
            })
        }else{
            alert("时间不能为空");
        }

    }
</script>
</html>
