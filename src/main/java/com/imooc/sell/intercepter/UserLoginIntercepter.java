package com.imooc.sell.intercepter;

/**
 * created by Leo徐忠春
 * created Time 2020/2/6-16:45
 * email 1437665365@qq.com
 */


import com.imooc.sell.consts.MallConsts;
import com.imooc.sell.exception.UserLoginException;
import com.imooc.sell.pojo.MallUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


/**
 * 统一对登录状态进行拦截
 * 针对URL进行拦截
 * 对登录状态进行判断
 */
@Slf4j
public class UserLoginIntercepter implements HandlerInterceptor {
    /**
     * true表示继续流程,false表示中断
     */
    //preHandle 在请求之前进行拦截
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        log.info("preHandle...");
        log.info("sessionId={}",request.getSession().getId());
        HttpSession session = request.getSession();
        MallUser user = (MallUser) session.getAttribute(MallConsts.CURRENT);
        if(user == null){
            log.info("user==null");
            throw new UserLoginException();//抛出异常
        }
        return true;
    }
}
