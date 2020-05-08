package com.lh.blog.exception;

import com.lh.blog.util.CodeMsg;
import com.lh.blog.util.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.BindException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 全局异常处理器
 * 通过Advice可知，这个处理器实际上是一个切面
 * @author linhao
 */
@RestController
@ControllerAdvice
public class GlobalExceptionHandler {

    private static Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    /**
     * 异常处理
     *
     * @param req 绑定了出现异常的请求信息
     * @param e       该请求所产生的异常
     * @return 向客户端返回的结果（这里为json数据）
     */
    @ExceptionHandler(value = Exception.class)
    public Result defaultErrorHandler(HttpServletRequest req, Exception e) {
        logger.error("出现异常", e);
        // 如果所拦截的异常是自定义的全局异常，按自定义异常的处理方式处理，否则按默认方式处理
        if (e instanceof GlobalException) {
            GlobalException exception = (GlobalException) e;
            logger.debug("自定义全局异常:" + exception.getCodeMsg());
            // 向客户端返回异常信息
            return Result.error(exception.getCodeMsg());

        }else if (e instanceof MethodArgumentNotValidException){
            // 同样是获取BindingResult对象，然后获取其中的错误信息
            // 如果前面开启了fail_fast，事实上这里只会有一个信息
            //如果没有，则可能又多个
            List<String> errorInformation = ((MethodArgumentNotValidException)e).getBindingResult().getAllErrors()
                    .stream()
                    .map(ObjectError::getDefaultMessage)
                    .collect(Collectors.toList());
            return Result.error(CodeMsg.BIND_ERROR.fillArgs("", errorInformation.toString()));
        }
        else if (e instanceof ConstraintViolationException){
            List<String> errorInformation = ((ConstraintViolationException)e).getConstraintViolations()
                    .stream()
                    .map(ConstraintViolation::getMessage)
                    .collect(Collectors.toList());
            return Result.error(CodeMsg.BIND_ERROR.fillArgs("", errorInformation.toString()));
        }
        else if (e instanceof BindException) {
            BindException bindException = (BindException) e;
            List<ObjectError> errors = bindException.getAllErrors();
            // 这里只获取了第一个错误对象
            ObjectError error = errors.get(0);
            // 获取其中的信息
            String message = error.getDefaultMessage();
            // 将错误信息动态地拼接到已定义的部分信息上
            return Result.error(CodeMsg.BIND_ERROR.fillArgs(message));
        } else {
            return Result.error(CodeMsg.SERVER_ERROR);
        }
    }
}
