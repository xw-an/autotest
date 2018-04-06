$(function(){
    //父表格展示对应测试用例的执行记录
    $('#testLogTable').bootstrapTable({
        method:"post",
        url: "./TestToolManage/LogList",
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
            visible: true
        },{
            field: 'caseId',
            title: '用例Id',
            align: 'center',
            valign: 'middle',
            visible: true
        },{
            field: 'userId',
            title: '用户Id',
            align: 'center',
            valign: 'middle',
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
            field: 'result',
            title: '运行结果',
            align: 'center',
            valign: 'middle',
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
    var execId=row.execId;
    var cur_table = $detail.html('<table></table>').find('table');
    $(cur_table).bootstrapTable({
        url: "./TestToolManage/LogDetail/"+execId,
        method: 'post',
        cache: false,
        striped: true,
        pageSize: 10,
        columns: [{
            field: 'logContent',
            title: '日志内容',
            valign: 'middle',
            visible: true
        },{
            field: 'remark',
            title: '备注信息',
            valign: 'middle',
            visible: true
        },{
            field: 'dataChange_CreateTime',
            title: '创建时间',
            valign: 'middle',
            visible: true
        }],
    });
}

//根据筛选条件查询日志记录
function searchLog(){
    var params={};
    params.runStartTime=$('#runStartTime').val();
    params.runEndTime=$('#runEndTime').val();
    params.runUserId=$('#runUserId').val();
    params.runCaseId=Number($('#runCaseId').val());
    params.runCaseName=$('#runCaseName').val();
    params.runCaseType=$('#runCaseType').val();
    params.runCaseResult=$('#runCaseResult').val();

    $.ajax({
        type:"post",
        url:"./TestToolManage/LogList",
        data:JSON.stringify(params),
        dataType:"json",
        contentType: "application/json",
        success:function(data){
            $('#testLogTable').bootstrapTable('load',data);
        }
    })
}