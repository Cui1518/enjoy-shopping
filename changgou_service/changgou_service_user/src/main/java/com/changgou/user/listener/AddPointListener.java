package com.changgou.user.listener;

import com.alibaba.fastjson.JSON;
import com.changgou.order.pojo.Task;
import com.changgou.user.config.RabbitMQConfig;
import com.changgou.user.service.UserService;
import org.apache.commons.lang.StringUtils;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

/**
 * @author cxl
 * @date 2020-04-13 19:04
 */
@Component
public class AddPointListener {
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private UserService userService;
    @Autowired
    private RabbitTemplate rabbitTemplate;

    @RabbitListener(queues = RabbitMQConfig.CG_BUYING_ADDPOINT)
    public void receiveMessage(String message){
        System.out.println("客户服务接收到了任务信息");

        //转换消息
        Task task = JSON.parseObject(message, Task.class);
        if (task==null|| StringUtils.isEmpty(task.getRequestBody())){
            return;
        }
        //判断redis中当前任务是否存在
        Object value = redisTemplate.boundValueOps(task.getId()).get();
        if (value!= null){
            return;
        }
        //更新用户积分
        int result = userService.updateUserPoint(task);
        if (result==0){
            return;
        }

        //向订单服务返回通知消息
        rabbitTemplate.convertAndSend(RabbitMQConfig.EX_BUYING_ADDPOINTUSER,RabbitMQConfig.CG_BUYING_FINISHADDPOINT_KEY,JSON.toJSONString(task));
    }
}
