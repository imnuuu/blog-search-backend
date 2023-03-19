package com.kakao.blog.common.constant;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class Constants {

    public static class DefaultAsyncTask {

        private DefaultAsyncTask() {}

        public static final int CORE_POOL_SIZE = 2;
        public static final int MAX_POOL_SIZE = 10;
        public static final int QUEUE_CAPACITY = 500;
        public static final String THREAD_NAME = "AsyncExecutor";
    }

    public static final class BlogPath {

        public static final String[] SWAGGER_PATH = {
          "/authenticate*/**",
          "/swagger*/**",
          "/v*/api-docs/**",
          "/webjars/**",
          "/api-docs/**",
          "/swagger-ui.html",
          "/**/api-docs.yaml"
        };
        public static final String HEALTH_PATH = "/health";

        public static final String METRICS_PATH = "/metrics";

        public static final String FAVICON_ICO_PATH = "/favicon.ico";

        public static final String[] APIS_PATH = {
          "/api/v1/blog/**"
        };

        private BlogPath() {}
    }

}
