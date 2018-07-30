package com.mxt.mysql.masterSlave.config;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Component;

import java.util.List;

@Aspect
@Component
public class DataSourceAspect {

    private final static Logger logger = LoggerFactory.getLogger(DataSourceAspect.class);

    @Before("execution(* com.mxt.mysql.masterSlave.service.*.*(..))")
    public void before(JoinPoint joinPoint) {
        String className = joinPoint.getTarget().getClass().getName();
        String method = joinPoint.getSignature().getName();
        String args = args(joinPoint.getArgs());
        logger.info("className:{}, method:{}, args:{} ", className, method, args);
        for (DataSourceType type: DataSourceType.values()) {
            List<String> list = DynamicDataSource.methodTypeMap.get(type);
            for (String key: list) {
                if (method.toLowerCase().indexOf(key.toLowerCase()) > -1) {
                    logger.info(">>{} 方法使用的数据源为:{}<<", method, key);
                    DatabaseContextHolder.setDatabaseType(type);
                    DataSourceType types = DatabaseContextHolder.getDatabaseType();
                    logger.info(">>{}方法使用的数据源为:{}<<", method, types);
                }
            }
        }
    }

    private String args(Object[] args) {
        StringBuilder stringBuilder = new StringBuilder();
        if (args != null) {
            for (int i = 0; i < args.length; i++) {
                stringBuilder.append(args[i].getClass()).append(";");
            }
        }
        return stringBuilder.toString();
    }
}
