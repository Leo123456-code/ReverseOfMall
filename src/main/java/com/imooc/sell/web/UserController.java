package com.imooc.sell.web;


import com.imooc.sell.consts.MallConsts;
import com.imooc.sell.form.UserLoginForm;
import com.imooc.sell.form.UserRegisterForm;
import com.imooc.sell.pojo.MallUser;
import com.imooc.sell.service.IUserService;
import com.imooc.sell.vo.ResponseVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

/**
 * created by Leo徐忠春
 * created Time 2020/2/5-23:18
 * email 1437665365@qq.com
 */
@RestController
@Slf4j
/**
 * 用户模块
 */
public class UserController {
    @Autowired
    private IUserService userService;

    //表单提交 @Valid
    @PostMapping("/user/register")
    public ResponseVo register(@Valid @RequestBody UserRegisterForm userForm
                               ){
        //参数判断是否有错
        //在统一异常处理中有处理
//        if(bindingResult.hasErrors()){
//            log.info("注册提交的参数有误,{}{}",
//                    //获取为空的字段
//                    bindingResult.getFieldError().getField(),
//                    //打印错误的信息
//                    bindingResult.getFieldError().getDefaultMessage());
//            //返回参数错误,和具体的错误信息
//            return ResponseVo.error(PARAM_ERROR,
//                    bindingResult);
//        }
        MallUser user = new MallUser();
        //第一参数传原对象,第二个传要复制的对象
        BeanUtils.copyProperties(userForm,user);
        log.info("username={}",userForm.getUsername());
        //存入数据
        return userService.register(user);

    }

    /**
     * 登录
     * @return
     */
    @PostMapping("/user/login")
    public ResponseVo<MallUser> login(@Valid @RequestBody UserLoginForm loginForm,
                                      HttpServletRequest httpServletRequest){
            //参数判断是否有错
        //在统一异常处理中有处理
//            if(bindingResult.hasErrors()){
//                log.info("注册提交的参数有误,{}{}",
//                        //获取为空的字段
//                        bindingResult.getFieldError().getField(),
//                        //打印错误的信息
//                        bindingResult.getFieldError().getDefaultMessage());
//                //返回参数错误,和具体的错误信息
//                return ResponseVo.error(PARAM_ERROR,
//                        bindingResult);
//            }
        ResponseVo<MallUser> responseVo =
                userService.login(loginForm.getUsername(), loginForm.getPassword());
            //设置session
        HttpSession session = httpServletRequest.getSession();
        session.setAttribute(MallConsts.CURRENT,responseVo.getData());

        return responseVo;
    }

    /**
     * 獲取用戶的登錄信息
     * session保存在内存里,服务器重启后就没有了,session会存在Redis中
     * token(token其实就是sessionId)加redis
     */
    @GetMapping("/user")
    public ResponseVo<MallUser> userInfo(HttpSession session){
        MallUser user = (MallUser) session.getAttribute(MallConsts.CURRENT);
        //在拦截器统一判断是否登录,在这里不需要判断了
//        if(user == null){
//            return ResponseVo.error(NEED_LOGIN);//未登录
//        }
        return ResponseVo.success(user);

    }

    /**
     * 登出
     * @param session
     * @return
     */
    //TODO 判断登录状态,拦截器
    @PostMapping("/user/logout")
    public ResponseVo logout(HttpSession session){
        log.info("sessionId={}",session.getId());
        MallUser user = (MallUser) session.getAttribute(MallConsts.CURRENT);
        //在拦截器统一判断是否登录,在这里不需要判断了
//        if(user == null){
//            return ResponseVo.error(NEED_LOGIN);//未登录
//        }
        //移除session
        session.removeAttribute(MallConsts.CURRENT);
        return ResponseVo.success();
    }
}
