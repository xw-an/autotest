package com.autotest.service.common;

import com.autotest.core.mapper.ITestUser;
import com.autotest.core.model.FailException;
import com.autotest.core.model.TestUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TestUserService implements ITestUserService {
    @Autowired
    private ITestUser testUser;

    /**
     * 根据username/userId和password查询用户
     * @param name
     * @param password
     * @return
     */
    @Override
    public TestUser selectUser(String name, String password) {
        TestUser user=testUser.select(name);
        if(user==null||user.equals("")) throw new FailException("用户名/用户id不正确");
        if(!user.getPassword().equals(password)) throw new FailException("密码不正确");
        return user;
    }
}
