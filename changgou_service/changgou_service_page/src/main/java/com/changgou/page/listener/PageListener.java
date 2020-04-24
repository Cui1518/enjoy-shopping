package com.changgou.page.listener;

import com.changgou.page.config.RabbitMQConfig;
import com.changgou.page.service.PageService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author cxl
 * @date 2020-04-04 19:48
 */
@Component
public class PageListener {

    @Autowired
    private PageService pageService;
    @RabbitListener(queues = RabbitMQConfig.PAGE_CREATE_QUEUE)
    public void receiveMessage(String spuId){
        System.out.println("获取静态化页面的商品id,id的值为: "+spuId);

        //调用业务层完成静态化页面
        pageService.generateHtml(spuId);
    }
}
