package cn.com.example.customermanagement.exception;

import cn.com.example.customermanagement.domain.ResultBean;
import cn.com.example.customermanagement.domain.ResultEnum;
import cn.com.example.customermanagement.utils.ResultUtil;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by fangzy on 2018/1/17 15:27
 */
@ControllerAdvice
public class ExceptionHandle {

    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public ResultBean handle(Exception e){
        if(e instanceof CustomException){
            CustomException customException = (CustomException) e;
            return ResultUtil.error(customException.getCode(),customException.getMessage());
        }
        return ResultUtil.error(ResultEnum.UNKONW_ERROR.getCode(),ResultEnum.UNKONW_ERROR.getMsg());
    }

}
