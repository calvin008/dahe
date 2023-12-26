package top.wdahe.common.config.weixinProperty;


import lombok.Data;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
public class WeixinProperty {
    /*微信小程序参数*/

    private String appid = "wx77de7c18cadbf590";
    private String appSecret = "e242ed907cec7befd105de93072aa2f0";
    private String mchId = "1613123907"; // 个人商户号
    private String mchKey = "Wonghanqing626264642323231121231";
    private String notifyUrl = "https://3b83-119-123-202-212.ngrok-free.app/api-app/order/orderNotifyUrl"; // 微信支付回调地址


}
