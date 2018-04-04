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