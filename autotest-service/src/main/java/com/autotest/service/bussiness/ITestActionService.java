package com.autotest.service.bussiness;

import java.util.List;
import java.util.Map;

public interface ITestActionService {
    public Map<String,String> selectKey(String actionType);

    public boolean execDb(Map<String, Object> dbMaps);

    public boolean execCallInterface(Map<String, Object> callInterfaceMaps);

    public boolean execCheckValue(Map<String, Object> checkValueMaps);

    public boolean execSetParameter(Map<String, Object> setParameterMaps);
}
