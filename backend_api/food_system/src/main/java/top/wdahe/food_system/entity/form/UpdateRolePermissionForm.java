package top.wdahe.food_system.entity.form;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

@Data
@Schema(description = "角色权限表单")
public class UpdateRolePermissionForm {
    private Integer roleId;

    private List<String> permissions;
}
