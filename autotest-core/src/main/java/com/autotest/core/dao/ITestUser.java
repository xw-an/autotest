package com.autotest.core.dao;

import com.autotest.core.model.TestUser;
import java.util.List;

public interface ITestUser {
    public void insert(TestUser tUser);
    public void delete(String userId);
    public TestUser select(String name);
    public void update(TestUser tUser);
    public List<TestUser> list();
}
