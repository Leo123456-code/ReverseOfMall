package com.imooc.sell.enums;

import lombok.Getter;

/**
 * created by Leo徐忠春
 * created Time 2020/2/9-21:22
 * email 1437665365@qq.com
 */
@Getter
public enum PaymentTypeEnum {
    PAY_ONLINE(1),
    ;
    Integer code;

    PaymentTypeEnum(Integer code) {
        this.code = code;
    }
}
