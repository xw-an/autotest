$(function(){
    var filter=null;//筛选条件
    //展示父表格内容-所有测试用例相关信息
    $('#testCaseTable').bootstrapTable({
        method:"post",
        url: "./TestCaseManage/TestCases",
        dataType:"json",
        cache: false,
        striped: true,
        search: true,
        showRefresh: true,
        pagination: true,
        pageSize: 20,
        height:800,
        detailView:true,
        onExpandRow:stepDetailTable,
        columns: [{
            field: 'state',
            checkbox: true,
            align: 'center',
            valign: 'middle',
            visible: true,
        },{
            field: 'id',
            title: '用例ID',
            align: 'center',
            valign: 'middle',
            sortable: true,
            visible: true
        },{
            field: 'testScenariosName',
            title: '用例名',
            align: 'center',
            valign: 'middle',
            visible: true
        },{
            field: 'testScenariosType',
            title: '用例类型',
            align: 'center',
            valign: 'middle',
            visible: true
        },{
            field: 'testContent',
            title: '用例内容',
            align: 'center',
            valign: 'middle',
            visible: true
        },{
            field: 'userId',
            title: '创建人',
            align: 'center',
            valign: 'middle',
            visible: true
        },{
            field: 'remark',
            title: '备注',
            align: 'center',
            valign: 'middle',
            visible: true
        },{
            field: 'operation',
            title: '操作',
            align: 'center',
            valign: 'middle',
            visible: true,
            events: caseOperateEvents,
            formatter: caseOperateFormatter
        }]
    });
})

//父表格操作项
window.caseOperateEvents = {
    'click #editCase': function (e, value, row, index) {
        showUpdateCaseModal(row);//使【修改测试用例】模态框可见
    },
    'click #deleteCase': function (e, value, row, index) {
        showDeleteCaseModal(row);//使【删除测试用例】模态框可见
    },
    'click #runCase': function (e, value, row, index) {
        runTestCase(index,row);
    }
};

//父表格操作项
function caseOperateFormatter(value, row, index) {
    return [
        '<button class="btn btn-primary" id="editCase">编辑</button>&nbsp;' +
        '<button class="btn btn-danger" id="deleteCase">删除</button>&nbsp;' +
        '<button class="btn btn-success" id="runCase">运行</button>&nbsp;'
    ].join('');
};

//展示子表格内容-测试用例详细步骤
function stepDetailTable(index, row, $detail) {
    var caseId=row.id;
    var cur_table = $detail.html('<table></table>').find('table');
    $(cur_table).bootstrapTable({
        url: "./TestCaseManage/TestSteps/"+caseId,
        method: 'post',
        cache: false,
        striped: true,
        pageSize: 10,
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
        }],
    });
}

//显示新增测试用例模态框
function showAddCase(){
    $('#addCaseModal').modal('show');
}

//操作新增用例的功能
function addCase(){
    var testCase={
        testScenariosName:$('#testScenariosName').val(),
        testScenariosType:$('#testScenariosType').val(),
        testContent:$('#testContent').val(),
        remark:$('#remark').val(),
        userId:$('#userId').val()
    };
    $.ajax({
        url:"./TestCaseManage/addCase",
        type:"post",
        dataType:"json",
        contentType: "application/json",
        data:JSON.stringify(testCase),
        error:function(data){
            alert(data.msg);
        },
        success:function(data){
            alert(data.msg);
            $('#addCaseModal').modal('hide');
            $('#addCaseForm')[0].reset();
            $('#testCaseTable').bootstrapTable('refresh');
        }
    })
}

//显示修改测试用例模态框
function showUpdateCaseModal(row){
    $('#updateCaseModal').modal('show');
    $('#updateId').val(row.id);
    $('#updateTestScenariosName').val(row.testScenariosName);
    $('#updateTestScenariosType').val(row.testScenariosType);
    $('#updateTestContent').val(row.testContent);
    $('#updateRemark').val(row.remark);
    $('#updateUserId').val(row.userId);
}

//修改测试用例的表单验证功能
function validupdateform(){
    return $("#updateCaseForm").validate({
        errorElement:"em",
        errorClass:"red",
        rules : {
            updateTestScenariosName : {required : true},
            updateTestContent : {required : true},
            updateUserId : {required : true},
        },
        messages : {
            updateTestScenariosName : {required : '测试名称必填',},
            updateTestContent : {required : '测试内容必填'},
            updateUserId : {required : '创建人id必填'},
        }
    })
}

//操作修改测试用例功能
function updateCase(){
    if(validupdateform().form()){
        var tcase={
            id : $('#updateId').val(),
            testScenariosName : $('#updateTestScenariosName').val(),
            testScenariosType : $('#updateTestScenariosType').val(),
            testContent : $('#updateTestContent').val(),
            remark : $('#updateRemark').val(),
            userId : $('#updateUserId').val()
        };
        $.ajax({
            type:"post",
            url:"./TestCaseManage/updateCase",
            dataType:"json",
            contentType: "application/json",
            data: JSON.stringify(tcase),
            error:function(data){
                alert(data.msg);
            },
            success:function(data){
                alert(data.msg);
                $('#updateCaseModal').modal('hide');
                $('#testCaseTable').bootstrapTable('refresh');
            }
        })
    }else{
        //校验不通过，什么都不用做，校验信息已经正常显示在表单上
        $("em.red").css("color","red");
    }
}

//显示删除测试用例模态框
function showDeleteCaseModal(row){
    $('#deleteCaseModal').modal('show');
    $('#deleteId').val(row.id);
}

//操作删除用例的功能
function deleteCase(){
    $.ajax({
        url:"./TestCaseManage/"+$('#deleteId').val()+"/deleteCase",
        type:"post",
        error:function(data){
            alert(data.msg);
        },
        success:function(data){
            alert(data.msg);
            $('#deleteCaseModal').modal('hide');
            $('#testCaseTable').bootstrapTable('refresh');
        }
    })
}