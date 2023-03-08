package com.lxx.cloud.mall.cartorder.feign;

import com.lxx.cloud.mall.categoryproduct.model.pojo.Product;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author 林修贤
 * @date 2023/3/8
 * @description 商品 FeignClient
 */
@FeignClient(value = "cloud-mall-category-product")
public interface ProductFeignClient {

    @GetMapping("/product/detailForFeign")
    Product detailForFeign(@RequestParam Integer id);
}
