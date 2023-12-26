package top.wdahe.food_system.system.controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;
import top.wdahe.common.annotation.NeedPermission;
import top.wdahe.common.constant.Const;
import top.wdahe.common.exception.ServiceException;
import top.wdahe.common.util.result.Result;
import top.wdahe.entity.common.AppConfig;
import top.wdahe.service.AppConfigService;
import top.wdahe.service.RedisService;

@Tag(name = "系统:小程序配置")
@RestController
@RequestMapping("/config")
public class AppConfigController {

    @Resource
    private AppConfigService appConfigService;

    @Resource
    private RedisService redisService;

    @Operation(summary = "获取小程序的所有配置")
    @GetMapping
    public Result<AppConfig> getAppConfig() throws ServiceException {
            return Result.ok(appConfigService.getAppConfig());
    }

    @Operation(summary = "修改小程序配置信息")
    @NeedPermission("system:admin:config:update")
    @PutMapping
    public Result<AppConfig> updateAppConfig(@RequestBody AppConfig updateConfig)throws ServiceException {
        if(updateConfig.getBusinessStartTime() < 0 || updateConfig.getBusinessStartTime()>24 ||
        updateConfig.getBusinessEndTime() <0 || updateConfig.getBusinessEndTime() > 24)
            throw new ServiceException("营业开始时间和结束时间不合法,只能0-24之间");

        if(updateConfig.getBusinessStartTime() >= updateConfig.getBusinessEndTime())
            throw new ServiceException("营业开始时间必须在营业结束时间之前");


        redisService.set(Const.CONST_app_config,updateConfig);
        return getAppConfig();
    }
}
