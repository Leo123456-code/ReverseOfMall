package com.imooc.sell.config;

import com.imooc.sell.intercepter.UserLoginIntercepter;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * created by Leo徐忠春
 * created Time 2020/2/6-17:01
 * email 1437665365@qq.com
 */
@Configuration
public class InterceptoeConfig implements WebMvcConfigurer {

    //使用拦截器
    //配置拦截地址
    //addPathPatterns("/**") 这里配置需要拦截的
    //excludePathPatterns("/user/login"); 这里配置不需要拦截的
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new UserLoginIntercepter()).addPathPatterns("/**").
                excludePathPatterns("/user/register","/user/login",
                        "/categories","/products","/products/*"
                ,"/error","/carts","/carts/*",
                        "/carts/selectAll","/carts/unSelectAll",
                        "/carts/products/sum","/shippings","/shippings/*");

    }
}
