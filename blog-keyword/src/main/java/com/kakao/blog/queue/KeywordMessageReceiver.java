package com.kakao.blog.queue;

import com.kakao.blog.common.MqConstants;
import com.kakao.blog.service.KeywordService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class KeywordMessageReceiver {

    private final KeywordService keywordService;

    @RabbitListener(queues = MqConstants.QUEUE_NAME)
    public void receiveMessage(final String keyword) {
        log.debug("[KeywordMessageReceiver] Receive Message: {}", keyword);
        keywordService.increaseKeywordCount(keyword);
    }

}
