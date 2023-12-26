package top.wdahe.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 
 * @TableName user
 */
@TableName(value ="user")
@Data
public class User implements Serializable {
    /**
     * 微信的openid
     */
    @TableId
    private String wxOpenid;

    /**
     * 姓名
     */
    private String name;

    /**
     * 手机号
     */
    private String phone;

    /**
     * 地址信息
     */
    private String address;

    /**
     * 性别
     */
    private Byte sex;

    /**
     * 微信头像
     */
    private String wxAvatar;

    /**
     * 账号冻结状态
     */
    private Boolean status;

    /**
     * 
     */
    private String wxName;

    /**
     * 
     */
    private Integer integral;

    /**
     * 
     */
    private BigDecimal balance;

    /**
     * 
     */
    private Integer level;

    @TableField(exist = false)
    private String token;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

}