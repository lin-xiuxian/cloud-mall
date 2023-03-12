package com.lxx.cloud.mall.cartorder.controller;


import com.lxx.cloud.mall.cartorder.feign.UserFeignClient;
import com.lxx.cloud.mall.cartorder.model.vo.CartVO;
import com.lxx.cloud.mall.cartorder.service.CartService;
import com.lxx.cloud.mall.common.ApiRestResponse;
import com.lxx.cloud.mall.user.model.pojo.User;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author 林修贤
 * @date 2023/2/17
 * @description 购物车 Controller
 */
@RestController
@RequestMapping("/cart")
public class CartController {
    @Autowired
    CartService cartService;

    @Autowired
    UserFeignClient userFeignClient;

    @GetMapping("/list")
    @ApiOperation("购物车列表")
    public ApiRestResponse list(){
        //内部获取用户 ID， 防止横向越权
        List<CartVO> cartVOList = cartService.list(userFeignClient.getUser().getId());
        return ApiRestResponse.success(cartVOList);
    }

    @PostMapping("/add")
    @ApiOperation("添加商品到购物车")
    public ApiRestResponse add(@RequestParam Integer productId, @RequestParam Integer count){
        User user = userFeignClient.getUser();
        List<CartVO> cartVOList = cartService.add(user.getId(), productId, count);
        return ApiRestResponse.success(cartVOList);
    }

    @PostMapping("/update")
    @ApiOperation("更新购物车")
    public ApiRestResponse update(@RequestParam Integer productId, @RequestParam Integer count){
        User user = userFeignClient.getUser();
        List<CartVO> cartVOList = cartService.update(user.getId(), productId, count);
        return ApiRestResponse.success(cartVOList);
    }

    @PostMapping("/delete")
    @ApiOperation("删除购物车")
    public ApiRestResponse delete(@RequestParam Integer productId){
        //不能传入 userId ， cartId 否则可以删除别人购物车
        User user = userFeignClient.getUser();
        List<CartVO> cartVOList = cartService.delete(user.getId(), productId);
        return ApiRestResponse.success(cartVOList);
    }

    @PostMapping("/select")
    @ApiOperation("选择/不选择购物车的商品")
    public ApiRestResponse select(@RequestParam Integer productId, @RequestParam Integer selected){
        //不能传入 userId ， cartId 否则可以删除别人购物车
        User user = userFeignClient.getUser();
        List<CartVO> cartVOList = cartService.selectOrNot(user.getId(), productId, selected);
        return ApiRestResponse.success(cartVOList);
    }

    @PostMapping("/selectAll")
    @ApiOperation("全选/全不选购物车的商品")
    public ApiRestResponse selectAll(@RequestParam Integer selected){
        //不能传入 userId ， cartId 否则可以删除别人购物车
        User user = userFeignClient.getUser();
        List<CartVO> cartVOList = cartService.selectAllOrNot(user.getId(), selected);
        return ApiRestResponse.success(cartVOList);
    }

}
