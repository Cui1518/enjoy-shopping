package com.changgou.seckill.config;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author cxl
 * @date 2020-04-20 17:43
 */
@Configuration
public class RabbitMQConfig {
    public static final String SECKILL_ORDER_QUEUE="seckill_order";

    @Bean
    public Queue queue(){
        return new Queue(SECKILL_ORDER_QUEUE,true);
    }
}
