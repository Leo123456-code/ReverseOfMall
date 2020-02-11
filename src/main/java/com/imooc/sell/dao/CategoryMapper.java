package com.imooc.sell.dao;

import com.imooc.sell.pojo.Category;

import java.util.List;

public interface CategoryMapper {

    int deleteByPrimaryKey(Integer id);

    int insert(Category record);

    int insertSelective(Category record);

    Category selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Category record);

    int updateByPrimaryKey(Category record);
    //查询所有上架的商品,status=1
    List<Category> selectAll();

}