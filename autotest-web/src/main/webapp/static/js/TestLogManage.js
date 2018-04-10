$(function(){
    //父表格展示对应测试用例的执行记录
    $('#testLogTable').bootstrapTable({
        method:"get",
        url: "./TestLogManage/LogList",
        dataType:"json",
        cache: false,
        striped: true,
        showRefresh: true,
        pagination: true,
        pageSize: 20,
        height:800,
        detailView:true,
        onExpandRow:logDetailTable,
        columns: [{
            field: 'id',
            title: 'id',
            align: 'center',
            valign: 'middle',
            width:'5%',
            visible: true
        },{
            field: 'caseId',
            title: '用例Id',
            align: 'center',
            valign: 'middle',
            width:'5%',
            visible: true
        },{
            field: 'userId',
            title: '执行人Id',
            align: 'center',
            valign: 'middle',
            width:'10%',
            visible: true
        },{
            field: 'testScenariosName',
            title: '用例名',
            align: 'center',
            valign: 'middle',
            width:'35%',
            visible: true
        },{
            field: 'testScenariosType',
            title: '用例类型',
            align: 'center',
            valign: 'middle',
            width:'35%',
            visible: true
        },{
            field: 'result',
            title: '运行结果',
            align: 'center',
            valign: 'middle',
            width:'10%',
            visible: true,
            cellStyle:resultCellStyle
        }]
    })
})

//运行结果状态展示
function resultCellStyle(value,row,index,field){
    if(value=="Fail"){
        return {
            css:{"color":"red","font-weight":"bold"}
        };
    }else if(value=="Success"){
        return {
            css:{"color":"green","font-weight":"bold"}
        };
    }else{
        return {
            css:{"color":"blue","font-weight":"bold"}
        };
    }
}

//子表格展示日志详细信息
function logDetailTable(index, row, $detail) {
    var resultId=row.id;
    var cur_table = $detail.html('<table></table>').find('table');
    $(cur_table).bootstrapTable({
        url: "./TestLogManage/LogDetail/"+resultId,
        method: 'post',
        cache: false,
        striped: true,
        pageSize: 10,
        columns: [{
            field: 'className',
            title: '类名',
            valign: 'middle',
            width:'15%',
            visible: true
        },{
            field: 'methodName',
            title: '方法名',
            valign: 'middle',
            width:'10%',
            visible: true
        },{
            field: 'runParams',
            title: '运行参数',
            valign: 'middle',
            width:'30%',
            visible: true
        },{
            field: 'logContent',
            title: '日志内容',
            valign: 'middle',
            width:'25%',
            visible: true
        },{
            field: 'logLevel',
            title: '日志级别',
            valign: 'middle',
            width:'5%',
            visible: true,
            cellStyle:LevelCellStyle
        },{
            field: 'remark',
            title: '备注信息',
            valign: 'middle',
            width:'5%',
            visible: true
        },{
            field: 'dataChange_CreateTime',
            title: '创建时间',
            valign: 'middle',
            width:'10%',
            visible: true
        }],
    });
}

//日志级别状态展示
function LevelCellStyle(value,row,index,field){
    if(value=="ERROR"){
        return {
            css:{"color":"red","font-weight":"bold"}
        };
    }else if(value=="INFO"){
        return {
            css:{"color":"green","font-weight":"bold"}
        };
    }else{
        return {
            css:{"color":"blue","font-weight":"bold"}
        };
    }
}

//根据筛选条件查询日志记录
function searchLog(){
    var params={};
    params.runStartTime=$('#runStartTime').val();
    params.runEndTime=$('#runEndTime').val();
    params.runUserId=$('#runUserId').val();;
    params.runCaseId=Number($('#runCaseId').val());
    params.runCaseName=$('#runCaseName').val();
    params.runCaseType=$('#runCaseType').val();
    params.runCaseResult=$('#runCaseResult').val();

    $.ajax({
        type:"post",
        url:"./TestLogManage/LogList",
        data:JSON.stringify(params),
        dataType:"json",
        contentType: "application/json",
        success:function(data){
            $('#testLogTable').bootstrapTable('load',data);
        }
    })
}