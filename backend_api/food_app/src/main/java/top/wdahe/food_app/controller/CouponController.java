package top.wdahe.food_app.controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.wdahe.common.annotation.NeedLogin;
import top.wdahe.common.exception.ServiceException;
import top.wdahe.common.util.result.Result;
import top.wdahe.entity.Coupon;
import top.wdahe.food_app.service.impl.CouponServiceImpl;

import java.util.List;

@Tag(name = "优惠卷服务")
@RestController
@RequestMapping("/api-app/coupon")
public class CouponController {

    @Resource
    private CouponServiceImpl couponService;

    @Operation(summary = "用户优惠卷列表")
    @NeedLogin
    @GetMapping("/list/{wxOpenid}")
    public Result<List<Coupon>> getUserCoupon(@PathVariable String wxOpenid) throws ServiceException {
        return Result.ok(couponService.getUserCoupon(wxOpenid));
    }
}
