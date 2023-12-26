package top.wdahe.common.util;

import com.baomidou.mybatisplus.core.toolkit.EncryptUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.atomic.AtomicInteger;

public class GeneratorUtil {
    private static final AtomicInteger orderIdCount = new AtomicInteger(0);

    private  static  final SimpleDateFormat CONST_order_id_format = new SimpleDateFormat("yyyyMMHHmmss");


    public static String generateOrderNo() {
        int i = orderIdCount.incrementAndGet() % 1000 + 1000;
        return CONST_order_id_format.format(new Date()) + "-" + i;
    }

    public static String md5Base64(String password) {
        return EncryptUtils.md5Base64(password);
    }

    public static  long generateExpireTime(long ttl) {
        return System.currentTimeMillis() + ttl * 1000;
    }

}
