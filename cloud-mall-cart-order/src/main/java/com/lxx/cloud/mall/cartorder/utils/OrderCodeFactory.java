package com.lxx.cloud.mall.cartorder.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

/**
 * @author 林修贤
 * @date 2023/2/19
 * @description 生成订单号工具
 */
public class OrderCodeFactory {
    public static ThreadLocal<SimpleDateFormat> simpleDateFormatThreadLocal = new ThreadLocal<SimpleDateFormat>(){
        @Override
        protected SimpleDateFormat initialValue() {
            return new SimpleDateFormat("yyyyMMddHHmmss");
        }
    };
    private static String getDateTime(){
        DateFormat sdf = simpleDateFormatThreadLocal.get();
        return sdf.format(new Date());
    }

    private static int getRandom(Long n){
        Random random = new Random();
        // 获取 5 位随机数
        return (int)(random.nextDouble() * (90000)) + 10000;
    }

    public static String getOrderCode(Long userId){
        return getDateTime() + getRandom(userId);
    }
}
