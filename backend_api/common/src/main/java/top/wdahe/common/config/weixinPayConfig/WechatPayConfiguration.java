package top.wdahe.common.config.weixinPayConfig;

import com.github.binarywang.wxpay.config.WxPayConfig;
import com.github.binarywang.wxpay.service.WxPayService;
import com.github.binarywang.wxpay.service.impl.WxPayServiceImpl;
import jakarta.annotation.Resource;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import top.wdahe.common.config.weixinProperty.WeixinProperty;

@Configuration
@ConditionalOnClass(WxPayService.class)
public class WechatPayConfiguration {
    @Resource
    private WeixinProperty weixinProperty;

    @Bean
    public WxPayService wxService() {
        WxPayConfig wxPayConfig = new WxPayConfig();
        wxPayConfig.setAppId(weixinProperty.getAppid());
        wxPayConfig.setMchId(weixinProperty.getMchId());
        wxPayConfig.setMchKey(weixinProperty.getMchKey());
        wxPayConfig.setNotifyUrl(weixinProperty.getNotifyUrl());

        WxPayService wxPayService = new WxPayServiceImpl();
        wxPayService.setConfig(wxPayConfig);
        return wxPayService;
    }
}
