package com.imooc.sell.vo;

/**
 * created by Leo徐忠春
 * created Time 2020/2/5-23:29
 * email 1437665365@qq.com
 */

import com.fasterxml.jackson.annotation.JsonInclude;
import com.imooc.sell.enums.ResponseEnum;
import lombok.Data;
import org.springframework.validation.BindingResult;

import java.util.Objects;

/**
 * 前端返回的页面数据
 */
@Data
//值为null,前端不显示
@JsonInclude(value = JsonInclude.Include.NON_NULL )
public class ResponseVo<T> {
    //状态
    private Integer status;
    //信息
    private String msg;
    //具体数据
    private T data;

    public ResponseVo(Integer status, String msg) {
        this.status = status;
        this.msg = msg;
    }
    //成功
    public static <T> ResponseVo<T> successByMsg(String msg){
        return new ResponseVo<T>(ResponseEnum.SUCCESS.getCode(),msg);
    }
    //成功
    public static <T> ResponseVo<T> success(){
        return new ResponseVo<T>(ResponseEnum.SUCCESS.getCode(),
                ResponseEnum.SUCCESS.getMessage());
    }
    //成功
    public static <T> ResponseVo<T> success(T data){
            return new ResponseVo<T>(ResponseEnum.SUCCESS.getCode(),
                    data);
    }
    //成功
    public static <T> ResponseVo<T> success(String msg ,T data){
            return new ResponseVo<T>(ResponseEnum.SUCCESS.getCode(),
                    msg,data);
    }
    //成功
    public static <T> ResponseVo<T> success(String msg){
            return new ResponseVo<T>(ResponseEnum.SUCCESS.getCode(),
                    msg);
    }



    //成功
    public static <T> ResponseVo<T> success(Integer status,T data){
        return new ResponseVo<T>(status,
                data);
    }
    //失败
    public static <T> ResponseVo<T> error(ResponseEnum responseEnum){
        return new ResponseVo<T>(responseEnum.getCode(),
               responseEnum.getMessage());
    }
    public static <T> ResponseVo<T> error(ResponseEnum responseEnum,String msg){
        return new ResponseVo<T>(responseEnum.getCode(),
               msg);
    }
    public static <T> ResponseVo<T> error(ResponseEnum responseEnum, BindingResult bindingResult){
        return new ResponseVo<T>(responseEnum.getCode(),
               Objects.requireNonNull(bindingResult.getFieldError().getField()
                       +" "+bindingResult.getFieldError().getDefaultMessage()));
    }


    //成功
    public ResponseVo(Integer status, T data) {
        this.status = status;
        this.data = data;
    }

    public ResponseVo(Integer status, String msg, T data) {
        this.status = status;
        this.msg = msg;
        this.data = data;
    }
}
