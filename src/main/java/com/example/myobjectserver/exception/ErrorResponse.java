package com.example.myobjectserver.exception;

import com.example.myobjectserver.result.ResultCodeEnum;
import lombok.Data;

/**
 * @author 恒光
 * createTime:2024-12-01
 * version:1.0
 */
@Data
public class ErrorResponse{
    private Integer code;
    private String message;
    private Boolean success;

    public ErrorResponse(ResultCodeEnum resultCodeEnum) {
        this.code = resultCodeEnum.getCode();
        this.message = resultCodeEnum.getMessage();
        this.success = resultCodeEnum.getSuccess();
    }
    public ErrorResponse(Integer code, String message, Boolean success) {
        this.code = code;
        this.message = message;
        this.success = success;
    }
}
