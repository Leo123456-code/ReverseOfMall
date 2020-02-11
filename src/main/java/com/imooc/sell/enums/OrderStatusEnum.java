package com.imooc.sell.enums;

import lombok.Getter;

/**
 * 订单状态
 * created by Leo徐忠春
 * created Time 2020/2/9-21:25
 * email 1437665365@qq.com
 */
@Getter
public enum OrderStatusEnum {

    CANCELED(0,"已取消"),
    NO_PAY(10,"未付款"),
    PAID(20,"已付款"),
    SHIPPED(40,"已发货"),
    TRADE_SUCCESS(50,"交易成功"),
    TRADE_CLOSE(60,"交易关闭"),
    ;
    Integer code;
    String message;

    OrderStatusEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
