package com.changgou.order.listener;

import com.changgou.order.config.RabbitMQConfig;
import com.changgou.order.service.OrderService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author cxl
 * @date 2020-04-18 21:03
 */

public class OrderTaskListener {

    @Autowired
    private OrderService orderService;
    @RabbitListener(queues = RabbitMQConfig.ORDER_TACK)
    public void autoTack(String message){
    //调用业务层完成自动确认收货
        orderService.autoTack();
    }
}
