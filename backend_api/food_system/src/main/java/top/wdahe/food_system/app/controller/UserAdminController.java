package top.wdahe.food_system.app.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;
import top.wdahe.common.annotation.NeedPermission;
import top.wdahe.common.util.result.Result;
import top.wdahe.entity.User;
import top.wdahe.food_system.app.service.UserAdminService;

import java.util.List;

@Tag(name = "系统:用户管理")
@RestController
@RequestMapping("/userAdmin")
public class UserAdminController {

    @Resource
    private UserAdminService userAdminService;

    @Operation(summary = "查询全部")
    @NeedPermission("system:user:list")
    @GetMapping("/list")
    public Result<List<User>> getAllUserAdmins() {
        return Result.ok(userAdminService.getAllUserAdmins());
    }


    @Operation(summary = "分页")
    @NeedPermission("system:user:list")
    @GetMapping("/page")
    public Result<Page<User>> getUserAdminByPage(@RequestParam(defaultValue = "1") int pageNo,
                                                 @RequestParam(defaultValue = "10") int pageSize) {
        return Result.ok(userAdminService.getUserAdminByPage(pageNo,pageSize));
    }

    @Operation(summary = "用户新增")
    @NeedPermission("system:user:add")
    @PostMapping
    public Result<Integer> add(@RequestBody User userAdmin) {
        return Result.ok(userAdminService.addUserAdmin(userAdmin));
    }

    @Operation(summary = "用户批量删除")
    @NeedPermission("system:user:delete")
    @DeleteMapping("/batch")
    public Result<Integer> delete(@RequestBody List<String> idList) {
        return Result.ok(userAdminService.deleteUserAdminBatchIds(idList));
    }

    @Operation(summary = "用户修改")
    @NeedPermission("system:user:update")
    @PutMapping
    public Result<Integer> update(@RequestBody User userAdmin) {
        return Result.ok(userAdminService.updateUserAdmin(userAdmin));
    }


}
