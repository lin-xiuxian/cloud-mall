package com.lxx.cloud.mall.cartorder.service.impl;

import com.lxx.cloud.mall.cartorder.feign.ProductFeignClient;
import com.lxx.cloud.mall.cartorder.model.dao.CartMapper;
import com.lxx.cloud.mall.cartorder.model.pojo.Cart;
import com.lxx.cloud.mall.cartorder.model.vo.CartVO;
import com.lxx.cloud.mall.cartorder.service.CartService;
import com.lxx.cloud.mall.categoryproduct.model.pojo.Product;
import com.lxx.cloud.mall.common.Constant;
import com.lxx.cloud.mall.exception.LxxMallException;
import com.lxx.cloud.mall.exception.LxxMallExceptionEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 林修贤
 * @date 2023/2/17
 * @description 购物车 Service 实现类
 */
@Service
public class CartServiceImpl implements CartService {
    @Autowired
    ProductFeignClient productFeignClient;

    @Autowired
    CartMapper cartMapper;

    @Override
    public List<CartVO> list(Integer userId){
        List<CartVO> cartVOS = cartMapper.selectList(userId);
        for (int i = 0; i < cartVOS.size(); i++) {
            CartVO cartVO = cartVOS.get(i);
            cartVO.setTotalPrice(cartVO.getPrice() * cartVO.getQuantity());
        }
        return cartVOS;
    }

    @Override
    public List<CartVO> add(Integer userId, Integer productId, Integer count){
        validProduct(productId, count);
        Cart cart = cartMapper.selectCartByUserIdAndProductId(userId, productId);
        if(cart == null) {
            //商品之前不在购物车里, 需要新增一个记录
            cart = new Cart();
            cart.setProductId(productId);
            cart.setUserId(userId);
            cart.setQuantity(count);
            cart.setSelected(Constant.Cart.CHECKED);
            cartMapper.insertSelective(cart);
        } else {
            //商品已存在购物车里则数量增加
            count = cart.getQuantity() + count;
            Cart cartNew = new Cart();
            cartNew.setQuantity(count);
            cartNew.setId(cart.getId());
            cartNew.setProductId(cart.getProductId());
            cartNew.setUserId(cart.getUserId());
            cartNew.setSelected(Constant.Cart.CHECKED);
            cartMapper.updateByPrimaryKeySelective(cartNew);
        }
        return this.list(userId);
    }

    private void validProduct(Integer productId, Integer count) {
        Product product = productFeignClient.detailForFeign(productId);
        //判断商品是否存在,是否上架
        if (product == null || product.getStatus().equals(Constant.SaleStatus.NOT_SALE)){
            throw new LxxMallException(LxxMallExceptionEnum.NOT_SALE);
        }
        //判断商品库存
        if (count > product.getStock()){
            throw new LxxMallException(LxxMallExceptionEnum.NOT_ENOUGH);
        }
    }

    @Override
    public List<CartVO> update(Integer userId, Integer productId, Integer count){
        validProduct(productId, count);
        Cart cart = cartMapper.selectCartByUserIdAndProductId(userId, productId);
        if(cart == null) {
            //商品之前不在购物车里, 无法更新, 只能更新已有商品
             throw new LxxMallException(LxxMallExceptionEnum.UPDATE_FAILED);
        } else {
            //商品已存在购物车里则 更新数量
            Cart cartNew = new Cart();
            cartNew.setQuantity(count);
            cartNew.setId(cart.getId());
            cartNew.setProductId(cart.getProductId());
            cartNew.setUserId(cart.getUserId());
            cartNew.setSelected(Constant.Cart.CHECKED);
            cartMapper.updateByPrimaryKeySelective(cartNew);
        }
        return this.list(userId);
    }

    @Override
    public List<CartVO> delete(Integer userId, Integer productId){
        Cart cart = cartMapper.selectCartByUserIdAndProductId(userId, productId);
        if(cart == null) {
            //商品之前不在购物车里, 无法删除
            throw new LxxMallException(LxxMallExceptionEnum.DELETE_FAILED);
        } else {
            //商品已存在购物车里则 可以删除
            cartMapper.deleteByPrimaryKey(cart.getId());
        }
        return this.list(userId);
    }

    @Override
    public List<CartVO> selectOrNot(Integer userId, Integer productId, Integer selected){
        Cart cart = cartMapper.selectCartByUserIdAndProductId(userId, productId);
        if(cart == null) {
            //商品之前不在购物车里, 无法选择
            throw new LxxMallException(LxxMallExceptionEnum.UPDATE_FAILED);
        } else {
            //商品已存在购物车里则 可以更新
            cartMapper.selectOrNot(userId, productId, selected);
        }
        return this.list(userId);
    }

    @Override
    public List<CartVO> selectAllOrNot(Integer userId, Integer selected){
        //改变选中状态
        cartMapper.selectOrNot(userId, null, selected);
        return this.list(userId);
    }
}
