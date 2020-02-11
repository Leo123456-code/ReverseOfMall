package com.imooc.sell.service.Impl;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.imooc.sell.DemoApplicationTests;
import com.imooc.sell.enums.ResponseEnum;
import com.imooc.sell.service.ICategoryService;
import com.imooc.sell.vo.CategoryVo;
import com.imooc.sell.vo.ResponseVo;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * created by Leo徐忠春
 * created Time 2020/2/6-23:21
 * email 1437665365@qq.com
 */
@Slf4j
public class CategoryServiceImplTest extends DemoApplicationTests {
    @Autowired
    private ICategoryService categoryService;

    private Gson gson = new GsonBuilder().setPrettyPrinting().create();

    private static final Integer ID = 100001;

    @Test
    public void selectAllByStatus() {
        ResponseVo<List<CategoryVo>> responseVo =
                categoryService.selectAllByStatus();
        log.info("result={}",gson.toJson(responseVo));
        //查看查询是否为空
        Assert.assertNotNull(responseVo);
        //查看返回状态是否为0
        Assert.assertEquals(ResponseEnum.SUCCESS.getCode(),responseVo.getStatus());

    }

    @Test
    public void findSubsCategoryId() {
        Set<Integer> set = new HashSet<>();
        categoryService.findSubsCategoryId(ID,set);
        log.info("set={}",gson.toJson(set));
    }
}