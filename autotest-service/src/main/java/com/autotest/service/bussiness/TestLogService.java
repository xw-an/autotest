package com.autotest.service.bussiness;

import com.autotest.core.mapper.ITestLog;
import com.autotest.core.model.TestLog;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Log4j
@Service
public class TestLogService implements ITestLogService {
    @Autowired
    private ITestLog testLog;

    /**
     * 根据执行结果resultid获取所有执行日志记录
     * @param resultId
     * @return
     */
    @Override
    public List<TestLog> selectByResultId(int resultId) {
        if(resultId<0) return null;
        else{
            Map<String,Object> parameters=new HashMap<>();
            parameters.put("resultId",resultId);
            return testLog.list(parameters);
        }
    }

    /**
     * 插入日志记录
     * @param tLog
     */
    @Override
    public void insertLog(TestLog tLog) {
        testLog.insert(tLog);
    }

    /**
     * 更新对应日志记录的resultId
     * @param resultId
     * @param logIds
     */
    @Override
    public void updateResultIdById(int resultId, List<Integer> logIds) {
        if(logIds.size()<1) return;
        else if(resultId==0) return;
        else {
            for(Integer i:logIds){
                Map<String,Object> parameter=new HashMap<>();
                parameter.put("resultId",resultId);
                parameter.put("id",i);
                testLog.updateResultId(parameter);
            }
        }
    }
}
