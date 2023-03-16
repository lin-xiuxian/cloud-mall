package com.lxx.cloud.mall.cartorder.utils;

import java.util.concurrent.ArrayBlockingQueue;

/**
 * @author 林修贤
 * @date 2023/3/16
 * @description
 */
public class MyBlockingQueue {

    public static void main(String[] args) {
        ArrayBlockingQueue<Object> queue = new ArrayBlockingQueue<>(10);
        new Thread(() -> {
            try {
                while(true){
                    queue.put(new Object());
                    System.out.println("生产者已放置");
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();

        new Thread(() -> {
            try {
                while(true){
                    queue.take();
                    System.out.println("消费者已消费");
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }
}
