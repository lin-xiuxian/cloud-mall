package com.lxx.cloud.mall.cartorder.service.impl;

import com.lxx.cloud.mall.cartorder.service.OrderService;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author 林修贤
 * @date 2023/3/13
 * @description
 */
public class OrderServiceImplTest {
    @Test
    public void detail() {
        OrderServiceImpl orderService = new OrderServiceImpl();
        orderService.detail("2023031321140420827");
    }
}