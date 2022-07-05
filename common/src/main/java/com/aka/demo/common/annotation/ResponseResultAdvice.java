package com.aka.demo.common.annotation;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.aka.demo.common.exception.BaseException;
import com.aka.demo.common.model.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolationException;

/**
 * 对返回结果进行封装
 * <p>
 * 包含全局异常处理
 */
@SuppressWarnings("ALL")
@RestControllerAdvice(annotations = {RestController.class, ResponseResult.class})
public class ResponseResultAdvice implements ResponseBodyAdvice<Object> {

    private static final Logger logger = LoggerFactory.getLogger(ResponseResultAdvice.class);

    /**
     * 判断是否需要对返回值进行包装
     */
    @Override
    public boolean supports(MethodParameter methodParameter, Class<? extends HttpMessageConverter<?>> aClass) {
        if (methodParameter.getDeclaringClass().isAnnotationPresent(IgnoreResponseAdvice.class)) {
            return false;
        }
        if (methodParameter.getMethod().isAnnotationPresent(IgnoreResponseAdvice.class)) {
            return false;
        }
        return true;
    }

    /**
     * 包装返回结果
     */
    @Override
    @Nullable
    public Object beforeBodyWrite(@Nullable Object o, MethodParameter methodParameter, MediaType mediaType, Class<? extends HttpMessageConverter<?>> aClass, ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse) {
        Result<Object> result = Result.success();

        if (null == o) {
            return result;
        } else if (o instanceof Result) {
            return o;
        } else {
            result.setData(o);
//            if (o instanceof String) {
//                return JSONUtil.toJsonStr(result);
//            }
        }

        return result;
    }

    /**
     * 参数校验异常返回结果封装
     */
    @ExceptionHandler(ConstraintViolationException.class)
    public Result<Object> handleConstraintViolationException(ConstraintViolationException exception) {
        String message = CollUtil.getFirst(exception.getConstraintViolations()).getMessage();
        return Result.fail(message);
    }

    /**
     * 参数校验异常返回结果封装
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Result<Object> handleMethodArgumentNotValidException(MethodArgumentNotValidException exception) {
        String message = CollUtil.getFirst(exception.getBindingResult().getAllErrors()).getDefaultMessage();
        return Result.fail(message);
    }

    /**
     * 对自定义基础异常进行包装返回
     */
    @ExceptionHandler(BaseException.class)
    public Result<Object> handleBaseException(BaseException exception) {
        String message = StrUtil.isBlank(exception.getMessage()) ? "服务异常" : exception.getMessage();
        return Result.fail(message);
    }

    /**
     * 对抛出异常进行包装返回
     */
    @ExceptionHandler(Exception.class)
    public Result<Object> handleException(HttpServletRequest request, Exception exception) {
        this.printMessage(request, exception);
        return Result.error();
    }

    /**
     * 打印异常信息
     */
    private void printMessage(HttpServletRequest request, Exception exception) {
        // 获取请求方式
        String method = request.getMethod();
        // 获取请求URI
        String requestURI = request.getRequestURI();
        // 打印堆栈信息
        logger.error("request error, method:[{}] requestURI:[{}] ", method, requestURI, exception);
    }

}
