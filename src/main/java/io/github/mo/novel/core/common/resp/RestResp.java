package io.github.mo.novel.core.common.resp;

import io.github.mo.novel.core.common.constant.ErrorCodeEnum;
import lombok.Getter;

import java.util.Objects;

/**
 * Http Rest 响应工具及数据格式封装
 *
 * @author mo
 * @date 2022/5/27 14:04
 */
@Getter
public class RestResp<T> {

    /**
     * 响应码
     */
    private String code;

    /**
     * 响应消息
     */
    private String message;

    /**
     * 响应数据
     */
    private T data;

    private  RestResp() {
        this.code = ErrorCodeEnum.OK.getCode();
        this.code = ErrorCodeEnum.OK.getMessage();
    }

    private RestResp(ErrorCodeEnum errorCode) {
        this.code = errorCode.getCode();
        this.message = errorCode.getMessage();
    }

    private  RestResp(T data){
        this.data = data;
    }

    /**
     * 业务处理成功，无数据返回
     */
    public static <T> RestResp<T> ok(T data) {
        return new RestResp<>(data);
    }

    /**
     * 业务处理失败
     */
    public static RestResp<Void> fail(ErrorCodeEnum errorCode) {
        return new RestResp<>(errorCode);
    }

    /**
     * 系统错误
     */
    public static RestResp<Void> error() {
        return new RestResp<>(ErrorCodeEnum.SYSTEM_ERROR);
    }

    /**
     * 判断是否成功
     */
    public boolean isOk() {
        return Objects.equals(this.code, ErrorCodeEnum.OK.getCode());
    }

}
