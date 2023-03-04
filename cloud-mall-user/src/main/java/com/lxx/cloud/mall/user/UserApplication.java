package com.lxx.cloud.mall.user;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author 林修贤
 * @date 2023/3/4
 * @description 启动类
 */
@EnableRedisHttpSession
@SpringBootApplication
@EnableSwagger2
@MapperScan(basePackages = "com.lxx.cloud.mall.user.model.dao")
public class UserApplication {
    public static void main(String[] args) {
        SpringApplication.run(UserApplication.class, args);
    }
}
