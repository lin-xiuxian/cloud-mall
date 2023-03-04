package com.lxx.cloud.mall.user.service;


import com.lxx.cloud.mall.exception.LxxMallException;
import com.lxx.cloud.mall.user.model.pojo.User;


/**
 * @author 林修贤
 * @date 2023/2/11
 * @description UserService
 */
public interface UserService {

    void register(String userName, String password) throws LxxMallException;

    User login(String userName, String password) throws LxxMallException;

    void updateInformation(User user) throws LxxMallException;

    boolean checkAdminRole(User user);
}
