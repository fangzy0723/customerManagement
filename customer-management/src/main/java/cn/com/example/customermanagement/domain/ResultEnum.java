package cn.com.example.customermanagement.domain;

/**
 * Created by fangzy on 2018/1/17 14:15
 */
public enum ResultEnum {
    UNKONW_ERROR(-1,"未知错误"),
    SUCCESS(1,"成功"),
    ERROR(0,"出错了");

    private Integer code;
    private String msg;

    ResultEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
