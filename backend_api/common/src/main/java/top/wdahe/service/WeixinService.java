package top.wdahe.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.util.JSONPObject;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import top.wdahe.common.config.webConfig.WebConfig;
import top.wdahe.common.config.weixinProperty.WeixinProperty;

@Service
@Slf4j
public class WeixinService {
    @Resource
    WeixinProperty weixinProperty;

    public String getWeiXinOpenid(String code) {
            String urlStr = "https://api.weixin.qq.com/sns/jscode2session?appid=" + weixinProperty.getAppid()
                    + "&secret=" + weixinProperty.getAppSecret()
                    + "&grant_type=authorization_code&js_code=" + code;
            String html = WebConfig.getHtml(urlStr);
        JSONObject result = JSON.parseObject(html);
        Integer errcode = result.getInteger("errcode");
        if(errcode == null || errcode.equals(0)) {
            return result.getString("openid");

        } else  {
            log.error("[获取openid失败] 回复报文:",html);
            return null;
        }
    }
}
