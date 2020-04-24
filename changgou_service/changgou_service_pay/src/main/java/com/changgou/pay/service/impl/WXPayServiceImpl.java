package com.changgou.pay.service.impl;

import com.changgou.pay.service.WXPayService;
import com.github.wxpay.sdk.WXPay;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 * @author cxl
 * @date 2020-04-16 14:29
 */
@Service
public class WXPayServiceImpl implements WXPayService {

    @Autowired
    private WXPay wxPay;

    @Value(("${wxpay.notify_url}"))
    private String notify_url;

    @Override
    public Map nativePay(String orderId, Integer money) {
        try {
            //封装请求参数
            Map<String,String> map = new HashMap<>();
            map.put("body","畅购");//商品描述
            map.put("out_trade_no",orderId);//订单号
            BigDecimal payMoney = new BigDecimal("0.01");
            BigDecimal fen = payMoney.multiply(new BigDecimal("100")); //得到1.00分
            //0 保留小数点后0位  BigDecimal.ROUND_UP:向上取整
            fen= fen.setScale(0,BigDecimal.ROUND_UP);//得到1分
            map.put("total_fee",String.valueOf(fen));
            map.put("spbill_create_ip","127.0.0.1");
            map.put("notify_url",notify_url);
            map.put("trade_type","NATIVE");//交易类型

            //基于wxpay完成统一下单接口的调用,并获取返回结果
            Map<String, String> result = wxPay.unifiedOrder(map);
            return result;

        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Map queryOrder(String orderId) {

        try {
            Map<String,String> map=new HashMap();
            map.put("out_trade_no",orderId);
            Map<String, String> resultMap = wxPay.orderQuery(map);
            return resultMap;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }

    }


    /**
     * 基于微信关闭订单
     * @param orderId
     * @return
     */
    @Override
    public Map closeOrder(String orderId) {

        try {
            Map<String,String> map=new HashMap<>();
            map.put("out_trade_no",orderId);
            Map<String, String> resultMap = wxPay.closeOrder(map);
            return resultMap;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }

    }
}
