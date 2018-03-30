package com.autotest.core.util;

import com.autotest.core.model.SystemParameters;

import java.util.HashMap;
import java.util.Map;

public class SystemParametersUtil {

    public static void addParameters(String key,Object value){
        Map<String,Object> ps= SystemParameters.getCommParameters();
        if(ps==null||ps.equals("")){
            ps=new HashMap<>();
        }
        ps.put(key,value);
        SystemParameters.setCommParameters(ps);
    }
}
