package com.wuyou.shop_item;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Forest
 * @Date 2019/10/16
 */
@Configuration
public class RabbitMQConfig {


    @Bean("search_queue")
    public Queue getQueue1(){
        return new Queue("search_queue");
    }

    @Bean("item_queue")
    public Queue getQueue2(){
        return new Queue("item_queue");
    }


    @Bean("goods_exchange")
    public FanoutExchange getExchange(){
        return new FanoutExchange("goods_exchange");
    }

    @Bean
    public Binding getBinding1(Queue search_queue, FanoutExchange goods_exchange){
        return BindingBuilder.bind(search_queue).to(goods_exchange);
    }

    @Bean
    public Binding getBinding2(Queue item_queue, FanoutExchange goods_exchange){
        return BindingBuilder.bind(item_queue).to(goods_exchange);
    }
}

