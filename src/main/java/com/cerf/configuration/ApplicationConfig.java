package com.cerf.configuration;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;
import java.util.Optional;

@Configuration
public class ApplicationConfig {

    @Autowired
    private Environment env;

    public String getProperty(String pPropertyKey) {
        return env.getProperty(pPropertyKey);
    }

    @Bean
    public Queue queue(){
        return new Queue(getProperty("QUEUE-NAME"),true);
    }

    @Bean
    public TopicExchange exchange(){
        return new TopicExchange(getProperty("EXCHANGE-NAME"));
    }

    @Bean
    public Binding binding(Queue queue,TopicExchange exchange){
        return BindingBuilder.bind(queue).to(exchange).with(getProperty("ROUTING-KEY"));
    }

    @Bean
    public MessageConverter converter(){
        return new Jackson2JsonMessageConverter();
    }
    @Bean
    public ConnectionFactory connectionFactory() {
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory();
        connectionFactory.setHost(env.getProperty("CONNECTION-URL"));
        connectionFactory.setPort(Integer.parseInt(env.getProperty("CONNECTION-PORT")));
        connectionFactory.setUsername(env.getProperty("USER-ID"));
        connectionFactory.setPassword(env.getProperty("USER-PWD"));
        return connectionFactory;
    }

    @Bean
    public AmqpTemplate template(ConnectionFactory connectionFactory){
        final RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(converter());
        return rabbitTemplate;
    }

    @Bean
    public AmqpAdmin amqpAdmin() {
        return new RabbitAdmin(connectionFactory());
    }

    @Bean
    public SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory() {
        final SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory());
        factory.setMessageConverter(converter());
        factory.setConcurrentConsumers(Integer.parseInt(Optional.ofNullable(env.getProperty("CONCURRENT-CONSUMERS")).orElse("2")));
        factory.setMaxConcurrentConsumers(Integer.parseInt(Optional.ofNullable(env.getProperty("MAX-CONCURRENT-CONSUMERS")).orElse("5")));
        return factory;
    }

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder restTemplateBuilder){
        RestTemplate restTemplate= restTemplateBuilder
                .setConnectTimeout(Duration.ofMillis(Integer.parseInt(Optional.ofNullable(env.getProperty("CONNECTION-TIMEOUT")).orElse("5000"))))
                .setReadTimeout(Duration.ofMillis(Integer.parseInt(Optional.ofNullable(env.getProperty("SOCKET-TIMEOUT")).orElse("5000"))))
                .build();
        return restTemplate;

    }
}
