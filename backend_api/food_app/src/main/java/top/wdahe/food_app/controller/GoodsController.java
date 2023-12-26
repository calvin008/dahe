package top.wdahe.food_app.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.wdahe.common.util.result.Result;
import top.wdahe.entity.vo.GoodsMenuVO;
import top.wdahe.food_app.service.impl.GoodsServiceImpl;

import java.util.List;

@RestController
@RequestMapping("/api-app/goods")
@Tag(name = "菜单")
public class GoodsController {

    @Resource
    private GoodsServiceImpl goodsService;

    @Operation(summary = "菜单列表")
    @GetMapping("/goodsMenu/list")
    public Result<List<GoodsMenuVO>> getGoodsMenuDetailList() {
        return Result.ok(goodsService.getGoodsMenuDetailList());
    }
}
