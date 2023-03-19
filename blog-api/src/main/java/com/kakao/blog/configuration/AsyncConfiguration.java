package com.kakao.blog.configuration;

import com.kakao.blog.common.constant.Constants;
import com.kakao.blog.common.exception.handler.CustomAsyncExceptionHandler;
import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@EnableAsync
@Configuration
public class AsyncConfiguration  implements AsyncConfigurer {
    @Override
    public Executor getAsyncExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(Constants.DefaultAsyncTask.CORE_POOL_SIZE);
        executor.setMaxPoolSize(Constants.DefaultAsyncTask.MAX_POOL_SIZE);
        executor.setQueueCapacity(Constants.DefaultAsyncTask.QUEUE_CAPACITY);
        executor.setThreadNamePrefix(Constants.DefaultAsyncTask.THREAD_NAME);
        //반드시 처리되어야 하는 경우, but main thread 성능에 영향
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        executor.initialize();
        return executor;
    }

    @Override
    public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
        return new CustomAsyncExceptionHandler();
    }

}
