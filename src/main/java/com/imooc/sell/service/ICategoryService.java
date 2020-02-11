package com.imooc.sell.service;

/**
 * created by Leo徐忠春
 * created Time 2020/2/6-18:03
 * email 1437665365@qq.com
 */

import com.imooc.sell.vo.CategoryVo;
import com.imooc.sell.vo.ResponseVo;

import java.util.List;
import java.util.Set;

/**
 * 商品类目
 */
public interface ICategoryService {
    //根据状态查询所有的类目商品,多级联查
    ResponseVo<List<CategoryVo>> selectAllByStatus();
    //查询所有的子类目,子子类目的Id  集合  多级联查
    void findSubsCategoryId(Integer id, Set<Integer> resultSet);
}
