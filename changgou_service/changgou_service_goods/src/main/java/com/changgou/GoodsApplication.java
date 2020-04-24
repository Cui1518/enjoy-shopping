package com.changgou;

import com.changgou.util.IdWorker;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
import tk.mybatis.spring.annotation.MapperScan;

@SpringBootApplication
@EnableEurekaClient
@MapperScan(basePackages = {"com.changgou.goods.dao"})
@EnableSwagger2 //开启swagger2
public class GoodsApplication {

    @Value("${workId}")
    private int workId;
    @Value("${datacenterId}")
    private int datacenterId;

    public static void main(String[] args) {
        SpringApplication.run( GoodsApplication.class);
    }

    @Bean
    public IdWorker idWorker(){
        return new IdWorker(workId,datacenterId);
    }

    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.changgou"))
                .paths(PathSelectors.any())
                .build();
    } private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("畅购商城API文档")
                .description("畅购商城API文档")
// .termsOfServiceUrl("/")
                .version("1.0")
                .build();
    }

}
