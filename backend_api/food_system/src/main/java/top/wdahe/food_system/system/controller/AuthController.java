package top.wdahe.food_system.system.controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.*;
import top.wdahe.common.annotation.NeedPermission;
import top.wdahe.common.util.result.Result;
import top.wdahe.food_system.common.session.SessionUtil;
import top.wdahe.food_system.entity.dto.SysUserDTO;
import top.wdahe.food_system.entity.form.SysUserLoginForm;
import top.wdahe.food_system.system.service.SysUserService;

@Tag(name = "系统,后台认证授权")
@RestController
@RequestMapping("/auth")
public class AuthController {

    @Resource
    private SysUserService sysUserService;

    @Operation(summary = "系统用户登录")
    @PostMapping("/login")
    public Result<SysUserDTO> login(@RequestBody SysUserLoginForm sysUserLoginVo, HttpServletRequest request) throws Exception {
        return Result.ok(sysUserService.login(sysUserLoginVo,request));
    }

    @Operation(summary = "系统用户token登录")
    @PostMapping("/loginByToken")
    @NeedPermission
    public Result<SysUserDTO> loginByToken(HttpServletRequest request) throws Exception{
        return Result.ok(sysUserService.loginByToken(request));
    }

    @Operation(summary = "获取用户信息")
    @NeedPermission
    @GetMapping("/userInfo")
    public Result<SysUserDTO> getLoginUserInfo(HttpServletRequest request) throws Exception {
            return Result.ok(SessionUtil.getCurrentSysUserDTO(request));
    }

    @Operation(summary = "退出登录")
    @DeleteMapping("/logout")
    public Result logout(HttpServletRequest request) {
        return Result.ok(SessionUtil.logout(request));
    }





}
