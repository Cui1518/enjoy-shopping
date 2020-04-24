package com.changgou.order.controller;

import com.changgou.entity.Result;
import com.changgou.entity.StatusCode;
import com.changgou.order.config.TokenDecode;
import com.changgou.order.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @author cxl
 * @date 2020-04-07 20:56
 */

@RestController
@RequestMapping("/cart")
public class CartController {

    @Autowired
    private CartService cartService;
    @Autowired
    private TokenDecode tokenDecode;

    //添加
    @RequestMapping("/addCart")
    public Result addCart(@RequestParam("skuId") String skuId, @RequestParam("num") Integer num){
        //动态获取登录人信息
//        String username="itcast";
        String username = tokenDecode.getUserInfo().get("username");
        cartService.addCart(skuId,num,username);
        return new Result(true, StatusCode.OK,"加入购物车成功");

    }
    //查询
    @GetMapping("/list")
    public Map list(){
        //动态获取登录人信息
//        String username="itcast";
        String username = tokenDecode.getUserInfo().get("username");
        //在获取购物车数据前刷新购物车数据
        cartService.refreshCart(username);
        Map map = cartService.list(username);
        return map;

    }
}
