package top.wdahe.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * 角色的权限(可以不止一个)
 * @TableName sys_role_permission
 */
@TableName(value ="sys_role_permission")
@Data
public class SysRolePermission implements Serializable {
    /**
     * 
     */
    @TableId
    private Integer roleId;

    /**
     * 权限 eg:system:user:add
     */

    private String permission;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}