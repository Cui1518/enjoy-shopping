package com.changgou.web.gateway.filter;

/**
 * @author cxl
 * @date 2020-04-07 16:32
 */

public class UrlFilter {
    //所有需要传递令牌的地址
    public static String filterPath="/api/wseckillorder,/api/seckill,/api/wxpay,/api/wxpay/**,/api/worder/**,/api/user/**,/api/address/**,/api/wcart/**,/api/cart/**,/api/categoryReport/**,/api/orderConfig/**,/api/order/**,/api/orderItem/**,/api/orderLog/**,/api/preferential/**,/api/returnCause/**,/api/returnOrder/**,/api/returnOrderItem/**";
    public static boolean hasAuthorize(String url){
        String[] split = filterPath.replace("**", "").split(",");
        for (String value : split) {
            if (url.startsWith(value)){
                return true; //当前的访问地址是需要传递令牌的
            }
        }
        return false;  //当前的访问地址是不需要传递令牌的
    }
}
