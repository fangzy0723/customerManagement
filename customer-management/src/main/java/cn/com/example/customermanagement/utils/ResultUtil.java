package cn.com.example.customermanagement.utils;


import cn.com.example.customermanagement.domain.ResultBean;

/**
 * Created by fanzgy on 2017/8/3.
 */
public class ResultUtil {
    /**
     * 返回成功的Result对象，带有data数据
     * @param code 返回状态码
     * @param message  返回信息
     * @param object  返回数据
     * @return
     */
    public static ResultBean success(Integer code, String message, Object object){
        ResultBean result = new ResultBean();
        result.setCode(code);
        result.setMessage(message);
        result.setData(object);
        return result;
    }
    /**
     * 返回成功的Result对象，没有data数据
     * @param code  状态码
     * @param message  返回信息
     * @return
     */
    public static ResultBean success(Integer code,String message){
        return success(code,message,null);
    }
    /**
     * 返回失败的Result对象
     * @param code  状态码
     * @param message  返回信息
     * @return
     */
    public static ResultBean error(Integer code,String message){
        ResultBean result = new ResultBean();
        result.setCode(code);
        result.setMessage(message);
        return result;
    }
}
