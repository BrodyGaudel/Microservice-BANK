package com.brodygaudel.bankaccountmanagement.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;

@Component
@Aspect
@Slf4j
public class AopConfig {
    @Before("execution(* com.brodygaudel.bankaccountmanagement.services.implementations.BankAccountServiceImpl.*(..))")
    public void logMethodServiceEntry(@NotNull JoinPoint joinPoint){
        String name = joinPoint.getSignature().getName();
        log.info("In method : "+name+":");
    }
}
