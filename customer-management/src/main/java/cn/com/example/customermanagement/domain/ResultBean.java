package cn.com.example.customermanagement.domain;

import java.io.Serializable;

/**
 * Created by fangzy on 2018/1/17 14:14
 */
public class ResultBean<T> implements Serializable {

    private Integer code;
    private String message;
    private T data;


    public ResultBean(Integer code, String message, T data) {
        super();
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public ResultBean() {
    }

    public ResultBean(T data) {
        super();
        this.data = data;
    }

    public ResultBean(Throwable e) {
        super();
        this.code = ResultEnum.ERROR.getCode();
        this.message = e.toString();
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
