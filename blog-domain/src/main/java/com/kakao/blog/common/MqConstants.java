package com.kakao.blog.common;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class MqConstants {

    public static final String QUEUE_NAME = "blog-keyword";
    public static final String QUEUE_EXCHANGE = "blog-keyword.exchange";
    public static final String QUEUE_ROUTING_KEY = "blog-keyword.routing.#";

    public static final int INITIAL_INTERVAL = 1000;
    public static final int MAX_TRY_COUNT = 3;
    public static final int MULTIPLIER = 3;
    public static final int MAX_INTERVAL = 40000;

}
