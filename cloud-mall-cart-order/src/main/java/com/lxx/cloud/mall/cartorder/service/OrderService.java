package com.lxx.cloud.mall.cartorder.service;

import com.github.pagehelper.PageInfo;
import com.lxx.cloud.mall.cartorder.model.request.CreateOrderReq;
import com.lxx.cloud.mall.cartorder.model.vo.OrderVO;

/**
 * @author 林修贤
 * @date 2023/2/18
 * @description 订单接口
 */
public interface OrderService {
    String create(CreateOrderReq createOrderReq);

    OrderVO detail(String orderNo);

    PageInfo listForCustomer(Integer pageNum, Integer pageSize);

    void cancel(String orderNo);

    String qrcode(String orderNo);

    void pay(String orderNo);

    PageInfo listForAdmin(Integer pageNum, Integer pageSize);

    void deliver(String orderNo);

    void finish(String orderNo);
}
