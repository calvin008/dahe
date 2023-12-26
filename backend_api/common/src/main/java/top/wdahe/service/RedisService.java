package top.wdahe.service;

import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
public class RedisService {

    @Resource
    private RedisTemplate redisTemplate;


    /*
    * 指定缓存失效时间
    * */
    public boolean expire(String key,long time) {
        try {
            if (time > 0 ) {
                redisTemplate.expire(key,time, TimeUnit.SECONDS);
            }
        } catch (Exception e) {
            log.error(e.getMessage(),e);
            return false;
        }

        return true;
    }

    public boolean expire(String key,long time,TimeUnit timeUnit) {
        try {
            if (time > 0 ) {
                redisTemplate.expire(key,time, timeUnit);
            }
        } catch (Exception e) {
            log.error(e.getMessage(),e);
            return false;
        }

        return true;
    }
    /*
    * 放入数据
    * */

    public boolean hset(String key, String item, Object value) {
        try {
            redisTemplate.opsForHash().put(key,item,value);
            return  true;
        } catch (Exception e) {
            log.error(e.getMessage(),e);
            return false;
        }

    }

    public boolean hset(String key, String item, Object value,long time) {
        try {
            redisTemplate.opsForHash().put(key,item,value);
           if(time >0) {
               expire(key,time);
           }

           return true;
        } catch (Exception e) {
            log.error(e.getMessage(),e);
            return false;
        }

    }

    public Object hget(String key,String item) {
            return redisTemplate.opsForHash().get(key,item);
    }

    //hHasKey查询用户是否登录
    public boolean hHasKey(String key,String item) {
        return redisTemplate.opsForHash().hasKey(key,item);
    }


    //从hash表中删除
    public boolean hdel(String key,Object... item) {
        return redisTemplate.opsForHash().delete(key,item) > 0;
    }

    public <T> boolean set(String key, T value) {
        try {

                redisTemplate.opsForValue().set(key,value);

            return  true;
        } catch (Exception e) {
            log.error(e.getMessage(),e);
            return  false;
        }
    }

    public <T> boolean set(String key, T value,long time) {
        try {
            if(time > 0) {
                redisTemplate.opsForValue().set(key,value,time,TimeUnit.SECONDS);

            }else {
                redisTemplate.opsForValue().set(key,value);
            }
            return  true;
        } catch (Exception e) {
            log.error(e.getMessage(),e);
            return  false;
        }
    }


    public Object get(String key) {
        return key == null ? null : redisTemplate.opsForValue().get(key);
    }

    public void del(String... keys) {
        if(keys != null && keys.length > 0) {
            if(keys.length == 1) {
                boolean result = redisTemplate.delete(keys[0]);
            } else {
                Set<Object> keySet = new HashSet<>();
                for (String key:keys) {
                    keySet.addAll(redisTemplate.keys(key));
                }

                long count = redisTemplate.delete(keySet);
            }

        }
    }


    public <T> Map<String ,T> hmget(String key) {
        return redisTemplate.opsForHash().entries(key);


    }

    public void  lRightPush(String key, String value) {
        redisTemplate.opsForList().rightPush(key,value);
    }
}
