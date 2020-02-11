package com.imooc.sell.web;

import com.imooc.sell.service.ICategoryService;
import com.imooc.sell.vo.CategoryVo;
import com.imooc.sell.vo.ResponseVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * created by Leo徐忠春
 * created Time 2020/2/6-17:55
 * email 1437665365@qq.com
 */
@RestController
@Slf4j
public class CategoryController {
    @Autowired
    private ICategoryService categoryService;

    /**
     * 查询多级目录菜单
     */
    @GetMapping("/categories")
    public ResponseVo<List<CategoryVo>> categories(){

        return categoryService.selectAllByStatus();
    }
}
