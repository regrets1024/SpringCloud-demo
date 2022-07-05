package com.aka.demo.common.model;

import com.aka.demo.common.enums.ResponseCodeEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

/**
 * 封装返回结果
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Result<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 状态码
     *
     * @see ResponseCodeEnum
     */
    private Integer code;

    /**
     * 消息
     *
     * @mock 成功
     */
    private String desc;

    /**
     * 数据
     */
    private T data;

    public static <T> Result<T> success() {
        return new Result<>(ResponseCodeEnum.SUCCESS.getCode(), ResponseCodeEnum.SUCCESS.getDesc(), null);
    }

    public static <T> Result<T> success(T data) {
        return new Result<>(ResponseCodeEnum.SUCCESS.getCode(), ResponseCodeEnum.SUCCESS.getDesc(), data);
    }

    public static <T> Result<T> fail() {
        return new Result<>(ResponseCodeEnum.FAIL.getCode(), ResponseCodeEnum.FAIL.getDesc(), null);
    }

    public static <T> Result<T> fail(String desc) {
        return new Result<>(ResponseCodeEnum.FAIL.getCode(), desc, null);
    }

    public static <T> Result<T> error() {
        return new Result<>(ResponseCodeEnum.ERROR.getCode(), ResponseCodeEnum.ERROR.getDesc(), null);
    }

    public static <T> Result<T> error(String desc) {
        return new Result<>(ResponseCodeEnum.ERROR.getCode(), desc, null);
    }
//
//    public boolean isSuccess() {
//        return ResponseCodeEnum.SUCCESS.getCode().equals(this.getCode());
//    }

}
