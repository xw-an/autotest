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
        responseHandler: function (res) {
            var len = res.length;
            for(var i=0;i<len;i++){
                res[i].rowIndexId = i;
            }
            return res;
        },
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
            field: 'testResult',
            title: '运行结果',
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
        runCase(index,row);//运行测试用例
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

//运行单条测试用例
function runCase(index,row){
    //更新测试结果这一列的值
    $('#testCaseTable').bootstrapTable("updateCell", {
        index : index,
        field : 'testResult',
        value : "<a href='#'><span class='label label-warning' style='font-size:14px'>Run</span></a>"
    });
    var runParams={userId:"HQ001",type:1,caseIds:[{"caseId":row.id,"rowId":row.rowIndexId}]};//1表示单线程，2表示多线程
    $.ajax({
        type:"post",
        url:"./TestCaseManage/runCase",
        contentType: 'application/json;charset=UTF-8', //内容类型
        traditional: true, //使json格式的字符串不会被转码
        data: JSON.stringify(runParams),//TODO 接入权限管理以后需要动态获取
        error:function(){
           var execResult = "<a href='#'><span class='label label-danger' style='font-size:14px'>Fail</span></a>";
            //更新测试结果这一列的值
            $('#testCaseTable').bootstrapTable("updateCell", {
                index : index,
                field : 'testResult',
                value : execResult
            });
        },
        success:function(data){
            var execResult;
            if(data[0].result){
                execResult = "<a href='#'><span class='label label-success' style='font-size:14px'>Success</span></a>";
            }else{
                execResult = "<a href='#'><span class='label label-danger' style='font-size:16px'>Fail</span></a>";
            }
            //更新测试结果这一列的值
            $('#testCaseTable').bootstrapTable("updateCell", {
                index : index,
                field : 'testResult',
                value : execResult
            });
        }
    });
}

//单线/多线程模式运行测试集
function runCaseByType(type){
    var checks=$('#testCaseTable').bootstrapTable('getSelections');
    var paramsArray = new Array();
    if(checks.length==0){
        alert("请勾选一条测试用例");
    }
    else{
        for(var i=0;i<checks.length;i++){
            //更新界面状态为Run
            $('#testCaseTable').bootstrapTable("updateCell", {
                index : checks[i].rowIndexId,
                field : 'testResult',
                value : "<a href='#'><span class='label label-warning' style='font-size:14px'>Run</span></a>"
            });

            var paramObj={caseId:checks[i].id,rowId:checks[i].rowIndexId}
            paramsArray.push(paramObj);
        }
        //var obj = JSON.stringify(paramsArray);//将对象转换成json字符串
        var runParams={userId:"HQ001",type:type,caseIds:paramsArray};//1表示单线程，2表示多线程

        $.ajax({
            type:"post",
            url:"./TestCaseManage/runCase",
            contentType: 'application/json;charset=UTF-8', //内容类型
            traditional: true, //使json格式的字符串不会被转码
            data: JSON.stringify(runParams),
            success:function(data){
                for(var i=0;i<data.length;i++){
                    var execResult;
                    if(data[i].result){
                        execResult = "<a href='#'><span class='label label-success' style='font-size:14px'>Success</span></a>";
                    }else{
                        execResult = "<a href='#'><span class='label label-danger' style='font-size:16px'>Fail</span></a>";
                    }
                    //更新对应行的测试结果这一列的值
                    $('#testCaseTable').bootstrapTable("updateCell", {
                        index : data[i].rowId,
                        field : 'testResult',
                        value : execResult
                    });
                }
            }
        });
    }
}
