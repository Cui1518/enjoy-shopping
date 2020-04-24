package com.changgou.pay.service;

import java.util.Map;

public interface WXPayService {

    /**
     * 同意下单
     * @param orderId
     * @param money
     * @return
     */
    Map nativePay(String orderId,Integer money);

    /**
     * 查询订单
     * @param orderId
     * @return
     */
    Map queryOrder(String orderId);

    /**
     * 基于微信关闭订单
     */
    Map closeOrder(String orderId);
}
