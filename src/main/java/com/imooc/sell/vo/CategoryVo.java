package com.imooc.sell.vo;

import lombok.Data;

import java.util.List;

/**
 * created by Leo徐忠春
 * created Time 2020/2/6-17:59
 * email 1437665365@qq.com
 */

/**
 * 递归,自己包含自己
 */
@Data
public class CategoryVo {


    /**
     * 类别Id
     */
    private Integer id;

    /**
     * 父类别id当id=0时说明是根节点,一级类别
     */
    private Integer parentId;
    /**
     * 类别名称
     */
    private String name;
    /**
     * 排序编号,同类展示顺序,数值相等则自然排序
     */
    private Integer sortOrder;
    //子类目
    private List<CategoryVo> subCategories;
}
