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
 * @TableName sys_department
 */
@TableName(value ="sys_department")
@Data
public class SysDepartment implements Serializable {
    /**
     * 部门id
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 
     */
    private Date createTime;

    /**
     * 
     */
    private Date updateTime;

    /**
     * 父级部门id
     */
    private Integer parentId;

    /**
     * 
     */
    private String name;

    /**
     * 显示顺序
     */
    private Integer orderNum;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}