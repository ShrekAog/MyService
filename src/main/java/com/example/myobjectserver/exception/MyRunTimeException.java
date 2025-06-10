package com.example.myobjectserver.exception;

import com.example.myobjectserver.result.ResultCodeEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @author 恒光
 * createTime:2025-04-02
 * version:1.0
 */
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public class MyRunTimeException extends RuntimeException{
    private Integer code;
    private String message;
    private Boolean success;
    public MyRunTimeException(ResultCodeEnum resultCodeEnum){
        this.code = resultCodeEnum.getCode();
        this.message = resultCodeEnum.getMessage();
        this.success = resultCodeEnum.getSuccess();
    }

    public MyRunTimeException(Exception e) {
        super(e);
    }
}
