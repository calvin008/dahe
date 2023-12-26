package top.wdahe.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 订单
 * @TableName order_info
 */
@TableName(value ="order_info")
@Data
public class OrderInfo implements Serializable {
    /**
     * 订单号
     */
    @TableId
    private String orderNo;

    /**
     * 
     */
    private String wxOpenid;

    /**
     * 订单状态
     */
    private String orderStatus;

    /**
     * 微信支付系统生成的订单号
     */
    private String wxPayTransactionId;

    /**
     * 取餐方式
     */
    private String takeType;

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
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date createTime;

    /**
     * 支付时间
     */
    private Date payTime;

    /**
     * 订单完成时间
     */
    private Date finishTime;

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
    private Integer storeId;

    /**
     * 
     */
    private Integer couponId;

    @TableField(exist = false)
    private String storeName;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}