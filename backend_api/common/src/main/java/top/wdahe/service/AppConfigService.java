package top.wdahe.service;

import jakarta.annotation.Resource;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import top.wdahe.common.constant.Const;
import top.wdahe.entity.common.AppConfig;

@Service
public class AppConfigService {
    @Resource
    RedisTemplate redisTemplate;

    public AppConfig getAppConfig() {
        //判断redis是否有配置缓存
        Object o = redisTemplate.opsForValue().get(Const.CONST_app_config);
        if(o!= null) {
            return (AppConfig) o;
        }

        AppConfig appConfig = new AppConfig();
        redisTemplate.opsForValue().set(Const.CONST_app_config,appConfig);
        return appConfig;
    }
}
