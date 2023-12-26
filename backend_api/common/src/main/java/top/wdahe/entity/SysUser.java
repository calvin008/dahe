package top.wdahe.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * 
 * @TableName sys_user
 */
@TableName(value ="sys_user")
@Data
public class SysUser implements Serializable {
    /**
     * 
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 
     */
    private String username;

    /**
     * 
     */
    private String password;

    /**
     * 
     */
    private Integer roleId;

    /**
     * 1表示账号可用
     */
    private Boolean status;

    /**
     * 管理员头像
     */
    private String avatar;

    /**
     * 
     */
    private Integer depId;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}