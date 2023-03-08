package com.lxx.cloud.mall.cartorder.service;

import com.lxx.cloud.mall.cartorder.model.vo.CartVO;

import java.util.List;

/**
 * @author 林修贤
 * @date 2023/2/17
 * @description 购物车service 类
 */
public interface CartService {
    List<CartVO> list(Integer userId);

    List<CartVO> add(Integer userId, Integer productId, Integer count);

    List<CartVO> update(Integer userId, Integer productId, Integer count);

    List<CartVO> delete(Integer userId, Integer productId);

    List<CartVO> selectOrNot(Integer userId, Integer productId, Integer selected);

    List<CartVO> selectAllOrNot(Integer userId, Integer selected);
}
