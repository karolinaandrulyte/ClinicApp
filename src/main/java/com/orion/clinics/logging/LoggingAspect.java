package com.orion.clinics.logging;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Aspect
@Component
@Slf4j
public class LoggingAspect {

    @Pointcut("execution(public * com.orion.clinics.services.*.*(..))")
    private void publicMethodsFromServicesPackage() {}

    @Around(value = "publicMethodsFromServicesPackage()")
    public Object logAround(ProceedingJoinPoint joinPoint) throws Throwable {
        Object[] args = joinPoint.getArgs();
        String methodName = joinPoint.getSignature().getName();

        log.debug(">> {}() - {}", methodName, Arrays.toString(args));
        Object result = joinPoint.proceed();

        log.debug("<< {}() - {}", methodName, result);
        return result;
    }

    @AfterThrowing(pointcut = "publicMethodsFromServicesPackage()", throwing = "exception")
    public void logException(JoinPoint joinPoint, Throwable exception) {
        String methodName = joinPoint.getSignature().getName();
        log.error("<< {}() - {}", methodName, exception.getMessage());
    }
}
