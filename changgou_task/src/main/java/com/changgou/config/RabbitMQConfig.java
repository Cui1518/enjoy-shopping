package com.changgou.config;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author cxl
 * @date 2020-04-18 20:45
 */
@Configuration
public class RabbitMQConfig {
    //添加队列
    public static final String ORDER_TACK="order_tack";

    //声明队列
    @Bean
    public Queue queue(){
        return new Queue(ORDER_TACK);
    }
}
