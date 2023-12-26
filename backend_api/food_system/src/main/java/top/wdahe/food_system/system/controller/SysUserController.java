package top.wdahe.food_system.system.controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;
import top.wdahe.common.annotation.NeedPermission;
import top.wdahe.common.exception.ServiceException;
import top.wdahe.common.util.GeneratorUtil;
import top.wdahe.common.util.result.Result;
import top.wdahe.entity.SysUser;
import top.wdahe.food_system.entity.dto.SysDepartmentDTO;
import top.wdahe.food_system.entity.vo.SysUserVo;
import top.wdahe.food_system.system.mapper.RolePermissionMapper;
import top.wdahe.food_system.system.mapper.SysDepartmentMapper;
import top.wdahe.food_system.system.mapper.SysUserMapper;
import top.wdahe.food_system.system.service.SysDepartmentService;
import top.wdahe.food_system.system.service.SysUserService;

import java.util.List;


@Tag(name = "系统:系统管理员")
@RestController
@RequestMapping("/sysUser")
public class SysUserController {

    @Resource
    private SysUserMapper sysUserMapper;

    @Resource
    private RolePermissionMapper rolePermissionMapper;

    @Resource
    private SysDepartmentService sysDepartmentService;





    @Operation(summary = "查询全部")
    @NeedPermission("system:admin:sysUser:list")
    @GetMapping("/list")
    public Result<List<SysUserVo>> getAllSysUsers() throws ServiceException {
        return  Result.ok(sysUserMapper.getAllUserVOs());
    }

    @Operation(summary = "根据部门id查询")
    @NeedPermission("system:admin:sysUser:search")
    @GetMapping("/depUsers/{depId}")
    public Result<List<SysUserVo>> getDepSysUsers(@PathVariable Integer depId) throws ServiceException {
        return  Result.ok(sysUserMapper.getDepUserVOs(depId));
    }

    @Operation(summary = "系统用户新增")
    @NeedPermission("system:admin:sysUSer:add")
    @PostMapping("")
    public Result add(@RequestBody SysUser sysUser) throws  ServiceException {
        sysUser.setPassword(GeneratorUtil.md5Base64("123456"));
        return Result.ok(sysUserMapper.insert(sysUser));
    }

    @Operation(summary = "系统用户修改")
    @NeedPermission("system:admin:sysUser:update")
    @PutMapping("")
    public Result update(@RequestBody SysUser sysUser) throws  ServiceException {
        return Result.ok(sysUserMapper.updateById(sysUser));
    }

    @Operation(summary = "系统用户删除")
    @NeedPermission("system:admin:sysUser:delete")
    @DeleteMapping("/{sysUserId}")
    public Result deleteById(@RequestBody Integer sysUserId) throws ServiceException {
        if(rolePermissionMapper.selectPermissionByUserId(sysUserId).contains("*"))
            throw ServiceException.CONST_can_not_change_role_of_super_system_admin_user;
        return Result.ok(sysUserMapper.deleteById(sysUserId));
    }

    @Operation(summary = "更新账号的激活状态")
    @NeedPermission("system:admin:sysUser:status")
    @PutMapping("/status/{sysUserId}/{status}")
    public Result updateStatus(@Parameter(description = "sysUserId") @PathVariable Integer sysUserId,
                               @Parameter(description = "status") @PathVariable Boolean status)throws ServiceException {
        SysUser sysUser = new SysUser();

        sysUser.setId(sysUserId);
        sysUser.setStatus(status);
        return  Result.ok(sysUserMapper.updateById(sysUser));
    }

    @Operation(summary = "设置用户的角色")
    @NeedPermission("system:admin:sysUser:update")
    @PutMapping("/role/{userId}/{roleId}")
    public Result setRoleOfSysUser(@PathVariable Integer userId, @PathVariable Integer roleId) throws ServiceException {
        SysUser sysUser = new SysUser();

        sysUser.setId(userId);
        sysUser.setRoleId(roleId);
        return  Result.ok(sysUserMapper.updateById(sysUser));
    }
    @Operation(summary = "重设密码为初始化密码123456")
    @NeedPermission("system:admin:sysUser:resetPassword")
    @PutMapping("/resetPassword/{sysUserId}")
    public Result resetPassword(@Parameter(description = "sysUserId") @PathVariable Integer sysUserId) throws ServiceException {
        SysUser sysUser = new SysUser();

        sysUser.setId(sysUserId);
        sysUser.setPassword(GeneratorUtil.md5Base64("123456"));
        return  Result.ok(sysUserMapper.updateById(sysUser));
    }

    @Operation(summary = "部门列表")
    @NeedPermission("system:admin:sysUser:department")
    @GetMapping("/department")
    public Result<List<SysDepartmentDTO>> getDepartmentList() {
        //部门 上下级关系的,
        return Result.ok(sysDepartmentService.getDepartment());
    }


}
