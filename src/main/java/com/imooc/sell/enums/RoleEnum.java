package com.imooc.sell.enums;

/**
 * created by Leo徐忠春
 * created Time 2020/2/5-23:08
 * email 1437665365@qq.com
 */

import lombok.Getter;

/**
 * 角色枚举
 */
@Getter
public enum RoleEnum {
    ADMIN(0),
    CUSTOMER(1),
    ;
    Integer code;

    RoleEnum(Integer code) {
        this.code = code;
    }
}
