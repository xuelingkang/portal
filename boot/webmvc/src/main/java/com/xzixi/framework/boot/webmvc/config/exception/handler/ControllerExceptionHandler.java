package com.xzixi.framework.boot.webmvc.config.exception.handler;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.xzixi.framework.boot.webmvc.exception.ClientException;
import com.xzixi.framework.boot.webmvc.exception.ProjectException;
import com.xzixi.framework.boot.webmvc.exception.ServerException;
import com.xzixi.framework.boot.webmvc.model.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * @author xuelingkang
 * @date 2020-10-24
 */
@Slf4j
@Component
@RestControllerAdvice
public class ControllerExceptionHandler {

    @Value("${spring.servlet.multipart.max-file-size}")
    private String maxFileSize;

    /**
     * 上传文件大小超限
     *
     * @return Result
     */
    @ExceptionHandler(MaxUploadSizeExceededException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Result<?> handleMaxUploadSizeExceededException() {
        String errMsg = String.format("上传文件大小不能超过%s！", maxFileSize);
        return new Result<>(HttpStatus.BAD_REQUEST.value(), errMsg, null);
    }

    /**
     * 缺少@RequestParam(require=true)的参数
     *
     * @param e MissingServletRequestParameterException
     * @return Result
     */
    @ExceptionHandler(MissingServletRequestParameterException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Result<?> handleMissingServletRequestParameterException(MissingServletRequestParameterException e) {
        String errMsg = String.format("缺少参数（类型：%s，名称：%s）！", e.getParameterType(), e.getParameterName());
        return new Result<>(HttpStatus.BAD_REQUEST.value(), errMsg, null);
    }

    /**
     * 参数类型转换异常，用错误的类型接收参数，或参数值不合法导致转换异常
     *
     * @param e MethodArgumentTypeMismatchException
     * @return Result
     */
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Result<?> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException e) {
        String errMsg = String.format("参数类型转换错误（类型：%s，名称：%s，值：%s）！",
                e.getParameter().getParameterType().getSimpleName(), e.getName(), e.getValue());
        return new Result<>(HttpStatus.BAD_REQUEST.value(), errMsg, null);
    }

    /**
     * controller形参校验异常
     *
     * @param e ConstraintViolationException
     * @return Result
     */
    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Result<?> handleConstraintViolationException(ConstraintViolationException e) {
        Set<ConstraintViolation<?>> constraintViolations = e.getConstraintViolations();
        StringBuilder errorMsg = new StringBuilder();
        Iterator<ConstraintViolation<?>> iterator = constraintViolations.iterator();
        while (iterator.hasNext()) {
            ConstraintViolation<?> violation = iterator.next();
            errorMsg.append(violation.getMessage());
            if (iterator.hasNext()) {
                errorMsg.append(",");
            }
        }
        return new Result<>(HttpStatus.BAD_REQUEST.value(), errorMsg.toString(), null);
    }

    /**
     * 实体类参数校验异常
     * MethodArgumentNotValidException为@RequestBody参数校验异常
     * BindException为普通实体类参数校验异常
     *
     * @param e MethodArgumentNotValidException或BindException
     * @return Result
     */
    @ExceptionHandler({MethodArgumentNotValidException.class, BindException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Result<?> handleValidException(Exception e) {
        Class<?> cls = e.getClass();
        BindingResult result = null;
        if (MethodArgumentNotValidException.class.isAssignableFrom(cls)) {
            result = ((MethodArgumentNotValidException) e).getBindingResult();
        } else if (BindException.class.isAssignableFrom(cls)) {
            result = ((BindException) e).getBindingResult();
        }
        StringBuilder errorMsg = new StringBuilder();
        if (result!=null) {
            List<ObjectError> errors = result.getAllErrors();
            Iterator<ObjectError> iterator = errors.iterator();
            while (iterator.hasNext()) {
                ObjectError error = iterator.next();
                errorMsg.append(error.getDefaultMessage());
                if (iterator.hasNext()) {
                    errorMsg.append(",");
                }
            }
        }
        return new Result<>(HttpStatus.BAD_REQUEST.value(), errorMsg.toString(), null);
    }

    /**
     * 客户端异常
     *
     * @param e ClientException
     * @return Result
     */
    @ExceptionHandler({ClientException.class})
    public Result<?> handleClientException(ClientException e, HttpServletResponse response) {
        response.setStatus(e.getStatus());
        return new Result<>(e.getStatus(), e.getMessage(), null);
    }

    /**
     * 服务端异常
     *
     * @param e ServerException
     * @return Result
     */
    @ExceptionHandler({ServerException.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Result<?> handleServerException(ServerException e) {
        String dataMsg = null;
        if (e.getData() != null) {
            if (CharSequence.class.isAssignableFrom(e.getData().getClass())) {
                dataMsg = e.getData().toString();
            } else {
                dataMsg = JSON.toJSONString(e.getData(), SerializerFeature.PrettyFormat, SerializerFeature.WriteClassName);
            }
        }
        log.error(String.format("异常信息：%s，异常数据：%s", e.getMessage(), dataMsg), e);
        return new Result<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), "服务端异常！", null);
    }

    /**
     * 项目异常
     *
     * @param e ProjectException
     * @return Result
     */
    @ExceptionHandler({ProjectException.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Result<?> handleProjectException(ProjectException e) {
        log.error(e.getMessage(), e);
        return new Result<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), "服务端异常！", null);
    }

    /**
     * 其他异常
     *
     * @param e Throwable
     * @return Result
     */
    @ExceptionHandler({Throwable.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Result<?> handleThrowable(Throwable e) {
        log.error(e.getMessage(), e);
        return new Result<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), "服务端异常！", null);
    }
}
