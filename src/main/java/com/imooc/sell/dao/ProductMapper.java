package com.imooc.sell.dao;


import com.imooc.sell.pojo.Product;
import org.apache.ibatis.annotations.Param;
import java.util.List;
import java.util.Set;

public interface ProductMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Product record);

    int insertSelective(Product record);



    int updateByPrimaryKeySelective(Product record);

    int updateByPrimaryKey(Product record);
    //查询所有商品信息 根据id
    List<Product> selectByCategoryIdSet(@Param("categoryIdSet")
                                                Set<Integer> categoryIdSet);
    //根据id查询商品详情
    Product selectByPrimaryKey(Integer id);

    //查询所有商品信息 根据productId集合
    List<Product> selectByProductIdSet(@Param("productIdSet")
                                                Set<Integer> productIdSet);
}