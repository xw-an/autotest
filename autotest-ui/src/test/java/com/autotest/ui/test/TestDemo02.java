package com.autotest.ui.test;

import org.testng.Assert;
import org.testng.annotations.Test;

public class TestDemo01 {

    @Test
    public void test01(){
        User u=new User();
        String email=u.getMail();
        Assert.assertNotNull(email);
        Assert.assertEquals(email,"123@123.com");
    }
}
