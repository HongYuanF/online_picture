package com.hongyuan.onlinepic.exception;

import com.hongyuan.onlinepic.util.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolationException;

/**
 * @className: GlobalExceptionHandler
 * @author: HongYuan
 * @date: 2023/3/10
 **/
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {


    @ResponseBody
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Result handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        log.error(e.getMessage(), e);
        System.out.println(e.getBindingResult().getAllErrors().get(0).getDefaultMessage());
        return Result.fail(e.getBindingResult().getAllErrors().get(0).getDefaultMessage());
    }

    @ResponseBody
    @ExceptionHandler(ConstraintViolationException.class)
    public Result handleConstraintViolationException(ConstraintViolationException e) {
        log.error(e.getMessage(), e);
        return Result.fail(e.getMessage());
    }
}
