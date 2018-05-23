package com.autotest.service.bussiness;

import com.alibaba.fastjson.JSON;
import com.autotest.core.mapper.ITestAction;
import com.autotest.core.config.SystemParameters;
import com.autotest.core.model.TestAction;
import com.autotest.core.util.HttpClient;
import com.autotest.core.util.SystemParametersUtil;
import lombok.extern.log4j.Log4j;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.expression.*;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.sql.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Log4j
@Service
public class TestActionService implements ITestActionService {
    @Autowired
    private ITestAction taDao;

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
        String runParams=JSON.toJSONString(dbMaps);
        runParams=runParams.replace("'","\\\'");
        MDC.put("runParams",runParams);//TODO 这个字段插入数据库时出错，需要解决该问题
        boolean execResult=false;
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
            String value= SystemParameters.getCommParameters().get(key).toString();
            sql = sql.replace(tempValue,value);
            log.info("替换sql语句中的变量。"+key+":"+value);
            log.info("当前sql语句为："+sql);
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
            log.info("创建jdbc连接");
            /*
                分类型执行sql语句
             */
            if (sql.toLowerCase().contains("select")) {
                log.info("开始执行select语句。");
                rs = stat.executeQuery(sql);
                 /*
                     将查询结果赋值给对应变量，并保存到全局变量中
                */
                String[] params = parameterName.split(",");
                log.info("将查询结果赋值给对应变量，并保存到全局变量中。");
                while (rs.next()) {
                    for (int i = 0; i < params.length; i++) {
                        SystemParametersUtil.addParameters(params[i], rs.getObject(i + 1));
                        log.info("保存全局变量"+params[i]+":"+rs.getObject(i + 1));
                    }
                }
            } else {
                log.info("开始执行非select语句。");
                stat.executeUpdate(sql);
            }
            log.info("数据库操作方法执行成功！");
            execResult=true;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            execResult=false;
            log.error("数据库操作执行异常:"+e.getMessage());
        } catch (SQLException e) {
            e.printStackTrace();
            execResult=false;
            log.error("数据库操作执行异常:"+e.getMessage());
        } finally {
            try {
                if (rs != null) rs.close();
                if (stat != null) stat.close();
                if (conn != null) conn.close();
                log.info("关闭jdbc连接");
            } catch (SQLException e) {
                e.printStackTrace();
                log.error("关闭jdbc连接异常:"+e.getMessage());
            }
            MDC.remove("runParams");
        }
        log.info("数据库操作结果："+execResult);
        return execResult;
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
        String runParams=JSON.toJSONString(callInterfaceMaps);
        MDC.put("runParams",runParams);//TODO 这个字段插入数据库时出错，需要解决该问题
        boolean execResult=false;
        String reqType = (String) callInterfaceMaps.get("reqType");
        String parameterName = (String) callInterfaceMaps.get("parameterName");
        String reqHeader = (String) callInterfaceMaps.get("reqHeader");
        String reqData=(String)callInterfaceMaps.get("reqData");

        if (reqHeader.equals("") || reqHeader == null) {
            callInterfaceMaps.put("reqHeader", new HashMap<>());
        } else {
            Map reqHeaderMap = JSON.parseObject(reqHeader, Map.class);
            callInterfaceMaps.put("reqHeader", reqHeaderMap);
        }
        /*
         * 组装reqData，${参数名}从全局变量中寻找对应的值做替换
         */
        String pattern = "(\\$\\{)([a-zA-Z0-9]*)(\\})";
        Pattern p = Pattern.compile(pattern);
        Matcher m = p.matcher(reqData);
        while (m.find()) {
            String tempValue = m.group();
            String key = tempValue.substring(2, tempValue.indexOf("}"));
            String value= SystemParameters.getCommParameters().get(key).toString();
            reqData = reqData.replace(tempValue,value);
            log.info("替换请求报文中的变量。"+key+":"+value);
            log.info("当前请求报文为："+reqData);
        }
        callInterfaceMaps.put("reqData",reqData);
        /*
        开始调用接口
         */
        try {
            String respData = null;
            if (reqType.equalsIgnoreCase("json")) {
                log.info("json格式调用接口");
                respData = HttpClient.getResponseByJson(callInterfaceMaps);
                log.info("接口返回报文:"+respData);
            }
            SystemParametersUtil.addParameters(parameterName, respData);
            log.info("将返回报文保存到全局变量中:"+parameterName+":"+respData);
            log.info("调用接口操作成功");
            execResult= true;
        } catch (Exception e) {
            e.printStackTrace();
            execResult=false;
            log.error("调用接口异常："+e.getMessage());
        }finally{
            MDC.remove("runParams");
        }
        log.info("调用接口操作结果:"+execResult);
        return execResult;
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
        String runParams=JSON.toJSONString(checkValueMaps);
        MDC.put("runParams",runParams);
        boolean execResult=false;

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
                    log.info("直接进行值比较");
                    if (expectValue.trim().equalsIgnoreCase(actualValue.trim())) {
                        execResult=true;
                        log.info("比较结果一致，实际值:"+actualValue.trim()+"预期值:"+expectValue.trim());
                    }else{
                        execResult=false;
                        log.error("比较结果不一致，实际值:"+actualValue.trim()+"预期值:"+expectValue.trim());
                    }
                    break;
                case 1:
                    /*
                    在全局变量中获取变量的值，然后进行值比较
                     */
                    log.info("在全局变量中获取变量的值，然后进行值比较");
                    String key = actualValue.substring(2, actualValue.indexOf("}"));
                    String actValue = SystemParameters.getCommParameters().get(key).toString();
                    log.info("获取变量值,"+key+":"+actValue);
                    if (actValue == null || actValue.equals("")){
                        execResult=false;
                        log.error("比较结果不一致，全局变量值为空");
                    }else if (actValue.trim().equalsIgnoreCase(expectValue.trim())) {
                        execResult=true;
                        log.info("比较结果一致，实际值:"+actValue.trim()+"预期值:"+expectValue.trim());
                    }else{
                        execResult=false;
                        log.error("比较结果不一致，实际值:"+actValue.trim()+"预期值:"+expectValue.trim());
                    }
                    break;
                case 2:
                    /*
                    通过执行springEL表达式获取结果
                     */
                    log.info("通过执行springEL表达式获取结果，然后进行值比较");
                    EvaluationContext context = new StandardEvaluationContext();//表达式上下文
                    Map<String, Object> sysParameters = SystemParameters.getCommParameters();
                    for (String k : sysParameters.keySet()) {
                        context.setVariable(k, sysParameters.get(k));
                    }

                    context.setVariable("json", com.alibaba.fastjson.JSON.class);//保存json处理类
                    ExpressionParser parser = new SpelExpressionParser();//创建解析器
                    Expression exp = parser.parseExpression(actualValue);//解析表达式
                    String actVal = exp.getValue(context, String.class);//根据springel表达式获取结果
                    log.info("根据springEl表达式获取到结果:"+actVal);
                    if (actVal == null || actVal.equals("")){
                        execResult=false;
                        log.error("比较失败:根据springEL表达式没有获取到结果");
                    }else if (actVal.trim().equalsIgnoreCase(expectValue.trim())) {
                        execResult=true;
                        log.info("比较结果一致，实际值:"+actVal.trim()+"预期值:"+expectValue.trim());
                    }else{
                        execResult=false;
                        log.error("比较结果不一致，实际值:"+actVal.trim()+"预期值:"+expectValue.trim());
                    }
                    break;
                default:
                    execResult=false;
                    break;
            }
        } catch (ParseException e) {
            e.printStackTrace();
            execResult=false;
            log.error("执行异常"+e.getMessage());
        } catch (EvaluationException e) {
            e.printStackTrace();
            execResult=false;
            log.error("执行异常"+e.getMessage());
        }finally{
            MDC.remove("runParams");
        }
        log.info("比较数据操作结果:"+execResult);
        return execResult;
    }

    /**
     * 设置全局变量
     * @param setParameterMaps
     * @return
     */
    @Override
    @Transactional
    public boolean execSetParameter(Map<String, Object> setParameterMaps) {
        String runParams=JSON.toJSONString(setParameterMaps);
        MDC.put("runParams",runParams);
        boolean execResult=false;

        String parameterName = setParameterMaps.get("parameterName").toString();
        String parameterValue = setParameterMaps.get("parameterValue").toString();
        try {
            SystemParametersUtil.addParameters(parameterName, parameterValue);
            execResult=true;
            log.info("添加全局变量:"+parameterName+":"+parameterValue);
        } catch (Exception e) {
            e.printStackTrace();
            execResult=false;
            log.error("执行异常:"+e.getMessage());
        }finally{
            MDC.remove("runParams");
        }
        log.info("设置变量操作结果:"+execResult);
        return execResult;
    }
}
