package com.github.kegszool.messaging.configuration;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfiguration {

    @Value("${spring.rabbitmq.template.exchange}")
    private String EXCHANGE_NAME;

    @Value("${spring.rabbitmq.queues.request_to_exchange}")
    private String REQUEST_TO_EXCHANGE_QUEUE;

    @Value("${spring.rabbitmq.queues.response_from_exchange}")
    private String RESPONSE_FROM_EXCHANGE_QUEUE;

    @Value("${spring.rabbitmq.template.routing-key.coin_price_request}")
    private String COIN_PRICE_REQUEST_ROUTING_KEY;

    @Value("${spring.rabbitmq.template.routing-key.coin_price_response}")
    private String COIN_PRICE_RESPONSE_ROUTING_KEY;

    @Bean
    public DirectExchange exchange() {
        return new DirectExchange(EXCHANGE_NAME, true, false);
    }

    @Bean
    public Queue requestToExchangeQueue() {
        return new Queue(REQUEST_TO_EXCHANGE_QUEUE);
    }

    @Bean
    public Queue responseFromExchangeQueue() {
        return new Queue(RESPONSE_FROM_EXCHANGE_QUEUE);
    }

    @Bean
    public Binding bindingCoinPriceRequestToExchange() {
        return BindingBuilder.bind(requestToExchangeQueue())
                .to(exchange())
                .with(COIN_PRICE_REQUEST_ROUTING_KEY);
    }

    @Bean
    public Binding bindingResponsePriceFromExchange() {
        return BindingBuilder.bind(responseFromExchangeQueue())
                .to(exchange())
                .with(COIN_PRICE_RESPONSE_ROUTING_KEY);
    }

    @Bean
    public Jackson2JsonMessageConverter jackson2JsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }
}