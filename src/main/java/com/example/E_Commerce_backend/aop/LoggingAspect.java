package com.example.E_Commerce_backend.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class LoggingAspect {

    @Pointcut("execution(* com.example.E_Commerce_backend.service.ServiceImplementation.CustomerServiceImplementation.*(..))")
    public void logCustomerMethods(){}

    @Around("logCustomerMethods()")
    public Object logAround(ProceedingJoinPoint joinPoint)throws Throwable{

        String methodName=joinPoint.getSignature().getName();

        Object []args= joinPoint.getArgs();

        log.info("Entering {} with args {}",methodName,args);

        long start=System.currentTimeMillis();

        Object result;

        try
        {
            result=joinPoint.proceed();
        }
        catch (Throwable ex){
            log.error("Exception in {}: {}",methodName,ex.getMessage());
            throw ex;
        }

        long timeTaken=System.currentTimeMillis()-start;
        log.info("Existing {} with result {} ({} ms)",methodName,result,timeTaken);

        return result;

    }
}
