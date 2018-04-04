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
            visible: true
        },{
            field: 'stepName',
            title: '步骤名',
            valign: 'middle',
            visible: true
        },{
            field: 'actionType',
            title: '动作类型',
            valign: 'middle',
            visible: true
        },{
            field: 'actionMap',
            title: '参数集合',
            valign: 'middle',
            visible: true
        },{
            field: 'operation',
            title: '操作',
            align: 'center',
            valign: 'middle',
            visible: true,
            events: stepOperateEvents,
            formatter: stepOperateFormatter
        }],
    });
})

//父表格操作项
window.stepOperateEvents = {
    'click #addStep': function (e, value, row, index) {
        //showUpdateCaseModal(row);
    },
    'click #deleteStep': function (e, value, row, index) {
        //showDeleteCaseModal(row);
    },
    'click #updateStep': function (e, value, row, index) {
       // runCase(index,row);
    }
};

//父表格操作项
function stepOperateFormatter(value, row, index) {
    return [
        '<button class="btn btn-primary" id="addStep">编辑</button>&nbsp;' +
        '<button class="btn btn-danger" id="deleteStep">删除</button>&nbsp;' +
        '<button class="btn btn-success" id="updateStep">运行</button>&nbsp;'
    ].join('');
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
}

//动态显示对应动作类型的输入内容
function showActionMap(){
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

//对新增步骤提交数据做校验
//TODO

//操作新增步骤，将记录插入到表格中
function addStep(){
    //获取步骤信息
    var stepName=$('#stepName').val();
    var actionType=$('#actionType').val();
    //从第三个控件开始获取
    var len=$('#addStepForm .form-group').length;
    var actionMap={};
    for(var i=2;i<len;i++){
        var paramName=$("#addStepForm .form-group:eq("+i+") input").attr("id");
        var paramValue=$("#addStepForm .form-group:eq("+i+") input").val();
    }
    //TODO 生成json的actionMap
    //往表格中插入一行记录
    var data={stepId:1,stepName:stepName,actionType:actionType,actionMap:"test"};
    //TODO 获取步骤id

    $('#caseStepsTable').bootstrapTable('prepend',data);
    $('#addStepForm')[0].reset();
    for(var i=2;i<len;i++){
        $("#addStepForm .form-group:eq("+i+")").remove();
    }
    $('#addStepModal').modal('hide');
}

