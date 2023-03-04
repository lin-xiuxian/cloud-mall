package com.lxx.could.mall.zuul.feign;

import com.lxx.cloud.mall.user.model.pojo.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @author 林修贤
 * @date 2023/3/4
 * @description UserFeignClient
 */
@FeignClient(value = "cloud-mall-user")
public interface UserFeignClient {

    @PostMapping("/checkAdminRole")
    public Boolean checkAdminRole(@RequestBody User user);
}
