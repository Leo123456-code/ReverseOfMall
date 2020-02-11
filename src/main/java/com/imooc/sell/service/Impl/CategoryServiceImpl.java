package com.imooc.sell.service.Impl;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.imooc.sell.dao.CategoryMapper;
import com.imooc.sell.pojo.Category;
import com.imooc.sell.service.ICategoryService;
import com.imooc.sell.vo.CategoryVo;
import com.imooc.sell.vo.ResponseVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;

import static com.imooc.sell.consts.MallConsts.ROOT_PARENT_ID;

/**
 * created by Leo徐忠春
 * created Time 2020/2/6-18:09
 * email 1437665365@qq.com
 */
@Slf4j
@Service
public class CategoryServiceImpl implements ICategoryService {

    private Gson gson = new GsonBuilder().setPrettyPrinting().create();

    @Autowired
    private CategoryMapper categoryMapper;

    ////根据状态查询所有的类目商品,多级联查

    //1.查父目录
    @Override
    public ResponseVo<List<CategoryVo>> selectAllByStatus() {
        /**
         * 1.先查出1级目录 parentId=0;
         * 2.查其子目录,一直查到的是null为止;
         */
        //categoryVoList 是存储所有类目商品的集合
        List<CategoryVo> categoryVoList = new ArrayList<>();
        //查询所有的上架商品
        List<Category> categories = categoryMapper.selectAll();
        //查出ParentId=0的数据(查一级目录）
        for (Category category : categories) {
            if(category.getParentId().equals(ROOT_PARENT_ID)){
                CategoryVo categoryVo = new CategoryVo();
                //把category的内容复制给categoryVo
                BeanUtils.copyProperties(category,categoryVo);
                categoryVoList.add(categoryVo);
            }
        }
        //查询子目录
        findSubCategory(categoryVoList,categories);
        return ResponseVo.success(categoryVoList);
    }

    //查询子目录具体实现
    private void findSubCategory(List<CategoryVo> categoryVoList,
                                 List<Category> categories) {
        for (CategoryVo categoryVo : categoryVoList) {
            //存储子目录类目商品的集合
            List<CategoryVo> subCategoryList = new ArrayList<>();
            for (Category category : categories) {
                //如果查到内容,设置subCategories,继续往下查
                if(categoryVo.getId().equals(category.getParentId())){
                    CategoryVo subCategoryVo=categoryVo(category);
                    subCategoryList.add(subCategoryVo);
                }
                //根据SortOrder进行降序排序  reversed() 降序
                subCategoryList.sort(Comparator.comparing
                        (CategoryVo::getSortOrder).reversed());
                categoryVo.setSubCategories(subCategoryList);
                //查询多级级目录(递归查询)
                findSubCategory(subCategoryList,categories);
            }
        }
    }

    ////查询所有的子类目,子子类目的Id  集合  多级联查
    @Override
    public void findSubsCategoryId(Integer id, Set<Integer> resultSet) {
        List<Category> categories = categoryMapper.selectAll();
        findSubsCategoryId(id,resultSet,categories);

    }

    //重载 findSubsCategoryId 方法
    public void findSubsCategoryId(Integer id, Set<Integer> resultSet,
                                   List<Category> categories){
        for (Category category : categories) {
            if(category.getParentId().equals(id)){
                resultSet.add(category.getId());
                findSubsCategoryId(category.getId(),resultSet,categories);
            }
        }
    }

    //转换  公共的方法  把category转换成categoryVo
    private CategoryVo categoryVo(Category category){
        CategoryVo categoryVo = new CategoryVo();
        BeanUtils.copyProperties(category,categoryVo);
        return categoryVo;
    }

}
