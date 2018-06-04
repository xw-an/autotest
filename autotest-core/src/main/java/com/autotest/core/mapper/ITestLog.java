package com.autotest.core.mapper;

import com.autotest.core.annotation.DataSource;
import com.autotest.core.model.TestLog;
import java.util.List;
import java.util.Map;

@DataSource("autotest")
public interface ITestLog {

    public void insert(TestLog tLog);
    public List<TestLog> list(Map<String,Object> parameters);
    public void update(TestLog tlog);
    public void updateResultId(Map<String,Object> parameters);
}
