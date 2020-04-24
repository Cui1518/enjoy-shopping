package com.changgou.seckill.web.controller;

import com.changgou.entity.Result;
import com.changgou.seckill.feign.SecKillOrderFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author cxl
 * @date 2020-04-19 17:46
 */
@RestController
@RequestMapping("/wseckillorder")
public class SecKillOrderController {
    @Autowired
    private SecKillOrderFeign secKillOrderFeign;

    @RequestMapping("/add")
    public Result add(@RequestParam("time") String time, @RequestParam("id") Long id){
        Result result = secKillOrderFeign.add(time, id);
        return result;
    }

}
