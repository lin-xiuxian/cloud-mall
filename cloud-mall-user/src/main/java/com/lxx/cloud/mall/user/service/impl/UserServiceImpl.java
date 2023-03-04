package com.lxx.cloud.mall.user.service.impl;


import com.lxx.cloud.mall.exception.LxxMallException;
import com.lxx.cloud.mall.exception.LxxMallExceptionEnum;

import com.lxx.cloud.mall.user.model.dao.UserMapper;
import com.lxx.cloud.mall.user.model.pojo.User;
import com.lxx.cloud.mall.user.service.UserService;
import com.lxx.cloud.mall.utils.MD5Utils;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;

/**
 * @author 林修贤
 * @date 2023/2/11
 * @description UserService 实现类
 */
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserMapper userMapper;

    @Override
    public void register(String userName, String password) throws LxxMallException {
        //查询是否重名
        User result = userMapper.selectByName(userName);
        if (result != null){
            throw new LxxMallException(LxxMallExceptionEnum.NAME_EXISTS);
        }

        //写到数据库
        User user = new User();
        user.setUsername(userName);
        try {
            user.setPassword(MD5Utils.getMD5String(password));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
//        user.setPassword(password);

        int count = userMapper.insertSelective(user);
        if (count == 0){
            throw new LxxMallException(LxxMallExceptionEnum.INSERT_FAILED);
        }

    }

    @Override
    public User login(String userName, String password) throws LxxMallException {
        String md5Password = null;
        try {
            md5Password = MD5Utils.getMD5String(password);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        User user = userMapper.selectLogin(userName, md5Password);
        if(user == null){
            throw new LxxMallException(LxxMallExceptionEnum.WRONG_PASSWORD);
        }
        return user;
    }

    @Override
    public void updateInformation(User user) throws LxxMallException {
        int updateCount = userMapper.updateByPrimaryKeySelective(user);
        if (updateCount > 1) {
            throw new LxxMallException(LxxMallExceptionEnum.UPDATE_FAILED);
        }
    }

    @Override
    public boolean checkAdminRole(User user){
        return user.getRole().equals(2);
    }

}
