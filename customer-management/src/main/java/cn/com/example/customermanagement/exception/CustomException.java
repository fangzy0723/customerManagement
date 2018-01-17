package cn.com.example.customermanagement.exception;

/**
 * Created by fangzy on 2018/1/17 14:17
 */
public class CustomException extends RuntimeException {
    private Integer code;

    public CustomException(Integer code, String message) {
        super(message);
        this.code = code;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }
}
