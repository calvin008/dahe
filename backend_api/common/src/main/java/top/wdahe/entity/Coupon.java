package top.wdahe.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 
 * @TableName coupon
 */
@TableName(value ="coupon")
@Data
public class Coupon implements Serializable {
    /**
     * 
     */
    @TableId
    private Integer couponId;

    /**
     * 
     */
    private BigDecimal couponAmount;

    /**
     * 
     */
    private String wxOpenid;

    /**
     * 
     */
    private String couponInfo;

    /**
     * 
     */
    private String couponTitle;

    /**
     * 0为可用,1为已用,2为失效
     */
    private Integer couponStatus;

    /**
     * 
     */
    private Date endTime;

    /**
     * 
     */
    private BigDecimal couponLow;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}