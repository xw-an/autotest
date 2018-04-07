package com.autotest.core.dao;

import com.autotest.core.model.TestLog;
import java.util.List;
import java.util.Map;

public interface ITestLog {

    public void insert(TestLog tLog);
    public List<TestLog> list(Map<String,String> parameters);
    public void update(TestLog tlog);
}
