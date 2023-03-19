package com.kakao.blog.common.exception.handler;

import java.nio.charset.StandardCharsets;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.retry.RejectAndDontRequeueRecoverer;

@Slf4j
public class MqExceptionHandler extends RejectAndDontRequeueRecoverer {
    @Override
    public void recover(Message message, Throwable cause) {
        final String msg = new String(message.getBody(), StandardCharsets.UTF_8);
        log.error("Retries exhausted for message:{}, cause: {}", msg, cause);
    }
}
