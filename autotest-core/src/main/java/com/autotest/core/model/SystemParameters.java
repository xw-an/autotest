package com.autotest.core.model;

import java.util.Map;

public class SystemParameters {
    private static ThreadLocal<Map<String,Object>> commParameters=new ThreadLocal<Map<String,Object>>();

    public static Map<String, Object> getCommParameters() {
        return commParameters.get();
    }

    public static void setCommParameters(Map<String, Object> _commParameters) {
        commParameters.set(_commParameters);
    }

    public static void removeCommParameters(){
        commParameters.remove();
    }
}
