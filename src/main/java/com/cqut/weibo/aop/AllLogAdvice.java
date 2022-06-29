package com.cqut.weibo.aop;


import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class AllLogAdvice {

    @Around(value = "pointCut()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        log.info("******************************** Request Info Start********************************");
        log.info("method: " + joinPoint.getSignature().toString());
        log.info("parameter: " + JSON.toJSONString(joinPoint.getArgs()));
        try {
            Object result = joinPoint.proceed();
            log.info("result: " + JSON.toJSONString(result));
            return result;
        } catch (Throwable throwable) {
            log.error("error info: " + throwable.getMessage());
            throw throwable;
        } finally {
            log.info("******************************** Request Info End********************************");
        }
    }

    @Pointcut("execution(* com.cqut.weibo.service.*.*(..)) ||" + "execution(* com.cqut.weibo.security.service.*.*(..))")
    public void pointCut() {
    }
}
