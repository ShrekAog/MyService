package com.example.myobjectserver.exception;

import com.example.myobjectserver.result.ResultCodeEnum;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MultipartException;


/**
 * @author 恒光
 * createTime:2024-12-01
 * version:1.0
 */
@RestControllerAdvice
public class GlobalExceptionHandler {
    //日志打印
    private static  final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    /**
     * 捕获文件上传异常
     * @param ex multipartException
     * @return errorException
     */
    @ExceptionHandler(MultipartException.class)
    public ResponseEntity<ErrorResponse> handleMultipartException(MultipartException ex){
        logger.error("发生文件上传异常，原因是:{}",ex.getMessage());
        ErrorResponse errorResponse = new ErrorResponse(ResultCodeEnum.FILE_ERROR);
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * 自定义异常
     * @param ex MyRunTimeException
     * @return ErrorResponse
     */
    @ExceptionHandler(MyRunTimeException.class)
    public ErrorResponse handleMyRunTimeException(MyRunTimeException ex){
        System.out.println("6666666666666");
        logger.error("发生自定义异常,原因是:{}",ex.getMessage());
        return new ErrorResponse(ex.getCode(),ex.getMessage(),ex.getSuccess());
    }

    /**
     * spring security 登录异常
     * @param ex BadCredentialsException
     * @return ResultCodeEnum.USER_ERROR
     */
    @ExceptionHandler(BadCredentialsException.class)
    public ErrorResponse handleBadCredentialsException(BadCredentialsException ex){
        logger.error("发生登录异常,原因是:{}",ex.getMessage());
        return new ErrorResponse(ResultCodeEnum.USER_ERROR);
    }

    /**
     * JWT过期
     * @param ex ExpiredJwtException
     * @return ErrorResponse
     */
    @ExceptionHandler(ExpiredJwtException.class)
    public ErrorResponse handleExpiredJwtException(ExpiredJwtException ex){
        logger.error("令牌已过期,原因是:{}",ex.getMessage());
        return new ErrorResponse(ResultCodeEnum.EXPIRED_JWT);
    }

    /**
     * 令牌错误
     * @param ex MalformedJwtException
     * @return ResultCodeEnum.EXPIRED_JWT
     */
    @ExceptionHandler(MalformedJwtException.class)
    public ErrorResponse handleMalformedJwtException(MalformedJwtException ex){
        logger.error("令牌错误,原因是:{}",ex.getMessage());
        return new ErrorResponse(ResultCodeEnum.EXPIRED_JWT);
    }


    /**
     * 捕获所有服务器异常
     * @param ex exception
     * @return errorException
     */
    @ExceptionHandler(Exception.class)
    public ErrorResponse handleAllException(Exception ex){
        logger.error("发生服务器异常,原因是:{}",ex.getMessage());
        return new ErrorResponse(ResultCodeEnum.UNKNOWN_ERROR);
    }



}
