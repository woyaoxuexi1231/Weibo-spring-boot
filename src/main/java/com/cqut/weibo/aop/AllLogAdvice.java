package com.cqut.weibo.aop;


import cn.hutool.core.date.StopWatch;
import cn.hutool.core.thread.ThreadFactoryBuilder;
import com.cqut.weibo.dao.LogMapper;
import com.cqut.weibo.dto.LogDO;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@Aspect
@Component
@Slf4j
public class AllLogAdvice {

    /**
     *
     */
    private static final ThreadPoolExecutor EXECUTOR = new ThreadPoolExecutor(5, 20, 20,
            TimeUnit.SECONDS, new ArrayBlockingQueue<>(150), new ThreadFactoryBuilder()
            .setNamePrefix("AllLogAdvice-thread-").build(), new ThreadPoolExecutor.CallerRunsPolicy());


    @Autowired
    LogMapper logMapper;

    @Around(value = "pointCut()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {

        // 计时器
        StopWatch stopWatch = new StopWatch();
        // 方法参数
        Object[] param = joinPoint.getArgs();

        Object result = new Object();

        String methodName = joinPoint.getSignature().getName();

        try {
            stopWatch.start();
            result = joinPoint.proceed();
            return result;
        } finally {
            stopWatch.stop();
            log.info("Invoke Method {}, Param: {}, Time: {}ms", methodName, param, stopWatch.getTotalTimeMillis());
            final LogDO logDO = new LogDO();
            logDO.setMethodName(methodName.length() > 256 ? methodName.substring(0, 256) : methodName);
            String paramStr = Arrays.toString(param);
            logDO.setMethodParam(paramStr.length() > 2048 ? paramStr.substring(0, 2048) : paramStr);
            logDO.setMethodResult(result.toString().length() > 2048 ? paramStr.substring(0, 2048) : result.toString());
            EXECUTOR.execute(() -> {
                logMapper.insert(logDO);
            });
        }

    }

    @Pointcut("execution(* com.cqut.weibo.controller.*.*(..)) ||" + "execution(* com.cqut.weibo.security.controller.*.*(..))")
    public void pointCut() {
    }
}
