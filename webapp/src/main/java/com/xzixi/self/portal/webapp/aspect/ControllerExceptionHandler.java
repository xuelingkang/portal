package com.xzixi.self.portal.webapp.aspect;

import com.xzixi.self.portal.webapp.framework.exception.LogicException;
import com.xzixi.self.portal.webapp.framework.exception.ProjectException;
import com.xzixi.self.portal.webapp.framework.exception.ServerException;
import com.xzixi.self.portal.webapp.framework.model.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * 全局异常拦截
 *
 * @author 薛凌康
 */
@RestControllerAdvice
@Slf4j
public class ControllerExceptionHandler {

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
     * 业务逻辑异常
     *
     * @param e LogicException
     * @return Result
     */
    @ExceptionHandler({LogicException.class})
    public Result<?> handleLogicException(LogicException e, HttpServletResponse response) {
        response.setStatus(e.getStatus());
        return new Result<>(e.getStatus(), e.getMessage(), null);
    }

    /**
     * 服务器异常
     *
     * @param e ServerException
     * @return Result
     */
    @ExceptionHandler({ServerException.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Result<?> handleServerException(ServerException e) {
        log.error(e.getMessage(), e);
        return new Result<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage(), null);
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
        return new Result<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), "项目异常！", null);
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
        return new Result<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), "服务器异常！", null);
    }
}
