package com.lxx.cloud.mall.cartorder.mq;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author 林修贤
 * @date 2023/3/16
 * @description 发送消息
 */
@Component
public class MsgSender {
    @Autowired
    private AmqpTemplate rabbitmqTemplate;

    public void send(Integer productId, Integer stock){
        rabbitmqTemplate.convertAndSend("cloudExchange", "productStock", productId + "," + stock);
    }
}
