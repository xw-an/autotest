package com.autotest.core.util;

import com.autotest.core.annotation.DataSource;
import com.autotest.core.config.DynamicDataSourceHolder;
import com.autotest.core.config.SystemParameters;
import lombok.extern.log4j.Log4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Repository;
import java.lang.reflect.Method;

@Log4j
@Repository
@Aspect
public class DataSourceAspect {

    /**
     *拦截目标方法，获取由@DataSource指定的数据源标识，设置到线程存储中以便切换数据源
     * @param point
     */
    @Before("execution(* com.autotest.core.mapper.*.*(..))")
    public void intercept(JoinPoint point){
        Class<?> target = point.getTarget().getClass();
        MethodSignature signature = (MethodSignature) point.getSignature();
        // 默认使用目标类型的注解，如果没有则使用其实现接口的注解
        boolean hasAnnotation=resolveDataSource(target, signature.getMethod());
        if(!hasAnnotation){
            for (Class<?> clazz : target.getInterfaces()) {
                resolveDataSource(clazz, signature.getMethod());
            }
        }
    }

    /**
     * 提取目标对象方法注解和类型注解中的数据源标识
     * @param clazz
     * @param method
     */

    private boolean resolveDataSource(Class<?> clazz, Method method) {
        try {
            Class<?>[] types = method.getParameterTypes();
            DataSource source=null;
            // 默认使用类型注解
            if (clazz.isAnnotationPresent(DataSource.class)) {
                source = clazz.getAnnotation(DataSource.class);
            }
            // 方法注解可以覆盖类型注解
            Method m = clazz.getMethod(method.getName(), types);
            if (m != null && m.isAnnotationPresent(DataSource.class)) {
                source = m.getAnnotation(DataSource.class);
            }
            if(source==null){return false; }
            else{
                String dataSource=source.value();
                if(dataSource.contains("_")){
                    dataSource=dataSource+ SystemParameters.environment;
                }
                DynamicDataSourceHolder.setDataSource(dataSource);
                return true;
            }

        } catch (Exception e) {
            log.info(clazz + ":" + e.getMessage());
        }
        return false;
    }

/*    private void resolveDataSource(Class<?> clazz, Method method) {
        try {
            Class<?>[] types = method.getParameterTypes();
            // 默认使用类型注解
            if (clazz.isAnnotationPresent(DataSource.class)) {
                DataSource source = clazz.getAnnotation(DataSource.class);
                //System.out.println(source.value());
                DynamicDataSourceHolder.setDataSource(source.value());
            }
            // 方法注解可以覆盖类型注解
            Method m = clazz.getMethod(method.getName(), types);
            if (m != null && m.isAnnotationPresent(DataSource.class)) {
                DataSource source = m.getAnnotation(DataSource.class);
                //System.out.println(source.value());
                DynamicDataSourceHolder.setDataSource(source.value());
            }
        } catch (Exception e) {
            System.out.println(clazz + ":" + e.getMessage());
        }
    }*/
}
