package top.wdahe.food_system.entity.vo;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema(description = "视图层系统用户对象")
@Data
public class SysUserVo {

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
    private Integer roleId;

    private String roleName;

    /**
     * 1表示账号可用
     */
    @Schema(description = "账号激活状态")
    private Boolean status;



    /**
     *
     */
    private Integer depId;
}
