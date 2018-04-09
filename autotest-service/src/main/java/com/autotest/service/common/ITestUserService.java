package com.autotest.service.common;

import com.autotest.core.model.TestUser;

public interface ITestUserService {
    public TestUser selectUser(String name,String password);
}
