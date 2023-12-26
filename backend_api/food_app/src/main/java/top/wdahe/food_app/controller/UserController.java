package top.wdahe.food_app.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.*;
import top.wdahe.common.annotation.NeedLogin;
import top.wdahe.common.exception.ServiceException;
import top.wdahe.common.util.result.Result;
import top.wdahe.entity.User;
import top.wdahe.entity.form.LoginByWeixinForm;
import top.wdahe.entity.form.UpdateUserForm;
import top.wdahe.food_app.service.impl.UserServiceImpl;
import top.wdahe.food_app.util.session.SessionUtil;

@Tag(name = "用户模块")
@RestController
@RequestMapping("/api-app/user")
public class UserController {

    @Resource
    UserServiceImpl userService;

    @Operation(summary = "微信小程序登录")
    @PostMapping("/login/weixin")
    public Result<User> loginByWx(@RequestBody LoginByWeixinForm form) throws ServiceException {

        return Result.ok(userService.loginByWeixin(form));
    }

    @Operation(summary = "获取用户资料")
    @NeedLogin
    @GetMapping("/info")
    public Result<User> getUserInfo(String wxOpenid) throws ServiceException {
        User user = userService.getUser(wxOpenid);
        SessionUtil.setUserSession(user);
        return Result.ok(user);
    }

    @Operation(summary = "修改用户资料")
    @NeedLogin
    @PutMapping
    public Result<Integer> updateUser(@Parameter(description = "用户资料") @RequestBody UpdateUserForm form, HttpServletRequest request) throws Exception {
        return Result.ok(userService.updateUser(form,SessionUtil.getCurrentWxOpenidFromRequest(request)));
    }

    @Operation(summary = "用户登出")
    @NeedLogin
    @DeleteMapping("/logout")
    public Result logout(HttpServletRequest request) {
        SessionUtil.logout(request);
        return Result.ok();
    }


}
