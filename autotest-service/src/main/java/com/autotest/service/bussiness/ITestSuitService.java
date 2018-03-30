package com.autotest.service.bussiness;

import com.autotest.core.model.TestSuit;
import java.util.Map;

public interface ITestSuitService {

    public Map<Integer,Boolean> runByOrder(TestSuit tSuit);
    public Map<Integer,Boolean> runBySynchronize(TestSuit tSuit);
}
