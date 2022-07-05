package com.aka.demo.common.exception;

/**
 * 接口基础异常
 */
public class BaseException extends RuntimeException {

    static final long serialVersionUID = -7034897199645766939L;

    /**
     * 异常状态码
     */
    private int code;

    /**
     * 异常描述
     */
    private String message;

    /**
     * 参数
     */
    private transient Object[] args;

    public BaseException() {
        super();
    }

    public BaseException(int code) {
        super();
        this.code = code;
    }

    public BaseException(String message) {
        super(message);
        this.message = message;
    }

    public BaseException(Throwable cause) {
        super(cause);
    }

    public BaseException(int code, String message) {
        super(message);
        this.code = code;
        this.message = message;
    }

    public BaseException(int code, String message, Object[] args) {
        super(message);
        this.code = code;
        this.message = message;
        this.args = args;
    }

    public BaseException(int code, String message, Throwable cause) {
        super(message, cause);
        this.code = code;
        this.message = message;
    }

    public BaseException(int code, String message, Object[] args, Throwable cause) {
        super(message, cause);
        this.code = code;
        this.message = message;
        this.args = args;
    }

    public int getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return this.message;
    }
}
