package top.wdahe.entity.form;

import lombok.Data;

import java.util.Date;

@Data
public class CreateOrderForm {
    /**
     * 取餐方式
     */
    private Object takeType;

    /**
     * 收货地址
     */
    private String addressDetail;

    /**
     * 商品信息eg: 奶茶*2
     */
    private String goodsPreview;

    /**
     * 商品总数
     */
    private Integer goodsTotalNum;

    /**
     *
     */
    private Integer totalPrice;

    /**
     * 支付金额
     */
    private Integer payPrice;

    /**
     * 取单号，一般取手机尾号
     */
    private String verifyNum;

    /**
     * 用户备注、订单取消原因、或其他额外信息
     */
    private String extraInfo;

    /**
     * 下单时间
     */
    private Date createTime;

    /**
     * 用户联系电话
     */
    private String userPhone;

    /**
     * 取餐人
     */
    private String receiver;

    /**
     *
     */
    private String storeName;

    /**
     *
     */
    private Integer couponId;

}
