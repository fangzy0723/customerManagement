package cn.com.example.customermanagement.advice;

import cn.com.example.customermanagement.domain.ResultBean;
import cn.com.example.customermanagement.domain.ResultEnum;
import cn.com.example.customermanagement.exception.CustomException;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * 类级注解：
 * @Aspect 声明这是一个切面对象
 * 方法级注解：
 * @Pointcut 声明一个切点规则
 * @Before 方法执行之前，执行通知。
 * @After 方法执行之后，不考虑其结果，执行通知。
 * @AfterReturning 方法执行之后，只有在方法成功完成时，才能执行通知
 * @AfterThrowing 方法执行之后，只有在方法退出抛出异常时，才能执行通知
 * @Around 在方法调用之前和之后，执行通知 等价于上面四个相加
 */
@Aspect
@Component
public class ResultBeansAdvice {
    Logger logger = LoggerFactory.getLogger(ResultBeansAdvice.class);

    /**
     * 切所有带有@ResultBeans注解的方法
     */
    @Pointcut("@annotation(cn.com.example.customermanagement.anno.ResultBeans)")
    private void businessService() {
    }


    /**
     * 环绕通知
     * @param pjp
     * @return
     */
    @Around("businessService()")
    public Object handlerControllerMethod(ProceedingJoinPoint pjp){
        long startTime = System.currentTimeMillis();
        ResultBean<?> result;

        try{
            result =(ResultBean<?>)pjp.proceed();
            result.setCode(ResultEnum.SUCCESS.getCode());
            result.setMessage(ResultEnum.SUCCESS.getMsg());

            logger.info("执行方法：{}  耗时: {}",pjp.getSignature(),(System.currentTimeMillis()-startTime));

        }catch(Throwable e){
            result =handlerException(pjp, e);
        }

        return result;
    }

    private ResultBean<?> handlerException(ProceedingJoinPoint pjp,Throwable e){

        ResultBean<?> result = new ResultBean();
        if(e instanceof CustomException){
        	CustomException CustomException = (CustomException)e;
            logger.info("这是个ResultBeansAdvice中的自定义异常");
            result.setMessage(CustomException.getMessage());
            result.setCode(CustomException.getCode());
        }else{
            logger.info("这是个ResultBeansAdvice中的未知异常");
            logger.error(pjp.getSignature()+" error ", e);
            result.setMessage(e.toString());
            result.setCode(ResultEnum.UNKONW_ERROR.getCode());
            // 未知异常是应该重点关注的，这里可以做其他操作，如通知邮件，单独写到某个文件等等。
        }
        return result;
    }
}
