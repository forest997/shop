package com.wuyou.listener;

import com.wuyou.entity.Goods;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.FileWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Forest
 * @Date 2019/10/16
 */
@Component
public class RabbitMQListener {
    @Autowired
    private Configuration configuration;

    @RabbitListener(queues = "item_queue")
    public void goodsMsgHandler(Goods goods) {
        System.out.println("收到RabbitMQ消息:"+goods+",并开始创建静态页面");
        Map<String, Object> map = new HashMap<>();
        map.put("goods", goods);
        try (
                Writer writer = new FileWriter(RabbitListener.class.getResource("/static/html").getPath() + "/" + goods.getId() + ".html");
        ) {
            Template template = configuration.getTemplate("goods.ftl");
            template.process(map, writer);

        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}
