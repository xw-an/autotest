package com.autotest.service.bussiness;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.autotest.core.dao.ITestAction;
import com.autotest.core.model.SystemParameters;
import com.autotest.core.model.TestAction;
import com.autotest.core.util.HttpClient;
import com.autotest.core.util.SystemParametersUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.expression.*;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class TestActionService implements ITestActionService {
    @Autowired
    private ITestAction taDao;
    private Log logger= LogFactory.getLog(this.getClass());

    /**
     * 根据操作类型返回对应的属性
     *
     * @param actionType
     * @return
     */
    @Override
    public Map<String,String> selectKey(String actionType) {
        Map<String,String> actionMap=new HashMap<>();
        Map<String, Object> maps = new HashMap<String, Object>();
        maps.put("actionType", actionType);
        List<TestAction> lta = taDao.selectList(maps);
        for (TestAction ta : lta) {
            actionMap.put(ta.getKeyName(),ta.getName());
        }
        return actionMap;
    }

    /**
     * 执行数据库操作，并且返回执行结果
     *
     * @param dbMaps
     * @return
     */
    @Override
    @Transactional
    public boolean execDb(Map<String, Object> dbMaps) {
        String url = dbMaps.get("conn").toString();
        String username = dbMaps.get("username").toString();
        String password = dbMaps.get("password").toString();
        String sql = dbMaps.get("sql").toString();
        String parameterName = dbMaps.get("parameterName").toString();
        /*
            组装sql语句，${参数名}从全局变量中寻找对应的值做替换
         */
        String pattern = "(\\$\\{)([a-zA-Z0-9]*)(\\})";
        Pattern p = Pattern.compile(pattern);
        Matcher m = p.matcher(sql);
        while (m.find()) {
            String tempValue = m.group();
            String key = tempValue.substring(2, tempValue.indexOf("}"));
            sql = sql.replace(tempValue, SystemParameters.getCommParameters().get(key).toString());
        }

        /*
            用jdbc的方式数据库操作，执行sql
         */
        Connection conn = null;
        Statement stat = null;
        ResultSet rs = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection(url, username, password);
            stat = conn.createStatement();

            /*
                分类型执行sql语句
             */
            if (sql.toLowerCase().contains("select")) {
                rs = stat.executeQuery(sql);
                 /*
                     将查询结果赋值给对应变量，并保存到全局变量中
                */
                String[] params = parameterName.split(",");

                while (rs.next()) {
                    for (int i = 0; i < params.length; i++) {
                        SystemParametersUtil.addParameters(params[i], rs.getObject(i + 1));
                    }
                }
            } else {
                stat.executeUpdate(sql);
            }
            return true;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (stat != null) stat.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    /**
     * 执行调用接口操作，并且返回执行结果
     *
     * @param callInterfaceMaps
     * @return
     */
    @Override
    @Transactional
    public boolean execCallInterface(Map<String, Object> callInterfaceMaps) {
        String reqType = (String) callInterfaceMaps.get("reqType");
        String parameterName = (String) callInterfaceMaps.get("parameterName");
        String reqHeader = (String) callInterfaceMaps.get("reqHeader");
        if (reqHeader.equals("") || reqHeader == null) {
            callInterfaceMaps.put("reqHeader", new HashMap<>());
        } else {
            Map reqHeaderMap = JSON.parseObject(reqHeader, Map.class);
            callInterfaceMaps.put("reqHeader", reqHeaderMap);
        }
        try {
            String respData = null;
            if (reqType.equalsIgnoreCase("json")) {
                respData = HttpClient.getResponseByJson(callInterfaceMaps);
            }
            SystemParametersUtil.addParameters(parameterName, respData);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }


    /**
     * 执行检查值功能，并且返回校验结果
     *
     * @param checkValueMaps
     * @return
     */
    @Override
    @Transactional
    public boolean execCheckValue(Map<String, Object> checkValueMaps) {
        int type = Integer.parseInt(checkValueMaps.get("type").toString());
        String expectValue = checkValueMaps.get("expectValue").toString();
        String actualValue = checkValueMaps.get("actualValue").toString();
        /*
        获取实际值比较类型，0值比较/1获取变量值比较/2执行表达式比较
         */
        try {
            switch (type) {
                case 0:
                    /*
                    直接进行值比较
                     */
                    if (expectValue.trim().equalsIgnoreCase(actualValue.trim())) return true;
                    break;
                case 1:
                    /*
                    在全局变量中获取变量的值，然后进行值比较
                     */
                    String key = expectValue.substring(2, expectValue.indexOf("}"));
                    String expValue = SystemParameters.getCommParameters().get(key).toString();
                    if (expValue == null || expValue.equals("")) return false;
                    else if (expValue.trim().equalsIgnoreCase(actualValue.trim())) return true;
                    break;
                case 2:
                    /*
                    通过执行springEL表达式获取结果
                     */
                    EvaluationContext context = new StandardEvaluationContext();//表达式上下文
                    Map<String, Object> sysParameters = SystemParameters.getCommParameters();
                    for (String k : sysParameters.keySet()) {
                        context.setVariable(k, sysParameters.get(k));
                    }

                    context.setVariable("json", com.alibaba.fastjson.JSON.class);//保存json处理类
                    ExpressionParser parser = new SpelExpressionParser();//创建解析器
                    Expression exp = parser.parseExpression(expectValue);//解析表达式
                    String expVal = exp.getValue(context, String.class);//根据springel表达式获取结果
                    if (expVal == null || expVal.equals("")) return false;
                    else if (expVal.trim().equalsIgnoreCase(actualValue.trim())) return true;
                    break;
                default:
                    return false;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (EvaluationException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    @Transactional
    public boolean execSetParameter(Map<String, Object> setParameterMaps) {
        String parameterName = setParameterMaps.get("parameterName").toString();
        String parameterValue = setParameterMaps.get("parameterValue").toString();
        try {
            SystemParametersUtil.addParameters(parameterName, parameterValue);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
