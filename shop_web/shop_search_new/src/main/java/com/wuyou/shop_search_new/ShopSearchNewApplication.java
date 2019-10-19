package com.wuyou.shop_search_new;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication(scanBasePackages = "com.wuyou")
@EnableEurekaClient
public class ShopSearchNewApplication {

    public static void main(String[] args) {
        SpringApplication.run(ShopSearchNewApplication.class, args);
    }

}
