package com.kakao.blog.common.constants;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class Constants {
    public static final class KeywordPath {

        public static final String HEALTH_PATH = "/health";

        public static final String METRICS_PATH = "/metrics";

        public static final String FAVICON_ICO_PATH = "/favicon.ico";

        public static final String[] APIS_PATH = {
          "/api/v1/keyword/**"
        };

        private KeywordPath() {}
    }

}
