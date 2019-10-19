package com.wuyou.shop_email;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Forest
 * @Date 2019/10/18
 */
@Configuration
public class RabbitMQConfig {
    @Bean("emailQueue")
    public Queue getQueue(){
        return new Queue("email_queue");
    }

    @Bean("emailExchange")
    public FanoutExchange getExchange(){
        return new FanoutExchange("email_exchange");
    }

    @Bean
    public Binding getBinding(Queue emailQueue,FanoutExchange emailExchange){
        return BindingBuilder.bind(emailQueue).to(emailExchange);
    }

}
