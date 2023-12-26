package top.wdahe.service;

import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
@Slf4j
public class LockService {

    @Resource
    private RedisTemplate redisTemplate;


    //锁
    public boolean tryLock(String key, String value, long time) {
        try {
            return redisTemplate.opsForValue().setIfAbsent(key,value, Duration.ofSeconds(time));
        } catch (Exception e) {
            log.error(e.getMessage(),e);
            return false;
        }
    }


    //解锁
    public boolean releaseLock(String key) {
        return redisTemplate.delete(key);
    }
}
