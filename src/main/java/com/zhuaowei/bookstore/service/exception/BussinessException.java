package com.zhuaowei.bookstore.service.exception;

/**
 * @ClassName: BussinessException
 * @Description: 业务逻辑异常
 * @Author: zhuaowei
 * @Date: 2021/8/23 20:59
 * @Version: 1.0
 **/
public class BussinessException extends RuntimeException {
    private String code;
    private String message;

    public BussinessException(String code, String message) {
        super(message);
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
