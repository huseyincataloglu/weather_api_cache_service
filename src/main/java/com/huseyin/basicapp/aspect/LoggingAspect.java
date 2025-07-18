package com.huseyin.basicapp.aspect;


import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Aspect
@Slf4j
@Component
public class LoggingAspect {

    @Pointcut("execution(* com.huseyin.basicapp.weather.service..*(..))")
    public void serviceMethods() {}


    @Before("serviceMethods()")
    public void logBefore(JoinPoint joinPoint){
        log.info("Called method :{} parameters: {}",joinPoint.getSignature(),Arrays.toString(joinPoint.getArgs()));
    }


    @AfterThrowing(
            pointcut = "serviceMethods()",
            throwing = "ex"
    )
    public void logExceptions(JoinPoint joinPoint,Throwable ex){
        Object[] args = joinPoint.getArgs();
        log.error("An error has occurred in method : {}}",joinPoint.getSignature());
        log.error("Method parameter: {}",Arrays.toString(args));
        log.error("Exception message: {}",ex.getMessage(),ex);
    }


}
