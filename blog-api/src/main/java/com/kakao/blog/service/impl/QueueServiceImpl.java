package com.kakao.blog.service.impl;

import com.kakao.blog.common.MqConstants;
import com.kakao.blog.service.QueueService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class QueueServiceImpl implements QueueService {

    private final RabbitTemplate rabbitTemplate;

    @Async
    @Override
    public void sendEvent(String keyword) {
        log.debug("[QueueService] Send Message: {}", keyword);
        rabbitTemplate.convertAndSend(MqConstants.QUEUE_EXCHANGE,MqConstants.QUEUE_ROUTING_KEY,keyword);
    }
}
