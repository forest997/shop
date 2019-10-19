package com.wuyou.shop_sso_neww;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication(scanBasePackages = "com.wuyou")
@EnableEurekaClient
@MapperScan(basePackages = "com.wuyou.dao")
public class ShopSsoNewwApplication {

    public static void main(String[] args) {
        SpringApplication.run(ShopSsoNewwApplication.class, args);
    }

}
