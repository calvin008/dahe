package top.wdahe.common.util.generator;

public class GeneratorUtil {

    public static  long generateExpireTime(long ttl) {
        return System.currentTimeMillis() + ttl * 1000;
    }
}
