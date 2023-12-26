package top.wdahe.food_system.app.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import top.wdahe.common.annotation.NeedPermission;
import top.wdahe.common.exception.ServiceException;
import top.wdahe.common.util.result.Result;
import top.wdahe.entity.dto.GoodsDTO;
import top.wdahe.food_system.app.mapper.GoodsAdminMapper;
import top.wdahe.food_system.app.service.GoodsAdminService;

import java.util.List;

@Tag(name = "系统:商品管理")
@RestController
@RequestMapping("/goodsAdmin")
public class GoodsAdminController {

    @Resource
    private GoodsAdminMapper goodsAdminMapper;

    @Resource
    private GoodsAdminService goodsAdminService;

    @Operation(summary = "分页查询")
    @NeedPermission("system:goods:goodsInfo:list")
    @GetMapping("/page")
    public Result<Page<GoodsDTO>> getGoodsAdminByPage(@RequestParam(defaultValue = "1") int pageNo,
                                                      @RequestParam(defaultValue = "10") int pageSize) {
        return Result.ok(goodsAdminService.getGoodsAdminByPage(pageNo,pageSize));
    }

    @Operation(summary = "查询一个商品")
    @NeedPermission("system:goods:goodsInfo:list")
    @GetMapping("/{goodsId}")
    public Result<GoodsDTO> getGoodsById(@PathVariable Integer goodsId) {
        return Result.ok(goodsAdminService.getGoodsById(goodsId));
    }

    @Operation(summary = "商品新增")
    @NeedPermission("system:goods:goodsInfo:add")
    @PostMapping
    public Result<Integer> add(@RequestBody GoodsDTO goodsAdmin) {
        return Result.ok(goodsAdminService.addGoodsAdmin(goodsAdmin));
    }

    @Operation(summary = "商品批量删除")
    @NeedPermission("system:goods:goodsInfo:delete")
    @DeleteMapping("/batch")
    public Result<Integer> delete(@RequestBody List<Integer> idList) {
            return Result.ok(goodsAdminService.deleteGoodsAdminBatchIds(idList));
    }
    @Operation(summary = "商品修改")
    @NeedPermission("system:goods:goodsInfo:update")
    @PutMapping
    public Result<Integer> update(@RequestBody GoodsDTO goodsDTO) {
        return Result.ok(goodsAdminService.updateGoodsAdmin(goodsDTO));
    }

    @Operation(summary = "上架或者下架商品")
    @NeedPermission("system:goods:goodsInfo:update")
    @PutMapping("/updateSellStatus/{goodsId}")
    public Result<Integer> updateSellStatus(@PathVariable Integer goodsId) {
        return  Result.ok(goodsAdminMapper.updateSellStatus(goodsId));
    }

    @Operation(summary = "上传商品图片")
    @NeedPermission("system:goods:goodsInfo:update")
    @PostMapping("/image")
    public Result<String> uploadGoodsImage(@RequestParam("file")MultipartFile file) throws ServiceException {
        return Result.ok(goodsAdminService.uploadGoodsImage(file));
    }





}
