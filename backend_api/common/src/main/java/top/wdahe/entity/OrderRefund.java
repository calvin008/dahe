package top.wdahe.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 订单退款信息
 * @TableName order_refund
 */
@TableName(value ="order_refund")
@Data
public class OrderRefund implements Serializable {
    /**
     * 
     */
    @TableId
    private String refundNo;

    /**
     * 微信支付退款号
     */
    private String wxRefundId;

    /**
     * 微信支付订单号
     */
    private String wxPayTransactionId;

    /**
     * 商户订单号
     */
    private String orderNo;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 成功退款时间
     */
    private Date successTime;

    /**
     * 退款状态: 成功，关闭，处理中，异常
     */
    private Object status;

    /**
     * 退款金额
     */
    private Integer refundFee;

    /**
     * 订单交易金额
     */
    private Integer orderPayPrice;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}