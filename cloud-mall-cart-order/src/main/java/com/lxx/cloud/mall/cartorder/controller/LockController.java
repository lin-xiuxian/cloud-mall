package com.lxx.cloud.mall.cartorder.controller;

import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 林修贤
 * @date 2023/3/19
 * @description
 */
@RestController
public class LockController {
    @Autowired
    RedissonClient redissonClient;

    @GetMapping("/redissonLock")
    public void redissonLock(){
        RLock redissonLock = redissonClient.getLock("redissonLock");
        boolean b = redissonLock.tryLock();
        if (b){
            try {
                System.out.println("redisson锁 + 1");
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("redisson锁未获取到");
        }
    }
}
