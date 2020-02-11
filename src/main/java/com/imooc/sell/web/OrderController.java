package com.imooc.sell.web;

import com.github.pagehelper.PageInfo;
import com.imooc.sell.consts.MallConsts;
import com.imooc.sell.form.OrderCreateForm;
import com.imooc.sell.pojo.MallUser;
import com.imooc.sell.service.IOrderService;
import com.imooc.sell.vo.OrderVo;
import com.imooc.sell.vo.ResponseVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

/**
 * 订单
 * 不可变：商品图片,收货地址
 * 可变:状态
 *
 * created by Leo徐忠春
 * created Time 2020/2/9-16:38
 * email 1437665365@qq.com
 */
@RestController
@Slf4j
public class OrderController {
    @Autowired
    private IOrderService orderService;

    //创建订单
    @PostMapping("/orders")
    public ResponseVo<OrderVo> create (@Valid @RequestBody OrderCreateForm form,
                                      HttpSession session) throws Exception {
        MallUser user = (MallUser) session.getAttribute(MallConsts.CURRENT);
        return orderService.create(user.getId(),form.getShippingId());
    }

    //列表信息
    @GetMapping("/orders")
    public ResponseVo<PageInfo> list (@RequestParam(required = true,defaultValue = "1") Integer pageNum,
                                      @RequestParam(required = true,defaultValue = "10")Integer pageSize,
                                      HttpSession session) throws Exception {
        MallUser user = (MallUser) session.getAttribute(MallConsts.CURRENT);
        return orderService.list(user.getId(),pageNum,pageSize);
    }
    //列表信息
    @GetMapping("/orders/{orderNo}")
    public ResponseVo<OrderVo> detail (@PathVariable Long orderNo, HttpSession session) {
        MallUser user = (MallUser) session.getAttribute(MallConsts.CURRENT);

        return orderService.detail(user.getId(),orderNo);
    }

    //订单取消
    @PutMapping("/orders/{orderNo}")
    public ResponseVo<OrderVo> cancel (@PathVariable Long orderNo, HttpSession session) {
        MallUser user = (MallUser) session.getAttribute(MallConsts.CURRENT);

        return orderService.cancel(user.getId(),orderNo);
    }


}
