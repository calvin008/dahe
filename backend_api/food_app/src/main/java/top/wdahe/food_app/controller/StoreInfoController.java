package top.wdahe.food_app.controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.wdahe.common.util.result.Result;
import top.wdahe.entity.StoreInfo;
import top.wdahe.food_app.service.impl.StoreInfoServiceImpl;

import java.util.List;

@Tag(name = "门店管理")
@RestController
@RequestMapping("/api-app/store")
public class StoreInfoController {
    @Resource
    private StoreInfoServiceImpl storeInfoService;

    @Operation(description = "所有门店列表")
    @GetMapping("/query")
    public Result<List<StoreInfo>> getStoreInfo() {
        return Result.ok(storeInfoService.getStoreInfo());
    }
}
