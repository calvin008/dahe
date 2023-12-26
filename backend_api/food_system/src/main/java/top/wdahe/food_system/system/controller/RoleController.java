package top.wdahe.food_system.system.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;
import top.wdahe.common.annotation.NeedPermission;
import top.wdahe.common.exception.ServiceException;
import top.wdahe.common.util.result.Result;
import top.wdahe.entity.SysRole;
import top.wdahe.entity.SysRolePermission;
import top.wdahe.food_system.entity.form.UpdateRolePermissionForm;
import top.wdahe.food_system.system.mapper.RoleMapper;
import top.wdahe.food_system.system.mapper.RolePermissionMapper;
import top.wdahe.food_system.system.service.SysRoleService;

import java.util.List;
import java.util.Map;

@Tag(name = "系统:角色管理")
@RestController
@RequestMapping("/role")
public class RoleController {

    @Resource
    private RoleMapper roleMapper;

    @Resource
    private RolePermissionMapper rolePermissionMapper;

    @Resource
    private SysRoleService sysRoleService;

    @Operation(description = "查询全部")
    @NeedPermission("system:admin:role:list")
    @GetMapping("/list")
    public Result<List<SysRole>> getAllsysRoles() throws ServiceException {
        return Result.ok(roleMapper.selectList(null));
    }
    @Operation(description = "分页查询")
    @NeedPermission("system:admin:role:list")
    @GetMapping("/page/{pageNo}/{pageSize}")
    public Result page(@Parameter(description = "页数") @PathVariable Integer pageNo,
                       @Parameter(description = "页面大小") @PathVariable Integer pageSize,
                       @Parameter(description = "查询参数") @RequestParam Map<String,Object> params) throws Exception {
        return Result.ok(sysRoleService.listByPageAndCondition(pageNo,pageSize,params));
    }


    @Operation(description = "角色所有权限")
    @NeedPermission("system:admin:role:info")
    @GetMapping("/perms/{sysRoleId}")
    public Result detail(@PathVariable("sysRoleId") Integer sysRoleId) {
            return Result.ok(
                    rolePermissionMapper.selectObjs(new QueryWrapper<SysRolePermission>()
                            .select("permission").eq("role_id",sysRoleId))
            );
    }

    @Operation(description = "角色新增")
    @NeedPermission("system:admin:role:add")
    @PostMapping()
    public Result add(@RequestBody SysRole sysRole) throws ServiceException {
        return Result.ok(roleMapper.insert(sysRole));
    }

    @Operation(description = "角色修改")
    @NeedPermission("system:admin:role:update")
    @PutMapping()
    public Result update(@RequestBody SysRole sysRole) throws ServiceException {
        return Result.ok(roleMapper.updateById(sysRole));
    }

    @Operation(description = "角色批量删除")
    @NeedPermission("system:admin:role:delete")
    @DeleteMapping("/batch")
    public Result delete(@RequestBody List<Integer> sysRoleIdList) throws  ServiceException{
        return Result.ok(roleMapper.deleteBatchIds(sysRoleIdList));
    }

    @Operation(description = "修改角色权限")
    @NeedPermission("system:admin:role:update")
    @PutMapping("permission")
    public Result updateRolePermission(@Parameter(description = "角色权限表单") @RequestBody UpdateRolePermissionForm vo) throws Exception {
        try {
            sysRoleService.updateRolePermission(vo);
        } catch (ServiceException e) {
            return Result.fail("修改权限失败");
        }
        return Result.ok();
    }






}
