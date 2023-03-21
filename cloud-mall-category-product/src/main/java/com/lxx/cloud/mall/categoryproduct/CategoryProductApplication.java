package com.lxx.cloud.mall.categoryproduct;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

/**
 * @author 林修贤
 * @date 2023/3/5
 * @description CategoryProduct 启动类
 */
@SpringBootApplication
@EnableRedisHttpSession
@EnableFeignClients
@MapperScan("com.lxx.cloud.mall.categoryproduct.model.dao")
@ComponentScan({"com.lxx.cloud.mall.categoryproduct", "com.lxx.cloud.mall.common"})
public class CategoryProductApplication {
    public static void main(String[] args) {
        SpringApplication.run(CategoryProductApplication.class);
    }
}
