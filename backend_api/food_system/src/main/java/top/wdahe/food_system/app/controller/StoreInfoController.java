package top.wdahe.food_system.app.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;
import top.wdahe.common.annotation.NeedPermission;
import top.wdahe.common.util.result.Result;
import top.wdahe.entity.StoreInfo;
import top.wdahe.entity.form.CreateStoreForm;
import top.wdahe.food_system.app.service.StoreInfoService;

import java.util.List;

@Tag(name = "系统:门店管理")
@RestController
@RequestMapping("/storeInfoAdmin")
public class StoreInfoController {

    @Resource
    private StoreInfoService storeInfoService;

    @Operation(summary = "门店查询")
    @NeedPermission("system:store:storeInfo:query")
    @GetMapping("/query")
    public Result<List<StoreInfo>> getStoreList() {
            return Result.ok(storeInfoService.getStoreList());
    }

    @Operation(summary = "门店分页查询")
    @NeedPermission("system:store:storeInfo:list")
    @GetMapping("/page")
    public Result<Page<StoreInfo>> getStoreINfoByPage(
            @RequestParam(defaultValue = "1") int pageNo,
            @RequestParam(defaultValue = "10") int pageSize
    ) {
        return Result.ok(storeInfoService.getStoreINfoByPage(pageNo,pageSize));
    }

    @Operation(summary = "添加门店")
    @NeedPermission("system:store:storeInfo:add")
    @PostMapping
    public Result<Integer> createStoreInfo(@RequestBody CreateStoreForm createStoreForm) {

        return storeInfoService.addStoreInfo(createStoreForm);

    }
    @Operation(summary = "删除门店")
    @NeedPermission("system:store:storeInfo:delete")
    @DeleteMapping("/delete/{store_id}")
    public Result<Integer> deleteStoreInfo(@PathVariable Integer store_id) {
        return Result.ok(storeInfoService.deleteStoreInfo(store_id));
    }

    @Operation(summary = "添加门店")
    @NeedPermission("system:store:storeInfo:add")
    @PutMapping("/update/")
    public Result<Integer> updateStoreInfo(@PathVariable Integer store_id, @RequestBody CreateStoreForm createStoreForm) {
            return Result.ok(storeInfoService.updateStoreInfo(store_id,createStoreForm));
    }

}
