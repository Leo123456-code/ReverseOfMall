package com.imooc.sell.listener;


import com.google.gson.Gson;
import com.imooc.sell.pojo.PayInfo;
import com.imooc.sell.service.IOrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 监听支付的消息
 * created by Leo徐忠春
 * created Time 2020/2/10-20:01
 * email 1437665365@qq.com
 */
@Slf4j
@Component
//队列的名称
@RabbitListener(queues = "payNotify" )
public class PayMsgListener {
    @Autowired
    private IOrderService orderService;

    @RabbitHandler
    public void process(String msg){

        log.info("[接收到消息]={}",msg);
        PayInfo payInfo = new Gson().fromJson(msg, PayInfo.class);
        //如果支付状态是成功的
        if(payInfo.getPlatformStatus().equals("SUCCESS")){
            //修改订单里的状态
            orderService.paid(payInfo.getOrderNo());
        }
    }
}
