package top.wdahe.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * @TableName address
 */
@TableName(value ="address")
@Data
public class Address implements Serializable {
    /**
     * 
     */
    @TableId(type = IdType.AUTO)
    private Integer addressId;

    /**
     * 
     */
    private String phone;

    /**
     * 
     */
    private String wxOpenid;

    /**
     * 
     */
    private String addressContent;

    /**
     * 
     */
    private String name;

    /**
     * 
     */
    private Integer sex;

    /**
     * 
     */
    private Date createTime;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}