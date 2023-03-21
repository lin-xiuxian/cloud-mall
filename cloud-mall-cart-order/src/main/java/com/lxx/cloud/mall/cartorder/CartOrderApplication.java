package com.lxx.cloud.mall.cartorder;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

/**
 * @author 林修贤
 * @date 2023/3/8
 * @description 购物车订单启动类
 */
@SpringBootApplication
@MapperScan(basePackages = "com.lxx.cloud.mall.cartorder.model.dao")
@EnableFeignClients
@EnableRedisHttpSession
@ComponentScan({"com.lxx.cloud.mall.cartorder", "com.lxx.cloud.mall.common"})
public class CartOrderApplication {
    public static void main(String[] args) {
        SpringApplication.run(CartOrderApplication.class);
    }
}
