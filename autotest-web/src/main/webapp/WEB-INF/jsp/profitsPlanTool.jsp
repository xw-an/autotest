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

    <title>计算收益工具</title>
</head>
<body>
    <div class="row">
        <div class="col-xs-3">
            <div class="form-group">
                <label>产品code</label>
                <input class="form-control" type="text" id="productCode">
            </div>
        </div>
        <div class="col-xs-3">
            <div class="form-group">
                <label>本金</label>
                <input class="form-control" type="text" id="price">
            </div>
        </div>
        <div class="col-xs-3">
            <div class="form-group">
                <label>活动利率</label>
                <input class="form-control" type="text" id="activityRate">
            </div>
        </div>
        <div class="col-xs-2">
            <button type="button" class="btn btn-default" onclick="calc()">计算</button>
        </div>
    </div>

    <div class="row">
        <div class="col-xs-10">
            <div class="form-group">
                <label>结果</label>
                <textarea class="form-control" id="result" rows="20"></textarea>
            </div>
        </div>
    </div>

</body>
<script type="text/javascript">
    function calc(){
        var productCode=$('#productCode').val();
        var price=$('#price').val();
        var activityRate=$('#activityRate').val();
        if(productCode!=""&&price!=""&&activityRate!=""){
            $('#result').val("");
            $.ajax({
                type:"post",
                url:"/TestToolManage/calculateAmount",
                data:{productCode:productCode,price:price,activityRate:activityRate},
                dataType:"json",
                success: function(data) {//ajax请求成功后触发的方法
                    $('#result').val(data.result);
                },
                error: function(data) {//ajax请求失败后触发的方法
                    alert(data.errorMsg); //弹出错误信息
                }
            })
        }else{
            alert("产品code,本金,活动利率不能为空")
        }
    }
</script>
</html>
