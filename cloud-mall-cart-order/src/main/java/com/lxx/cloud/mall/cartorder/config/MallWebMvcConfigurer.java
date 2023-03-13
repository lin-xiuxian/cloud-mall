package com.lxx.cloud.mall.cartorder.config;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


/**
 * @author 林修贤
 * @date 2023/2/12
 * @description
 */
@Configuration
public class MallWebMvcConfigurer implements WebMvcConfigurer {
    @Value("${file.upload.dir}")
    String FILE_UPLOAD_DIR;
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/images/**").addResourceLocations("file:" + FILE_UPLOAD_DIR);
    }
}
