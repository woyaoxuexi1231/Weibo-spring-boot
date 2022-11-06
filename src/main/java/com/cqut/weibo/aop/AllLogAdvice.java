package com.cqut.weibo.aop;


import cn.hutool.core.date.StopWatch;
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

        // 计时器
        StopWatch stopWatch = new StopWatch();
        // 方法参数
        Object[] param = joinPoint.getArgs();

        try {
            stopWatch.start();
            return joinPoint.proceed();
        } finally {
            stopWatch.stop();
            log.info("Invoke Method {}, Param: {}, Time: {}ms", joinPoint.getSignature(), param, stopWatch.getTotalTimeMillis());
        }

    }

    @Pointcut("execution(* com.cqut.weibo.service.*.*(..)) ||" + "execution(* com.cqut.weibo.security.service.*.*(..))")
    public void pointCut() {
    }
}
