package top.wdahe.common.annotation;

import java.lang.annotation.*;

// 限流标识
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Documented
public @interface AccessLimiter {
    // eg: 5秒内10次
    int seconds() default 5;
    int maxCount() default 10;
    Type type() default Type.ENUM_ip;

    // 根据什么去限流
    public enum Type{
        ENUM_ip,
        ENUM_api
    }
}
