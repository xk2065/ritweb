package com.yuwen.rtiweb.aspect;


import com.alibaba.fastjson2.JSON;
import com.yuwen.rtiweb.annotation.LogAnnotation;
import com.yuwen.rtiweb.entity.Log;
import com.yuwen.rtiweb.service.LogService;
import lombok.var;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Date;

/**
 * @author Administrator
 */
@Aspect
@Component
public class LogAspect {

    @Autowired
    private LogService logService;

    @Pointcut("@annotation(com.yuwen.rtiweb.annotation.LogAnnotation)")
    public void logPointcut() {
    }

    @AfterReturning(pointcut = "logPointcut()", returning = "result")
    public void afterReturning(JoinPoint joinPoint, Object result) throws NoSuchMethodException {
        // 获取注解值
        String annotationValue = getAnnotationValue(joinPoint);
        Log logEntity = new Log();
        logEntity.setContent(JSON.toJSONString(result));
        logEntity.setCreateTime(new Date());
        logEntity.setTableName("log_table_" + annotationValue.toLowerCase());
        logService.saveLog(logEntity);
    }

    private String getAnnotationValue(@NotNull JoinPoint joinPoint) throws NoSuchMethodException {
        // 从JoinPoint获取方法签名
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        var parameterTypes = signature.getParameterTypes();
        Method method = joinPoint.getTarget().getClass().getMethod(signature.getName(), parameterTypes);
        LogAnnotation logAnnotation = method.getAnnotation(LogAnnotation.class);
        return (logAnnotation != null) ? logAnnotation.value() : "Default";
    }
}
