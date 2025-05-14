package com.example.myobjectserver.result;

import com.example.myobjectserver.exception.ErrorResponse;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author KORE
 */
@Data
@NoArgsConstructor
public class R<T> {
    private Boolean success;
    private Integer code;
    private String message;
    private T data;

    public R(Boolean success, Integer code, String message, T data) {
        this.success = success;
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public R(ResultCodeEnum resultCodeEnum) {
        this.success = resultCodeEnum.getSuccess();
        this.code = resultCodeEnum.getCode();
        this.message = resultCodeEnum.getMessage();
    }

    public R(ResultCodeEnum resultCodeEnum,T data) {
        this.success = resultCodeEnum.getSuccess();
        this.code = resultCodeEnum.getCode();
        this.message = resultCodeEnum.getMessage();
        this.data = data;
    }

    public R(ErrorResponse response){
        this.setCode(response.getCode());
        this.setMessage(response.getMessage());
    }

    public static R<Void> ok(){
        return new R<>(ResultCodeEnum.SUCCESS);
    }
    public static <T> R<T> ok(T data){
        return new R<>(ResultCodeEnum.SUCCESS,data);
    }
    public static R<Void> ok(Integer code,String message){
        return new R<>(false,code,message,null);
    }
    public static R<Void> error(Integer code, String message){
        return new R<>(false,code,message,null);
    }

    public static <T> R<T> error(ResultCodeEnum resultCodeEnum){
        return new R<>(resultCodeEnum);
    }
}
