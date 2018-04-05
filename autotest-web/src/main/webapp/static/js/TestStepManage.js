$(function(){
    $('#caseStepsTable').bootstrapTable({
        url: "./TestStepManage/caseSteps",
        method: 'post',
        dataType:"json",
        cache: false,
        striped: true,
        pagination: true,
        pageSize: 20,
        height:800,
        columns: [{
            field: 'stepId',
            title: '步骤Id',
            valign: 'middle',
            visible: true,
            width:'5%',
            formatter:function(value,row,index){return index+1}
        },{
            field: 'stepName',
            title: '步骤名',
            valign: 'middle',
            width:'10%',
            visible: true
        },{
            field: 'actionType',
            title: '动作类型',
            valign: 'middle',
            width:'10%',
            visible: true
        },{
            field: 'actionMap',
            title: '参数集合',
            valign: 'middle',
            width:'60%',
            visible: true
        },{
            field: 'operation',
            title: '操作',
            align: 'center',
            valign: 'middle',
            width:'15%',
            visible: true,
            events: stepOperateEvents,
            formatter: stepOperateFormatter
        }],
    });
})

//父表格操作项
window.stepOperateEvents = {
    'click #updateCaseStep': function (e, value, row, index) {
        updateCaseStep(row,index);
    },
    'click #deleteCaseStep': function (e, value, row, index) {
        deleteCaseStep(row,index);
    },
    'click #addCurrentStep': function (e, value, row, index) {
        addCurrentStep(row,index);
    }
};

//父表格操作项
function stepOperateFormatter(value, row, index) {
     return [
         '<span class="glyphicon glyphicon-pencil" aria-hidden="true" id="updateCaseStep"></span>&nbsp;&nbsp;&nbsp;'+
         '<span class="glyphicon glyphicon-trash" aria-hidden="true" id="deleteCaseStep"></span>&nbsp;&nbsp;&nbsp;'+
         '<span class="glyphicon glyphicon-plus" aria-hidden="true" id="addCurrentStep"></span>&nbsp;&nbsp;&nbsp;'
     ].join('');

    /*return [
        '<button class="btn btn-xs btn-primary" id="updateCaseStep">更新步骤</button>&nbsp;&nbsp;&nbsp;'+
        '<button class="btn btn-xs btn-danger" id="deleteCaseStep">删除步骤</button>&nbsp;&nbsp;&nbsp;'+
        '<button class="btn btn-xs btn-success" id="addCurrentStep">增加步骤</button>&nbsp;&nbsp;&nbsp;'
    ].join('');*/
};

//查询该用例下的所有步骤
function searchStep(){
    var searchParams={caseId:$('#caseId').val().trim()};
    $.ajax({
        type:"post",
        url:"./TestStepManage/caseSteps",
        dataType:"json",
        contentType: "application/json",
        traditional: true, //使json格式的字符串不会被转码
        data:JSON.stringify(searchParams),
        success:function(data){
            $('#caseStepsTable').bootstrapTable('load',data);
        },
        error:function(){
            $('#caseStepsTable').bootstrapTable('removeAll');
        }
    })
}

//显示添加步骤模态框
function showAddStep(){
    $('#addStepModal').modal('show');
    $('#addStepButton').one("click",function () {
        addStep(-1);
    });
}

//动态显示新增步骤框中的对应动作类型的输入内容
function addActionMap(){
    $('.actionParams').remove();
    var actionType=$('#actionType').val();
    if(actionType=='请选择') return;
    //ajax请求获取类型下的所有参数
    $.ajax({
        type:"post",
        url:"./TestStepManage/"+actionType+"/getActionParams",
        dataType:"json",
        contentType: "application/json",
        traditional: true, //使json格式的字符串不会被转码
        success:function(data){
            for (var key in data)
            {
                //界面展示填写项
                var name=data[key]
                var html="<div class=\"form-group actionParams\" id=\""+key+"Group\">\n" +
                    "<label>"+name+"</label>\n" +
                    "<input type=\"text\" class=\"form-control\" name=\""+key+"\" id=\""+key+"\" placeholder=\""+key+"\">\n" +
                    "</div>"
                $('#addStepForm .form-group').last().append(html);
            }
        },
        error:function(){
            alert("数据加载失败");
        }
    })
}

//清空动态文本框
function clearActionMap(type){
    if(type=="add"){
        var len=$('#addStepForm .form-group').length;
        $('#addStepForm')[0].reset();
        for(var i=2;i<len;i++){
            $("#addStepForm .form-group:eq("+i+")").remove();
        }
        $('#addStepModal').modal('hide');
    }else if(type=="update"){
        var len=$('#updateStepForm .form-group').length;
        $('#updateStepForm')[0].reset();
        for(var i=2;i<len;i++){
            $("#updateStepForm .form-group:eq("+i+")").remove();
        }
        $('#updateStepModal').modal('hide');
    }
}

//对新增步骤提交数据做校验
//TODO

//操作新增步骤，将记录插入到表格中
function addStep(index){
    //获取步骤信息
    var stepName=$('#stepName').val();
    var actionType=$('#actionType').val();
    var stepId;
    if(index == -1){
        stepId = $('#caseStepsTable').bootstrapTable('getData').length+1;
    }else{
        stepId = index + 2;
    }

    //从第三个控件开始获取
    var len=$('#addStepForm .form-group').length;
    var actionMap={};
    for(var i=2;i<len;i++){
        var paramName=$("#addStepForm .form-group:eq("+i+") input").attr("id");
        var paramValue=$("#addStepForm .form-group:eq("+i+") input").val();
        actionMap[paramName] = paramValue;
    }
    //往表格中插入一行记录
    var data={stepId:stepId,stepName:stepName,actionType:actionType,actionMap:JSON.stringify(actionMap)};
    if(index!=-1){
        $('#caseStepsTable').bootstrapTable('insertRow',{index:index+1,row:data});
    }else{
        $('#caseStepsTable').bootstrapTable('append',data);
    }
    clearActionMap('add');
}

//TODO 删除当前步骤
function deleteCaseStep(row,index) {
    //$("#caseStepsTable tr[data-index='"+index+"']").remove();
    $('#caseStepsTable').bootstrapTable('remove', {
        field: 'stepId',
        values: [row.stepId]
    });
}

//动态显示修改步骤框中的对应动作类型的输入内容
function updateActionMap(){
    $('.updateActionParams').remove();
    var updateActionType=$('#updateActionType').val();
    if(updateActionType=='请选择') return;
    //ajax请求获取类型下的所有参数
    $.ajax({
        type:"post",
        url:"./TestStepManage/"+updateActionType+"/getActionParams",
        dataType:"json",
        contentType: "application/json",
        traditional: true, //使json格式的字符串不会被转码
        success:function(data){
            for (var key in data)
            {
                //界面展示填写项
                var name=data[key]
                var html="<div class=\"form-group updateActionParams\" id=\"update"+key+"Group\">\n" +
                    "<label>"+name+"</label>\n" +
                    "<input type=\"text\" class=\"form-control\" name=\"update"+key+"\" id=\"update"+key+"\" placeholder=\""+key+"\">\n" +
                    "</div>"
                $('#updateStepForm .form-group').last().append(html);
            }
        },
        error:function(){
            alert("数据加载失败");
        }
    })
}

//显示修改步骤模态框
function updateCaseStep(row,index){
    $('#updateStepModal').modal('show');
    $('#updateStepName').val(row.stepName);
    $('#updateActionType').val(row.actionType);
    var jsonActionMap=JSON.parse(row.actionMap);
    //ajax请求获取类型下的所有参数
    $.ajax({
        type:"post",
        url:"./TestStepManage/"+row.actionType+"/getActionParams",
        dataType:"json",
        contentType: "application/json",
        traditional: true, //使json格式的字符串不会被转码
        success:function(data){
            for (var key in data)
            {
                //界面展示填写项
                var name=data[key]
                var html="<div class=\"form-group updateActionParams\" id=\"update"+key+"Group\">\n" +
                    "<label>"+name+"</label>\n" +
                    "<input type=\"text\" class=\"form-control\" name=\"update"+key+"\" id=\"update"+key+"\" value=\""+jsonActionMap[key]+"\">\n" +
                    "</div>"
                $('#updateStepForm .form-group').last().append(html);
            }
        },
        error:function(){
            alert("数据加载失败");
        }
    })
    //将row和index传入修改按钮的onclick事件中
    $("#updateButton").one("click",function(){
        updateStep(row,index);
    });//为当前元素绑定一次性点击事件
}

//提交修改操作,更新表格中当前这条记录
function updateStep(row,index){
    //获取步骤信息
    var updateStepName=$('#updateStepName').val();
    var updateActionType=$('#updateActionType').val();
    //从第三个控件开始获取
    var len=$('#updateStepForm .form-group').length;
    var updateActionMap={};
    for(var i=2;i<len;i++){
        var paramName=$("#updateStepForm .form-group:eq("+i+") input").attr("id");
        var paramValue=$("#updateStepForm .form-group:eq("+i+") input").val();
        paramName=paramName.substring("update".length);
        updateActionMap[paramName] = paramValue;
    }
    //更新记录
    var newdata={stepId:row.stepId,stepName:updateStepName,actionType:updateActionType,actionMap:JSON.stringify(updateActionMap)};
    $('#caseStepsTable').bootstrapTable('updateRow',{index:index,row: newdata});//更新这行记录
    clearActionMap('update');
}

//显示添加步骤模态框
function  addCurrentStep(row,index){
    $('#addStepModal').modal('show');
    addActionMap();
    $('#addStepButton').one("click",function () {
        addStep(index);
    });
};