package top.wdahe.food_system.system.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;
import top.wdahe.common.annotation.NeedPermission;
import top.wdahe.common.exception.ServiceException;
import top.wdahe.common.util.result.Result;
import top.wdahe.food_system.common.sysMenu.MenuUtils;
import top.wdahe.food_system.entity.dto.SysMenuDTO;

import top.wdahe.food_system.entity.vo.RouterVo;
import top.wdahe.food_system.system.mapper.SysMenuMapper;
import top.wdahe.food_system.system.service.SysMenuService;

import java.util.List;

@Tag(name = "系统:菜单管理")
@RestController
@RequestMapping("/sysMenu")
public class SysMenuController {
    @Resource
    private SysMenuMapper sysMenuMapper;

    @Resource
    private SysMenuService sysMenuService;

    @Operation(summary = "查询全部")
    @GetMapping("/list")
    public Result<List<SysMenuDTO>> getAllSysMenus() throws ServiceException {
        return  Result.ok(sysMenuService.getMenu());
    }

    @Operation(summary = "查询上级树状")
    @NeedPermission("system:admin:menu:listLevel")
    @GetMapping("/listLevel")
    public Result<List<SysMenuDTO>> getLevelSysMenus() throws ServiceException {
        return Result.ok(sysMenuService.getLevelMenu());
    }

    @Operation(summary = "新增菜单")
    @NeedPermission("system:admin:menu:add")
    @PostMapping("")
    public Result addMenu(@RequestBody SysMenuDTO sysMenu) throws ServiceException {
        return  Result.ok(sysMenuMapper.insert(sysMenu));
    }

    @Operation(summary = "批量删除菜单")
    @NeedPermission("system:admin:menu:delete")
    @DeleteMapping("/batch")
    public Result deleteMenu(@RequestBody List<Integer> sysMenuIdList) throws ServiceException {
        return Result.ok(sysMenuMapper.deleteBatchIds(sysMenuIdList));
    }

    @Operation(summary = "修改菜单")
    @NeedPermission("system:admin:menu:update")
    @PutMapping("updateMenu")
    public Result update(@RequestBody SysMenuDTO sysMenu) throws ServiceException {
        return Result.ok(sysMenuMapper.updateById(sysMenu));
    }

    @Operation(summary = "通过权限生成用户menu")
    @NeedPermission("system:admin:menu:list")
    @GetMapping("/selectUserMenu/{sysRoleId}")
    public  Result<List<RouterVo>> selectUserMenu(@PathVariable("sysRoleId") Integer sysRoleId) throws ServiceException {
        List<SysMenuDTO> list = sysMenuService.getUserRoleMenu(sysRoleId);
        return  Result.ok(MenuUtils.makeRouter(list,0));
    }

}
