package com.wuyou.listener;

import com.wuyou.entity.Goods;
import com.wuyou.service.ISearchService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author Forest
 * @Date 2019/10/16
 */
@Component
public class RabbitMQListener {
    @Autowired
    private ISearchService searchService;

    @RabbitListener(queues = "search_queue")
    public void goodsMsgHandler(Goods goods) {
        System.out.println("接受到RabbitMQ的消息,并同步到索引库中");
        searchService.insert(goods);
    }
}
