package com.changgou.order.listener;

import com.alibaba.fastjson.JSON;
import com.changgou.order.config.RabbitMQConfig;
import com.changgou.order.pojo.Task;
import com.changgou.order.service.TaskService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author cxl
 * @date 2020-04-13 20:31
 */
@Component
public class DelTaskListener {
    @Autowired
    private TaskService taskService;
    @RabbitListener(queues = RabbitMQConfig.CG_BUYING_FINISHADDPOINT)
    public void receiveDelTaskMessage(String message){
        Task task = JSON.parseObject(message, Task.class);
        //删除任务表中数据,向历史任务表中添加记录
        taskService.delTask(task);
    }
}
