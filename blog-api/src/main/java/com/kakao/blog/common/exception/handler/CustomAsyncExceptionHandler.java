package com.kakao.blog.common.exception.handler;

import java.lang.reflect.Method;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;

@Slf4j
public class CustomAsyncExceptionHandler implements AsyncUncaughtExceptionHandler {

    @Override
    public void handleUncaughtException(Throwable throwable, Method method, Object... params) {
        String paramInfo = Stream.ofNullable(params).map(String::valueOf).collect(Collectors.joining(","));
        log.error("[AsyncUncaughtExceptionHandler] Exception message - {}, Method name - {}, params - {}", throwable.getMessage(), method.getName(), paramInfo);
    }
}
