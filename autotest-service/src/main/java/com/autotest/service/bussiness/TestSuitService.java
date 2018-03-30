package com.autotest.service.bussiness;

import com.autotest.core.model.SystemParameters;
import com.autotest.core.model.TestSuit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

@Service
public class TestSuitService implements ITestSuitService {

    @Autowired
    private ITestCaseService tCaseService;

    /**
     * 顺序运行测试用例
     * @param tSuit 测试用例集
     * @return 返回测试用例集的执行结果
     */
    @Override
    public Map<Integer, Boolean> runByOrder(TestSuit tSuit) {
        Map<Integer, Boolean> tSuitResult=new HashMap<>();
        List<Integer> caseIds=tSuit.getCaseIds();
        for(Integer caseId:caseIds){
           boolean result=tCaseService.runTestCase(caseId);
           tSuitResult.put(caseId,result);
        }
        return tSuitResult;
    }

    /**
     * 并行运行测试用例
     * @param tSuit 测试用例集
     * @return 返回测试用例集的执行结果
     */
    @Override
    public Map<Integer, Boolean> runBySynchronize(TestSuit tSuit) {
        Map<Integer, Boolean> tSuitResult=new HashMap<>();
        List<Integer> caseIds=tSuit.getCaseIds();
        /*
        创建线程池，并行运行测试用例
         */
        ExecutorService pool= Executors.newFixedThreadPool(caseIds.size());
        List<Future<?>> futures= new ArrayList<>();
        for(Integer caseId:caseIds){
            futures.add(pool.submit(new Runnable() {
                @Override
                public void run() {
                    boolean result=tCaseService.runTestCase(caseId);
                    tSuitResult.put(caseId,result);
                }
            }));
        }
        Iterator<Future<?>> it=futures.iterator();
        while(it.hasNext()){
            try {
                it.next().get();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }
        return tSuitResult;
    }
}
