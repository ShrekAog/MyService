package com.example.myobjectserver.result;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

/**
 * @author KORE
 */
@Getter
@AllArgsConstructor
@ToString
public enum ResultCodeEnum {

    SUCCESS(true,20000,"成功"),
    FILE_ERROR(false,30001,"图片上传失败"),
    FILE_UNKNOWN(false,30004,"未找到需要删除的文件"),
    UNKNOWN_ERROR(false,20001,"服务器未知错误,请联系管理员"),


    USER_ERROR(false,10000,"用户名或密码错误"),
    EXPIRED_JWT(false,10001,"令牌已过期，请重新登录"),
    NO_AUTH(false,10002,"暂无权限");
    private final Boolean success;
    private final Integer code;
    private final String message;

}
