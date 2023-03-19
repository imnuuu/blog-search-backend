package com.kakao.blog.configuration;

import static com.kakao.blog.common.MqConstants.INITIAL_INTERVAL;
import static com.kakao.blog.common.MqConstants.MAX_INTERVAL;
import static com.kakao.blog.common.MqConstants.MAX_TRY_COUNT;
import static com.kakao.blog.common.MqConstants.MULTIPLIER;

import com.kakao.blog.common.MqConstants;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.config.RetryInterceptorBuilder;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class MQConfiguration {

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate();
        rabbitTemplate.setConnectionFactory(connectionFactory);
        rabbitTemplate.setMandatory(true);
        rabbitTemplate.setChannelTransacted(true);
        rabbitTemplate.setReplyTimeout(60000);
        rabbitTemplate.setMessageConverter(queueMessageConverter());
        return rabbitTemplate;
    }

    private Jackson2JsonMessageConverter queueMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

}
