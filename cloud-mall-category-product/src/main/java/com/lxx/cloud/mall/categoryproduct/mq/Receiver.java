package com.lxx.cloud.mall.categoryproduct.mq;

import com.lxx.cloud.mall.categoryproduct.service.ProductService;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author 林修贤
 * @date 2023/3/16
 * @description 接收者
 */
@Component
@RabbitListener(queues = "queue1")
public class Receiver {

    @Autowired
    ProductService productService;

    @RabbitHandler
    public void process(String message){
        System.out.println("收到了" + message);
        String[] split = message.split(",");
        productService.updateStock(Integer.valueOf(split[0]), Integer.valueOf(split[1]));
    }
}
