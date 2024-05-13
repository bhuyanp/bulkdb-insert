package com.example.bulkdbinsert.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StopWatch;

import java.util.Map;

@Aspect
@Configuration
@Slf4j
public class TimedAspect {

    @Around("@annotation(com.example.bulkdbinsert.annotation.Timed)")
    public Object processModelParam(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        StopWatch sw = new StopWatch();
        sw.start();
        Object response = proceedingJoinPoint.proceed();
        sw.stop();
        double totalTimeSeconds = sw.getTotalTimeSeconds();
		log.info("Time Taken(s): {}",totalTimeSeconds);
		if (response instanceof Map mapResponse) {
			mapResponse.put("Time Taken(s)", ""+totalTimeSeconds);
            return mapResponse;
        } else {
            return response;
        }
    }
}
