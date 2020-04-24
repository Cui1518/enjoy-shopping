package com.changgou.web.gateway.filter;

import com.changgou.web.gateway.service.AuthService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * @author cxl
 * @date 2020-04-07 14:26
 */
@Component //声明是一个Bean
public class AuthFilter implements GlobalFilter, Ordered {

    private static final String LOGIN_URL="http://localhost:8001/api/oauth/toLogin";

    @Autowired
    private AuthService authService;
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        ServerHttpResponse response = exchange.getResponse();
        //1.判断当前请求是否为登录请求,如果是则直接放行
        String path = request.getURI().getPath();
        if ("/api/oauth/login".equals(path) || !UrlFilter.hasAuthorize(path)){
            //直接放行
            return chain.filter(exchange);
        }

        //2.如果不是登录请求,则从cookie中获取jti值.如果该值不存在,则拒绝本次访问
        String jti = authService.getJtiFromCookie(request);
        if (StringUtils.isEmpty(jti)){
            //拒绝访问
//            response.setStatusCode(HttpStatus.UNAUTHORIZED);
//            return response.setComplete();
              //跳转到登录页面
            return this.toLoginPage(LOGIN_URL+"?FROM="+request.getURI().getPath(),exchange);

        }

        //3.如果jti存在,则从redis中获取jwt值,如果该值不存在,则直接拒绝

        String jwt = authService.getJwtFromRedis(jti);
        if (StringUtils.isEmpty(jwt)){
            //拒绝访问
//            response.setStatusCode(HttpStatus.UNAUTHORIZED);
//            return response.setComplete();
            return this.toLoginPage(LOGIN_URL,exchange);
        }

        //4.jwt存在,对当前的请求进行中增强让他会携带令牌的信息
        request.mutate().header("Authorization","Bearer "+jwt);
        return chain.filter(exchange);
    }

    private Mono<Void> toLoginPage(String loginUrl, ServerWebExchange exchange) {
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(HttpStatus.SEE_OTHER);
        response.getHeaders().set("Location",loginUrl);
        return response.setComplete();
    }

    @Override
    public int getOrder() {
        return 0;
    }
}
