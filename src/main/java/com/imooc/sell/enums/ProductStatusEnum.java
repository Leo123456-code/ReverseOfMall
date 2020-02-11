package com.imooc.sell.enums;

/**
 * created by Leo徐忠春
 * created Time 2020/2/7-16:28
 * email 1437665365@qq.com
 */

import lombok.Getter;

/**
 * 商品状态枚举
 */
@Getter
public enum ProductStatusEnum {

    ON_SALE(1),

    OFF_SALE(2),

    DELETE(3),
    ;

    Integer code;

    ProductStatusEnum(Integer code) {
        this.code = code;
    }
}
