package com.changgou.order.service.impl;

import com.changgou.entity.Result;
import com.changgou.goods.feign.SkuFeign;
import com.changgou.goods.feign.SpuFeign;
import com.changgou.goods.pojo.Sku;
import com.changgou.goods.pojo.Spu;
import com.changgou.order.pojo.OrderItem;
import com.changgou.order.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author cxl
 * @date 2020-04-07 20:04
 */
@Service
public class CartServiceImpl implements CartService {
    private static final String CART="Cart_";
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private SkuFeign skuFeign;
    @Autowired
    private SpuFeign spuFeign;
    @Override
    public void addCart(String skuId, Integer num, String username) {
        //1.查询redis中相对应的商品信息
        OrderItem orderItem = (OrderItem) redisTemplate.boundHashOps(CART + username).get(skuId);
        //2.如果当前商品在redis中存在则更新商品的数量与价格
        if (orderItem!=null){
            if (Math.abs(num)==1){
                orderItem.setNum(orderItem.getNum()+num);
            }else {
                orderItem.setNum(num);
            }

            if (orderItem.getNum()<=0){
                //删除该商品
                redisTemplate.boundHashOps(CART+username).delete(skuId);
                return;
            }
            orderItem.setMoney(orderItem.getNum()*orderItem.getPrice());
            orderItem.setPayMoney(orderItem.getNum()*orderItem.getPrice());
        }else {
            //3.如果当前商品不存在,将商品添加到redis中
           Sku sku = skuFeign.findById(skuId).getData();
            Spu spu = spuFeign.findSpuById(sku.getSpuId()).getData();
            //封装orderItem
            orderItem=this.sku2OrderItem(sku,spu,num);

        }
         //4.将orderItem添加到redis中
        redisTemplate.boundHashOps(CART+username).put(skuId,orderItem);

    }
    //查询购物车列表数据
    @Override
    public Map list(String username) {
        HashMap map = new HashMap<>();
        List<OrderItem> orderItemList = redisTemplate.boundHashOps(CART + username).values();
        map.put("orderItemList",orderItemList);
        //商品的总数量与总价格
        Integer totalNum=0;
        Integer totalMoney=0;
        for (OrderItem orderItem : orderItemList) {
            totalNum+=orderItem.getNum();
            totalMoney+=orderItem.getMoney();
        }
        map.put("totalNum",totalNum);
        map.put("totalMoney",totalMoney);
        return map;
    }

    @Override
    public void refreshCart(String username) {
        //获得当前购物车
        List<OrderItem> orderItems = redisTemplate.boundHashOps(CART + username).values();
        //遍历所有购物车,刷新数据
        for (OrderItem orderItem : orderItems) {
            //根据SkuId查询商品信息
            Sku sku = skuFeign.findById(orderItem.getSkuId()).getData();
            //判断价格是否发生改变,对价格进行跟新
            if(sku.getPrice() != orderItem.getPrice()){
                //价格发生改变,跟新价格
                orderItem.setPrice(sku.getPrice());
                //更新支付金额
                orderItem.setPayMoney(sku.getPrice()*orderItem.getNum());
            }
            redisTemplate.boundHashOps(CART+username).put(sku.getId(),orderItem);
        }
    }


    private OrderItem sku2OrderItem(Sku sku, Spu spu, Integer num) {
        OrderItem orderItem = new OrderItem();
        orderItem.setSpuId(sku.getSpuId());
        orderItem.setSkuId(sku.getId());
        orderItem.setName(sku.getName());
        orderItem.setPrice(sku.getPrice());
        orderItem.setNum(num);
        orderItem.setMoney(orderItem.getPrice()*num);
        orderItem.setPayMoney(orderItem.getPrice()*num);
        orderItem.setImage(sku.getImage());
        orderItem.setWeight(sku.getWeight()*num);
        //分类信息
        orderItem.setCategoryId1(spu.getCategory1Id());
        orderItem.setCategoryId2(spu.getCategory2Id());
        orderItem.setCategoryId3(spu.getCategory3Id());
        return orderItem;
    }
}
