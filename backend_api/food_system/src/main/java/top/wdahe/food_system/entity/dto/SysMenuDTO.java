package top.wdahe.food_system.entity.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;
import java.util.List;

@Schema(description = "后台菜单按钮管理")
@Data
@TableName("sys_menu")
public class SysMenuDTO {
    /**
     * 菜单ID
     */
    @TableId(type = IdType.AUTO)
    private Integer menuId;

    /**
     *
     */
    private Date createTime;

    /**
     *
     */
    private Date updateTime;

    /**
     * 父菜单ID
     */
    private Integer parentId;

    /**
     * 菜单名称
     */
    private String menuName;

    /**
     *
     */
    private String router;

    /**
     *
     */
    private String permission;

    /**
     *
     */
    private Integer type;

    /**
     *
     */
    private String icon;

    /**
     *
     */
    private Integer orderNum;

    /**
     *
     */
    private String viewPath;

    /**
     *
     */
    private Integer keepalive;

    /**
     *
     */
    private Integer isShow;

    /**
     *
     */
    private String title;

    @TableField(exist = false)
    @Accessors(chain = true)
    private List<SysMenuDTO> children;
}
