package top.wdahe.food_system.entity.dto;

import lombok.Data;
import top.wdahe.entity.SysUser;

import java.util.Set;

@Data
public class SysUserDTO extends SysUser {
    private Integer sysUserId;
    private String roleName;

    private String token;

    private Set<String> permissions;
}
