package com.autotest.core.mapper;

import com.autotest.core.annotation.DataSource;
import com.autotest.core.model.TestUser;
import java.util.List;

@DataSource("autotest")
public interface ITestUser {
    public void insert(TestUser tUser);
    public void delete(String userId);
    public TestUser select(String name);
    public void update(TestUser tUser);
    public List<TestUser> list();
}
