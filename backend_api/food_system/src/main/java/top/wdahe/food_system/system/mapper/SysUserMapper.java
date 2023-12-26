package top.wdahe.food_system.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import top.wdahe.entity.SysUser;
import top.wdahe.food_system.entity.vo.SysUserVo;

import java.util.List;


public interface SysUserMapper extends BaseMapper<SysUser> {


    @Select("select  sys_user.id, sys_user.`username`,sys_user.`status`,sys_user.`dep_id`, \n" +
    "sys_role.`id` as roleId, sys_role.`name` as roleName from sys_user \n" +
    " left join sys_role on sys_user.`role_id` = sys_role.`id`;")
        List<SysUserVo> getAllUserVOs();


    @Select("select  sys_user.id, sys_user.`username`,sys_user.`status`,sys_user.`dep_id`, \n" +
            "sys_role.`id` as roleId, sys_role.`name` as roleName from sys_user \n" +
            " left join sys_role on sys_user.`role_id` = sys_role.`id` \n" +
            "WHERE sys_user.dep_id = #{departmentId} ;")
    List<SysUserVo> getDepUserVOs(@Param("departmentId") Integer departmentId);
}
