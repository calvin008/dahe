package top.wdahe.food_system.app.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;
import top.wdahe.common.annotation.NeedPermission;
import top.wdahe.common.enums.GoodsPropertyCategory;
import top.wdahe.common.util.result.Result;
import top.wdahe.entity.GoodsProperty;
import top.wdahe.food_system.app.mapper.GoodsAdminMapper;
import top.wdahe.food_system.app.mapper.GoodsPropertyAdminMapper;

import java.util.List;

@Tag(name = "系统:商品属性管理")
@RestController
@RequestMapping("/goodsPropertyAdmin")
public class GoodsPropertyAdminController {

    @Resource
    private GoodsAdminMapper goodsAdminMapper;

    @Resource
    private GoodsPropertyAdminMapper goodsPropertyAdminMapper;

    /**
     * 查询全部商品规格
     */
    @Operation(summary = "查询全部商品规格")
    @NeedPermission("system:app:goodsProperty:list")
    @GetMapping("/list")
    public Result<List<GoodsProperty>> getAllGoodsPropertyAdmins() {
        return Result.ok(goodsPropertyAdminMapper.selectList(
                new QueryWrapper<GoodsProperty>().orderByAsc("id")
        ));
    }

    @Operation(summary = "添加商品规格")
    @NeedPermission("system:app:goods:update")
    @PostMapping
    public Result<Integer> addGoodsProperty(@RequestBody GoodsProperty goodsProperty) {
        if(GoodsPropertyCategory.ENUM_Feeding.value.equals(goodsProperty.getCategory())) {
            goodsProperty.setIsDefault(false);
        } else {
            Long sameCategoryPropertyCount = goodsPropertyAdminMapper.selectCount(
                    new QueryWrapper<GoodsProperty>().eq("goods_id",goodsProperty.getGoodsId()).eq("category",goodsProperty.getCategory())
            );

            if(sameCategoryPropertyCount<=0) {
                goodsProperty.setIsDefault(true);
                if(GoodsPropertyCategory.ENUM_size.value.equals(goodsProperty.getCategory()))
                    goodsAdminMapper.updateGoodsDefaultPrice(goodsProperty.getGoodsId(),goodsProperty.getRebasePrice());
            } else
                goodsProperty.setIsDefault(false);
        }
        return Result.ok(goodsPropertyAdminMapper.insert(goodsProperty));
    }

    @Operation(summary = "修改商品属性")
    @NeedPermission("system:app:goods:update")
    @PutMapping
    public Result<Integer> updateGoodsCategory(@RequestBody GoodsProperty goodsProperty) {
        if(goodsProperty.getIsDefault() && GoodsPropertyCategory.ENUM_size.value.equals(goodsProperty.getCategory())) {
            goodsAdminMapper.updateGoodsDefaultPrice(goodsProperty.getGoodsId(),goodsProperty.getRebasePrice());
        } else {
            goodsProperty.setIsDefault(null);
        }

        return Result.ok(goodsPropertyAdminMapper.updateById(goodsProperty));
    }
    @Operation(summary = "删除商品属性")
    @NeedPermission("system:app:goods:update")
    @DeleteMapping("/{goodsPropertyId}")
    public Result<Integer> deleteGoodsCategory(@PathVariable Integer goodsPropertyId) {
        GoodsProperty goodsProperty = goodsPropertyAdminMapper.selectById(goodsPropertyId);
        //如果是默认属性或者加料类型就直接删除
        if(goodsProperty.getIsDefault() || GoodsPropertyCategory.ENUM_Feeding.equals(goodsProperty.getCategory()))
            return Result.ok(goodsPropertyAdminMapper.deleteById(goodsPropertyId));

        List<GoodsProperty> goodsPropertyList = goodsPropertyAdminMapper.selectList(
                new QueryWrapper<GoodsProperty>().eq("goods_id",goodsProperty.getGoodsId()).eq("category",goodsProperty.getCategory())
        );

        if(CollectionUtils.isEmpty(goodsPropertyList))
            return Result.ok(goodsPropertyAdminMapper.deleteById(goodsPropertyId));

        //设置一个默认属性
        GoodsProperty randomGoodsProperty = goodsPropertyList.get(0);
        randomGoodsProperty.setIsDefault(true);
        goodsPropertyAdminMapper.updateById(randomGoodsProperty);

        if(GoodsPropertyCategory.ENUM_size.value.equals(goodsProperty.getCategory()))
             goodsAdminMapper.updateGoodsDefaultPrice(goodsProperty.getGoodsId(),goodsProperty.getRebasePrice());

        return Result.ok(goodsPropertyAdminMapper.deleteById(goodsPropertyId));

    }
}
