package com.kakao.blog.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import com.kakao.blog.common.MqConstants;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

@Nested
@SpringBootTest
@ActiveProfiles("test")
class QueueServiceTest {
    @MockBean
    RabbitTemplate rabbitTemplate;

    @Autowired
    QueueService queueService;

    @Test
    void sendEvent() {

        //given
        willDoNothing().given(rabbitTemplate).convertAndSend(any(String.class), any(String.class),any(Object.class));

        //when
        queueService.sendEvent("test");

        //then
        verify(rabbitTemplate, times(1)).convertAndSend(MqConstants.QUEUE_EXCHANGE,MqConstants.QUEUE_ROUTING_KEY,"test");

    }
}