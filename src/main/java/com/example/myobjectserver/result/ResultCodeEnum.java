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
    //资源问题从1000开始
    SUCCESS(true,20000,"成功"),

    FILE_ERROR(false,1000,"图片上传失败"),
    FILE_MAX_SIZE(false,1001,"图片超过服务器限制"),
    FILE_UNKNOWN(false,1002,"删除资源失败"),


    UNKNOWN_ERROR(false,20001,"服务器未知错误,请联系管理员"),
    REQUEST_METHOD_ERROR(false,20003,"请求方式错误"),
    MISSING_REQUEST_PARAMETER(false,20004,"缺少必要参数"),
    FIND_RESOURCE_ERROR(false,20005,"查找资源失败"),

    USER_ERROR(false,10000,"用户名或密码错误"),
    EXPIRED_JWT(false,10001,"令牌已过期，请重新登录"),
    NO_AUTH(false,10002,"暂无权限");
    private final Boolean success;
    private final Integer code;
    private final String message;

}
