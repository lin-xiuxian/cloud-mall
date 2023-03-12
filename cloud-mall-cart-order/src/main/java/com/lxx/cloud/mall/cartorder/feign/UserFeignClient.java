package com.lxx.cloud.mall.cartorder.feign;

import com.lxx.cloud.mall.user.model.pojo.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author 林修贤
 * @date 2023/3/12
 * @description User 的feign 客户端
 */
@FeignClient(value = "cloud-mall-user")
public interface UserFeignClient {
    /**
     * 获取当前登录的 User 对象
     * @return
     */
    @GetMapping("/getUser")
    User getUser();

    @GetMapping("/checkAdminRoleForFeign")
    Boolean checkAdminRole(User user);
}
