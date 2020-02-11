package com.imooc.sell;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan(basePackages = "com.imooc.sell.dao")
public class MallApplication {

    public static void main(String[] args) {

        SpringApplication.run(MallApplication.class, args);
    }
}
