package top.wdahe.food_system.entity.form;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "管理员登录表单")
public class SysUserLoginForm {
    private String username;

    private String password;

    private String uuid;

    private String verifyCode;

    private String ip;

}
