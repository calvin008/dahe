package top.wdahe.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 
 * @TableName store_info
 */
@TableName(value ="store_info")
@Data
public class StoreInfo implements Serializable {
    /**
     * 
     */
    @TableId(type = IdType.AUTO)
    private Integer storeId;

    /**
     * 
     */
    private Date createTime;

    /**
     * 
     */
    private Date updateTime;

    /**
     * 
     */
    private String storeName;

    /**
     * 
     */
    private BigDecimal storeLatitude;

    /**
     * 
     */
    private BigDecimal storeLongitude;

    /**
     * 
     */
    private String storeCity;

    /**
     * 
     */
    private String storeMobile;

    /**
     * 
     */
    private String address;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}