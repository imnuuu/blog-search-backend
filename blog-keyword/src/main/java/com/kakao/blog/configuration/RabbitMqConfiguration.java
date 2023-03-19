package com.kakao.blog.configuration;

import static com.kakao.blog.common.MqConstants.INITIAL_INTERVAL;
import static com.kakao.blog.common.MqConstants.MAX_INTERVAL;
import static com.kakao.blog.common.MqConstants.MAX_TRY_COUNT;
import static com.kakao.blog.common.MqConstants.MULTIPLIER;

import com.kakao.blog.common.MqConstants;
import com.kakao.blog.common.exception.handler.MqExceptionHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.config.RetryInterceptorBuilder;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class RabbitMqConfiguration {

    @Bean
    public Queue keywordQueue() {

        return new Queue(MqConstants.QUEUE_NAME, false);
    }

    @Bean
    public TopicExchange keywordTopicExchange() {
        return new TopicExchange(MqConstants.QUEUE_EXCHANGE, true, false);
    }

    @Bean
    public Binding keywordBinding(Queue keywordQueue, TopicExchange keywordTopicExchange) {
        return BindingBuilder.bind(keywordQueue).to(keywordTopicExchange).with(MqConstants.QUEUE_ROUTING_KEY);
    }

    @Bean
    public SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory(ConnectionFactory connectionFactory) {
        final SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        factory.setDefaultRequeueRejected(false);
        factory.setMessageConverter(queueMessageConverter());
        factory.setChannelTransacted(true);
        factory.setAdviceChain(RetryInterceptorBuilder
          .stateless()
          .maxAttempts(MAX_TRY_COUNT)
          .recoverer(new MqExceptionHandler())
          .backOffOptions(INITIAL_INTERVAL, MULTIPLIER, MAX_INTERVAL)
          .build());
        return factory;
    }

    private Jackson2JsonMessageConverter queueMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }


}
