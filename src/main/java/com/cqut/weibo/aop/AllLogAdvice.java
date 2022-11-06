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
            final LogDO logDO = LogDO.builder()
                    .methodName(methodName)
                    .methodParam(Arrays.toString(param))
                    .methodResult(result.toString())
                    .build();
            EXECUTOR.execute(() -> {
                logMapper.insert(logDO);
            });
        }

    }

    @Pointcut("execution(* com.cqut.weibo.service.*.*(..)) ||" + "execution(* com.cqut.weibo.security.service.*.*(..))")
    public void pointCut() {
    }
}
