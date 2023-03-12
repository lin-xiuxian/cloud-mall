package com.lxx.cloud.mall.user.controller;



import com.lxx.cloud.mall.common.ApiRestResponse;
import com.lxx.cloud.mall.common.Constant;
import com.lxx.cloud.mall.exception.LxxMallException;

import com.lxx.cloud.mall.exception.LxxMallExceptionEnum;

import com.lxx.cloud.mall.user.model.pojo.User;

import com.lxx.cloud.mall.user.service.UserService;
import com.mysql.cj.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

/**
 * @author 林修贤
 * @date 2023/2/11
 * @description 用户控制器
 */
@Controller
public class UserController {

    @Autowired
    UserService userService;

    /**
     * 注册
     * @param userName
     * @param password
     * @return
     * @throws LxxMallException
     */
    @PostMapping("/register")
    @ResponseBody
    public ApiRestResponse register(@RequestParam("userName") String userName, @RequestParam("password") String password) throws LxxMallException {
        if (StringUtils.isNullOrEmpty(userName)) {
            return ApiRestResponse.error(LxxMallExceptionEnum.NEED_USER_NAME);
        }
        if (StringUtils.isNullOrEmpty(password)) {
            return ApiRestResponse.error(LxxMallExceptionEnum.NEED_PASSWORD);
        }
        //密码长度不能小于8位
        if (password.length() < 8) {
            return ApiRestResponse.error(LxxMallExceptionEnum.PASSWORD_TOO_SHORT);
        }
        userService.register(userName, password);
        return ApiRestResponse.success();
    }

    /**
     * 用户登录
     * @param userName
     * @param password
     * @param session
     * @return
     * @throws LxxMallException
     */
    @GetMapping("/login")
    @ResponseBody
    public ApiRestResponse login(@RequestParam("userName") String userName, @RequestParam("password") String password, HttpSession session) throws LxxMallException {
        if (StringUtils.isNullOrEmpty(userName)) {
            return ApiRestResponse.error(LxxMallExceptionEnum.NEED_USER_NAME);
        }
        if (StringUtils.isNullOrEmpty(password)) {
            return ApiRestResponse.error(LxxMallExceptionEnum.NEED_PASSWORD);
        }
        User user = userService.login(userName, password);
        //保存用户信息时不保存密码
        user.setPassword(null);
        session.setAttribute(Constant.LXX_MALL_USER, user);
        return ApiRestResponse.success(user);
    }

    /**
     * 更新个性签名
     * @param session
     * @param signature
     * @return
     * @throws LxxMallException
     */
    @PostMapping("/user/update")
    @ResponseBody
    public ApiRestResponse updateUserInfo(HttpSession session, @RequestParam String signature) throws LxxMallException {
        User currentUser = (User) session.getAttribute(Constant.LXX_MALL_USER);
        if (currentUser == null) {
            return ApiRestResponse.error(LxxMallExceptionEnum.NEED_LOGIN);
        }
        User user = new User();
        user.setId(currentUser.getId());
        user.setPersonalizedSignature(signature);
        userService.updateInformation(user);
        return ApiRestResponse.success();
    }

    /**
     * 登出，删除session
     * @param session
     * @return
     */
    @PostMapping("/user/logout")
    @ResponseBody
    public ApiRestResponse logout(HttpSession session){
        session.removeAttribute(Constant.LXX_MALL_USER);
        return ApiRestResponse.success();
    }

    /**
     * 管理员登录接口
     * @param userName
     * @param password
     * @param session
     * @return
     * @throws LxxMallException
     */
    @GetMapping("/adminLogin")
    @ResponseBody
    public ApiRestResponse adminLogin(@RequestParam("userName") String userName, @RequestParam("password") String password, HttpSession session) throws LxxMallException {
        if (StringUtils.isNullOrEmpty(userName)) {
            return ApiRestResponse.error(LxxMallExceptionEnum.NEED_USER_NAME);
        }
        if (StringUtils.isNullOrEmpty(password)) {
            return ApiRestResponse.error(LxxMallExceptionEnum.NEED_PASSWORD);
        }
        User user = userService.login(userName, password);
        //校验是否是管理员
        if (userService.checkAdminRole(user)) {
            //是管理员，执行操作
            //保存用户信息时不保存密码
            user.setPassword(null);
            session.setAttribute(Constant.LXX_MALL_USER, user);
            return ApiRestResponse.success(user);
        } else {
            return ApiRestResponse.error(LxxMallExceptionEnum.NEED_ADMIN);
        }
    }

    /**
     * 校验是否是管理员
     * @param user
     * @return
     */
    @PostMapping("/checkAdminRole")
    @ResponseBody
    public Boolean checkAdminRole(@RequestBody User user){
        return userService.checkAdminRole(user);
    }


    /**
     * 获取当前用户的 User 对象
     * @param session
     * @return
     */
    @GetMapping("/getUser")
    @ResponseBody
    public User getUser(HttpSession session){
        User currentUser = (User) session.getAttribute(Constant.LXX_MALL_USER);
        return currentUser;
    }

    @PostMapping("/checkAdminRoleForFeign")
    public Boolean checkAdminRoleForFeign(User user){
        return userService.checkAdminRole(user);
    }
}