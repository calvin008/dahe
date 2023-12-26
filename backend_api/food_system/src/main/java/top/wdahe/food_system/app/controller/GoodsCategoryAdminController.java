package top.wdahe.food_system.app.controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;
import top.wdahe.common.annotation.NeedPermission;
import top.wdahe.common.util.result.Result;
import top.wdahe.entity.GoodsCategory;
import top.wdahe.food_system.app.mapper.GoodsCategoryAdminMapper;
import top.wdahe.food_system.app.service.GoodsCategoryAdminService;

import java.util.List;

@Tag(name = "系统:商品类目管理")
@RestController
@RequestMapping("/goodsCategoryAdmin")
public class GoodsCategoryAdminController {

        @Resource
        private GoodsCategoryAdminService goodsCategoryAdminService;

        @Resource
        private GoodsCategoryAdminMapper goodsCategoryAdminMapper;

        @Operation(summary = "查询全部")
        @NeedPermission("system:goods:goodsCategory:list")
        @GetMapping("/list")
        public Result<List<GoodsCategory>> getAllGoodsCategoryAdmins() {
            return Result.ok(goodsCategoryAdminService.getAllGoodsCategoryAdmins());
        }

        @Operation(summary = "商品类目增加")
        @NeedPermission("system:goods:goodsCategory:add")
        @PostMapping("")
        public Result<Integer> add(@RequestBody GoodsCategory goodsCategoryAdmin) {
                return Result.ok(goodsCategoryAdminService.addGoodsCategoryAdmin(goodsCategoryAdmin));
        }

        @Operation(summary = "商品类目删除")
        @NeedPermission("system:goods:goodsCategory:delete")
        @DeleteMapping("/{categoryName}")
        public Result<Integer> delete(@PathVariable String categoryName) {
                return Result.ok(goodsCategoryAdminService.deleteGoodsCategoryAdminByName(categoryName));
        }

        @Operation(summary = "商品类目修改")
        @NeedPermission("system:goods:goodsCategory:update")
        @PutMapping("/{oldName}")
        public Result<Integer> update(@PathVariable String oldName,@RequestBody GoodsCategory goodsCategoryAdmin) {
                return Result.ok(goodsCategoryAdminService.updateGoodsCategoryAdmin(oldName,goodsCategoryAdmin));
        }




        @Operation(summary = "显示或者隐藏类目")
        @NeedPermission("system:goods:goodsCategory:update")
        @PutMapping("/showStatus/{name}")
        public Result<Integer> updateCategoryShowStatus(@PathVariable String name) {
                return Result.ok(goodsCategoryAdminMapper.updateCategoryShowStatus(name));
        }





}
