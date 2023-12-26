package top.wdahe.common.util.result;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

@Schema(description = "封装controller返回结果")
@Data
public class Result<T> implements Serializable {
    private int code;
    private String message;

    private T data;

    public Result(int code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public static  int CONST_ok = 200;

    public static  int CONST_fail = 400;

    public static <T> Result<T> ok() {
        return  new Result(CONST_ok, "成功", null);
    }

    public static <T> Result<T> ok(T result) {
        return  new Result(CONST_ok, "成功", result);
    }

    public static <T> Result<T> ok(String message,T data) {
        return  new Result(CONST_ok, message, data);
    }

    public static <T> Result<T> fail(String message) {
        return new Result(CONST_fail, message, null);
    }

    public static <T> Result<T> fail(String message,T data) {
        return new Result(CONST_fail, message, data);
    }

    public static <T> Result<T> newInstance(int code,String message) {return new Result(code,message,null);}


}
