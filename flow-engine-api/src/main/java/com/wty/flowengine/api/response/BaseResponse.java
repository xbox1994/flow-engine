package com.wty.flowengine.api.response;

import lombok.Data;

@Data
public class BaseResponse<T> {

    /**
     * 0 表示成功，其它表示异常
     */
    private int code;

    /**
     * "success" 表示成功，其它表示具体的异常信息
     */
    private String msg;

    /**
     * 具体的返回数据内容
     */
    private T body;

    public BaseResponse(int code, String msg, T body) {
        this.code = code;
        this.msg = msg;
        this.body = body;
    }

    public static <T> BaseResponse<T> ok(T body) {
        return new BaseResponse<>(0, "success", body);
    }

    public static <T> BaseResponse<T> fail(int code, String msg) {
        return new BaseResponse<>(code, msg, null);
    }


}
