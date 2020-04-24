package com.changgou.web.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * @author cxl
 * @date 2020-04-07 14:09
 */
@SpringBootApplication
@EnableEurekaClient
public class WebGateWayApplication {
    public static void main(String[] args) {
        SpringApplication.run(WebGateWayApplication.class,args);
    }
}
