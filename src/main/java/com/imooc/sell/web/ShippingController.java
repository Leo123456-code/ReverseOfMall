package com.imooc.sell.web;

import com.imooc.sell.consts.MallConsts;
import com.imooc.sell.form.ShippingAndForm;
import com.imooc.sell.pojo.MallUser;
import com.imooc.sell.service.IShippingService;
import com.imooc.sell.vo.ResponseVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.Map;

import static com.imooc.sell.enums.ResponseEnum.SHIPPING_NOT_LOGIN;

/**
 *  收货地址
 * created by Leo徐忠春
 * created Time 2020/2/8-20:48
 * email 1437665365@qq.com
 */
@RestController
@Slf4j
public class ShippingController {
    @Autowired
    private IShippingService shippingService;

    /**
     * 新增
     * @param form
     * @param session
     * @return
     */
    @PostMapping("/shippings")
    public ResponseVo shippings(@Valid @RequestBody ShippingAndForm form,
                                HttpSession session){
        MallUser user = (MallUser) session.getAttribute(MallConsts.CURRENT);
        ResponseVo<Map<String, Integer>> map = shippingService.add(user.getId(), form);
        return map;
    }

    /**
     * 删除地址
     */
    @DeleteMapping("/shippings/{shippingId}")
    public ResponseVo delete(@PathVariable Integer shippingId,
                               HttpSession session){
        MallUser user = (MallUser) session.getAttribute(MallConsts.CURRENT);

        return shippingService.delete(user.getId(),shippingId);
    }

    /**
     * 更新地址
     */
    @PutMapping("/shippings/{shippingId}")
    public ResponseVo update(@PathVariable Integer shippingId,
                               @Valid @RequestBody ShippingAndForm form,
                               HttpSession session){
        MallUser user = (MallUser) session.getAttribute(MallConsts.CURRENT);

        return shippingService.update(user.getId(),shippingId,form);
    }

    /**
     * 地址查询列表信息
     */
    @GetMapping("/shippings")
    public ResponseVo list(@RequestParam(required = false,defaultValue = "1") Integer pageNum,
                           @RequestParam(required = false,defaultValue = "10") Integer pageSize,
                           HttpSession session){
        MallUser user = (MallUser) session.getAttribute(MallConsts.CURRENT);

        if(user==null){
            return ResponseVo.error(SHIPPING_NOT_LOGIN);
        }
        return shippingService.list(user.getId(), pageNum, pageSize);
    }




}
