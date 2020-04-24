package com.changgou.task;

import com.changgou.config.RabbitMQConfig;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @author cxl
 * @date 2020-04-18 20:53
 */
@Component
public class OrderTask {
    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Scheduled(cron = "0 0 0 * * ?")
    public void autoTake(){
        rabbitTemplate.convertAndSend("", RabbitMQConfig.ORDER_TACK,"随便写");
    }
}
