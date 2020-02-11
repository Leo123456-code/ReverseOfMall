package com.imooc.sell.service.Impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.imooc.sell.dao.ShippingMapper;
import com.imooc.sell.form.ShippingAndForm;
import com.imooc.sell.pojo.Shipping;
import com.imooc.sell.service.IShippingService;
import com.imooc.sell.vo.ResponseVo;
import com.imooc.sell.vo.ShippingVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

import static com.imooc.sell.enums.ResponseEnum.*;

/**
 * created by Leo徐忠春
 * created Time 2020/2/8-20:59
 * email 1437665365@qq.com
 */
@Slf4j
@Service
public class ShippingServiceImpl implements IShippingService {

    @Autowired
    private ShippingMapper shippingMapper;

    /**
     * 新增地址
     * @param uid
     * @param form
     * @return
     */
    @Override
    public ResponseVo<Map<String, Integer>> add(Integer uid, ShippingAndForm form) {
        Shipping shipping = new Shipping();
        BeanUtils.copyProperties(form,shipping);
        //新增
        shipping.setUserId(uid);
        shipping.setCreateTime(new Date());
        shipping.setUpdateTime(new Date());
        int rows = shippingMapper.insertSelective(shipping);
        if(rows == 0){
            return ResponseVo.error(ERROR);//服务端错误
        }
        Map<String, Integer> map = new HashMap<>();
        map.put("shippingId",shipping.getId());

        return ResponseVo.success(map);
    }

    /**
     * 删除地址
     * @param uid
     * @param shippingId
     * @return
     */
    @Override
    public ResponseVo delete(Integer uid, Integer shippingId) {
        int rows = shippingMapper.deleteByIdAndUid(uid, shippingId);
        if(rows==0){
            return ResponseVo.error(SHIPPING_DELETE_FAIL);
        }
        return ResponseVo.success(SHIPPING_DELETE_SUCCESS.getMessage());

    }

    /**
     * 修改地址
     * @param uid
     * @param form
     * @return
     */
    @Override
    public ResponseVo update(Integer uid,Integer shippingId,ShippingAndForm form) {
        Shipping shipping = new Shipping();
        BeanUtils.copyProperties(form,shipping);

        int rows = shippingMapper.updateByPrimaryKeySelective(shipping);
        if(rows==0){
            return ResponseVo.error(SHIPPING_UPDATE_FAIL);
        }
        return ResponseVo.success(SHIPPING_UPDATE_SUCCESS.getMessage());
    }

    /**
     * 列表信息
     * @param uid
     * @param pageNum
     * @param pageSize
     * @return
     */
    @Override
    public ResponseVo<PageInfo> list(Integer uid, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        //查询数据库
        List<Shipping> list = shippingMapper.selectByUid(uid);
        //  把shipping的值赋给 shippingVo
        List<ShippingVo> shippingVoList = new ArrayList<>();
        for (Shipping s : list) {
            ShippingVo shippingVo = new ShippingVo();
            BeanUtils.copyProperties(s,shippingVo);
            shippingVoList.add(shippingVo);
        }

        PageInfo<ShippingVo> pageInfo = new PageInfo<>(shippingVoList);

        if(pageInfo==null){
            return ResponseVo.error(ERROR);
        }
        return ResponseVo.success(pageInfo);
    }
}
