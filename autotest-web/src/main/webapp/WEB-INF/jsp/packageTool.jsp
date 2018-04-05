<%--
  Created by IntelliJ IDEA.
  User: xuewei
  Date: 2018/4/5
  Time: 15:23
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>打包工具</title>
</head>
<body>
    <div class="container-fluid">
        <form class="form-inline">
            <div class="row">
                <div class="col-xs-2">
                    <div class="form-group">
                        <label for="environment">环境:</label>
                        <select id="environment" class="form-control">
                            <option value="">uat</option>
                            <option value="uat">uat</option>
                            <option value="product">生产</option>
                        </select>
                    </div>
                </div>

                <div class="col-xs-6">
                    <div class="form-group">
                        <label for="inputFile">导入文件：</label>
                        <input class="form-control" type="file" id="inputFile">
                    </div>
                </div>
                <div class="col-xs-2">
                    <button type="button" class="btn btn-default" onclick="">执行</button>
                </div>
            </div>
        </form>
    </div>
</body>
</html>
