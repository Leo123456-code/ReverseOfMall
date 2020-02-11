package com.imooc.sell.enums;

import lombok.Getter;

/**
 * created by Leo徐忠春
 * created Time 2020/2/5-23:53
 * email 1437665365@qq.com
 */
@Getter
public enum ResponseEnum {

    ERROR(-1,"服务端错误"),
    SUCCESS(0,"成功"),
    PASSWORD_ERROR(1,"密码错误"),
    USERNAME_EXIST(2,"用户名已存在"),
    PARAM_ERROR(3,"参数错误"),
    EMAIL_ERROR(4,"邮箱已存在"),
    NEED_LOGIN(10,"用户未登录,请先登录"),
    USER_OR_PASSWORD_FAIL(11,"用戶名或密碼錯誤"),
    PRODUCT_OFF_SALE_OR_DELETE(12,"商品下架或删除"),
    PRODUCT_NOT_EXIST(13,"商品不存在"),
    PRODUCT_STOCK_NOT_MALL(14,"商品库存不足"),
    CART_PRODUCT_NOT_EXIST(15,"购物车里无此商品"),
    SHIPPING_DELETE_FAIL(16,"删除收货地址失败"),
    SHIPPING_DELETE_SUCCESS(17,"删除收货地址成功"),
    SHIPPING_UPDATE_SUCCESS(18,"更新收货地址成功"),
    SHIPPING_UPDATE_FAIL(19,"更新收货地址失败"),
    SHIPPING_NOT_LOGIN(20,"请登录之后查询"),
    SHIPPING_NOT_EXIST(21,"收货地址不存在"),
    CART_SELECTED_IS_EMPTY(22,"请选择商品后下单"),
    CART_STOCK_IS_EMPTY(23,"库存不足"),
    CART_NO(24,"订单不存在"),
    ORDER_STATUS_ERROR(25,"订单状态有误"),
    ;
    Integer code;
    String message;

    ResponseEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
