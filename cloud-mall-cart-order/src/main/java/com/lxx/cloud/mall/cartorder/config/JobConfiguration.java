package com.lxx.cloud.mall.cartorder.config;


import com.lxx.cloud.mall.cartorder.model.pojo.Order;
import com.lxx.cloud.mall.cartorder.service.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author 林修贤
 * @date 2023/3/15
 * @description 定时任务类
 */
@Component
public class JobConfiguration {
    @Autowired
    OrderService orderService;

    private final Logger log = LoggerFactory.getLogger(JobConfiguration.class);

//    @Scheduled(fixedRate = 3000)
//    public void process() throws InterruptedException {
//        log.info("start....");
//        Thread.sleep(2000);
//        log.info("stop.....");
//
//    }

//    @Scheduled(cron = "0/3 * * * * ?")
//    public void process() throws InterruptedException {
//        log.info("start....");
//        Thread.sleep(2000);
//        log.info("stop.....");
//
//    }

    @Scheduled(cron = "0 0/5 * * * ?")
    public void cancelUnpaidOrder(){
        List<Order> unpaidOrders = orderService.getUnpaidOrder();
        for (int i = 0; i < unpaidOrders.size(); i++) {
            Order order = unpaidOrders.get(i);
            orderService.cancel(order.getOrderNo(), true);
        }
    }
}
