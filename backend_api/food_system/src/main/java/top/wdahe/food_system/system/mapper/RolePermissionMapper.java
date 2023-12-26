package top.wdahe.food_system.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;
import top.wdahe.entity.SysRolePermission;

import java.util.List;

public interface RolePermissionMapper extends BaseMapper<SysRolePermission> {

    @Select("select permission from sys_role_permission where role_id =#{param1} group by permission;")
    List<String> selectPermissionByRoleId(Integer roleId);

    @Select("select sys_role_permission.`permission` from sys_role_permission where sys_role_permission.role_id = \n" +
    "\t(select sys_user.`role_id` from sys_user where sys_user.id =#{param1});")
    List<String> selectPermissionByUserId(Integer userId);
}
