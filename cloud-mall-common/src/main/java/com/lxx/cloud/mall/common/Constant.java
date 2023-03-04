package com.lxx.cloud.mall.common;

import com.google.common.collect.Sets;
import com.lxx.cloud.mall.exception.LxxMallException;
import com.lxx.cloud.mall.exception.LxxMallExceptionEnum;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Set;

/**
 * @author 林修贤
 * @date 2023/2/11
 * @description 常量值
 */
@Component
public class Constant {

    public static final String LXX_MALL_USER = "lxx_mall_user";
    public static final String SALT = "ig(s*'/2few";
    public static final String EMAIL_SUBJECT = "您的验证码";
    public static final String EMAIL_FROM = "2625453652@qq.com";
    public static final Integer IMAGE_SIZE = 400;
    public static final Float IMAGE_OPACITY = 0.5f;
    public static final String WATER_MARK_JPG = "watermark.jpg";

    public static String FILE_UPLOAD_DIR;

    @Value("${file.upload.dir}")
    public void setFileUploadDir(String fileUploadDir) {
        FILE_UPLOAD_DIR = fileUploadDir;
    }

    public interface ProductListOrderBy {
        Set<String> PRICE_ASC_DESC = Sets.newHashSet("price desc", "price asc");

    }

    public interface SaleStatus {
        int NOT_SALE = 0; //商品下架状态
        int SALE = 1; //商品上架状态
    }

    public interface Cart {
        int UN_CHECKED = 0; //购物车未选中状态
        int CHECKED = 1; //购物车选中状态
    }

    public enum OrderStatusEnum{
        CANCELED(0, "用户已取消"),
        NOT_PAID(10, "未付款"),
        PAID(20, "已付款"),
        DELIVERED(30, "已发货"),
        FINISHED(40, "交易完成");

        private String value;
        private int code;

        OrderStatusEnum(int code, String value) {
            this.value = value;
            this.code = code;
        }

        public static OrderStatusEnum codeOf(int code){
            for (OrderStatusEnum orderStatusEnum : values()){
                if (orderStatusEnum.getCode() == code){
                    return orderStatusEnum;
                }
            }
            throw new LxxMallException(LxxMallExceptionEnum.NO_ENUM);
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }
    }

    public static final String JWT_KEY = "imooc-mall";
    public static final String JWT_TOKEN = "jwt_token";
    public static final String USER_NAME = "user_name";
    public static final String USER_ID = "user_id";
    public static final String USER_ROLE = "user_role";
    public static final Long EXPIRE_TIME = 60 * 1000 * 60 * 24 * 5L; //单位毫秒
}
