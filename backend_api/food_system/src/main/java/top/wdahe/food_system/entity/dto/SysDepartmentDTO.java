package top.wdahe.food_system.entity.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;
import java.util.List;

@TableName(value ="sys_department")
@Data
public class SysDepartmentDTO {
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
    @Accessors(chain = true)
    private List<SysDepartmentDTO> children;

}
