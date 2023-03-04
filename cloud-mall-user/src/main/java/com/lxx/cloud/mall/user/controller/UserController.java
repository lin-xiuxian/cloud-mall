package com.lxx.cloud.mall.user.controller;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.lxx.mall.common.ApiRestResponse;
import com.lxx.mall.common.Constant;
import com.lxx.mall.exception.LxxMallException;
import com.lxx.mall.exception.LxxMallExceptionEnum;
import com.lxx.mall.model.pojo.User;
import com.lxx.mall.service.EmailService;
import com.lxx.mall.service.UserService;
import com.lxx.mall.util.EmailUtil;
import com.mysql.cj.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.Date;

/**
 * @author 林修贤
 * @date 2023/2/11
 * @description 用户控制器
 */
@Controller
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    EmailService emailService;
    @GetMapping("/test")
    @ResponseBody
    public User personalPage() {
        return userService.getUser();
    }

    /**
     * 注册
     * @param userName
     * @param password
     * @return
     * @throws LxxMallException
     */
    @PostMapping("/register")
    @ResponseBody
    public ApiRestResponse register(@RequestParam("userName") String userName, @RequestParam("password") String password, @RequestParam("emailAddress") String emailAddress, @RequestParam("verificationCode") String verificationCode) throws LxxMallException {
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
        if (StringUtils.isNullOrEmpty(emailAddress)) {
            return ApiRestResponse.error(LxxMallExceptionEnum.NEED_EMAIL_ADDRESS);
        }
        if (StringUtils.isNullOrEmpty(verificationCode)) {
            return ApiRestResponse.error(LxxMallExceptionEnum.NEED_VERIFICATION_CODE);
        }
        //检查邮件地址是否注册
        boolean emailPass = userService.checkEmailRegistered(emailAddress);
        if (!emailPass) {
            return ApiRestResponse.error(LxxMallExceptionEnum.EMAIL_ALREADY_BEEN_REGISTERED);
        }
        Boolean passEmailAndCode = emailService.checkEmailAndCode(emailAddress, verificationCode);
        if(!passEmailAndCode){
            return ApiRestResponse.error(LxxMallExceptionEnum.WRONG_VERIFICATION_CODE);
        }
        userService.register(userName, password, emailAddress);
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

    @PostMapping("/user/sendEmail")
    @ResponseBody
    public ApiRestResponse sendEmail(@RequestParam("emailAddress") String emailAddress){
        //检查邮件地址是否有效， 检查是否注册
        boolean validEmailAddress = EmailUtil.isValidEmailAddress(emailAddress);
        if (validEmailAddress) {
            boolean emailPass = userService.checkEmailRegistered(emailAddress);
            if (!emailPass){
                return ApiRestResponse.error(LxxMallExceptionEnum.EMAIL_ALREADY_BEEN_REGISTERED);
            } else {
                String verificationCode = EmailUtil.genVerificationCode();
                boolean result = emailService.savaEmailToRedis(emailAddress, verificationCode);
                if (result){
                    emailService.sendSimpleMessage(emailAddress, Constant.EMAIL_SUBJECT, "欢迎注册，您的验证码是: " + verificationCode);
                    return ApiRestResponse.success();
                } else {
                    return ApiRestResponse.error(LxxMallExceptionEnum.EMAIL_ALREADY_BEEN_SEND);
                }
            }
        } else {
            return ApiRestResponse.error(LxxMallExceptionEnum.WRONG_EMAIL);
        }
    }

    @GetMapping("/loginWithJWT")
    @ResponseBody
    public ApiRestResponse loginWithJWT(@RequestParam("userName") String userName, @RequestParam("password") String password){
        if (StringUtils.isNullOrEmpty(userName)) {
            return ApiRestResponse.error(LxxMallExceptionEnum.NEED_USER_NAME);
        }
        if (StringUtils.isNullOrEmpty(password)) {
            return ApiRestResponse.error(LxxMallExceptionEnum.NEED_PASSWORD);
        }
        User user = userService.login(userName, password);
        //保存用户信息时不保存密码
        user.setPassword(null);
        Algorithm algorithm = Algorithm.HMAC256(Constant.JWT_KEY);
        String token = JWT.create()
                .withClaim(Constant.USER_NAME, user.getUsername())
                .withClaim(Constant.USER_ID, user.getId())
                .withClaim(Constant.USER_ROLE, user.getRole())
                .withExpiresAt(new Date(System.currentTimeMillis() + Constant.EXPIRE_TIME))
                .sign(algorithm);
        return ApiRestResponse.success(token);
    }
}