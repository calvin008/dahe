package top.wdahe.food_app.controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import top.wdahe.common.util.result.Result;
import top.wdahe.entity.common.AppConfig;
import top.wdahe.service.AppConfigService;

@RestController
@Tag(name = "小程序配置")
public class AppConfigController {
    @Resource
    AppConfigService appConfigService;

    @GetMapping("/api-app/config")
    @Operation(description = "小程序配置查询")
    public Result<AppConfig> getAppConfig() {
        return Result.ok(appConfigService.getAppConfig());
    }
}
