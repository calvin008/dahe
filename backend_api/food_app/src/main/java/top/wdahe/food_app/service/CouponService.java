package top.wdahe.food_app.service;

import top.wdahe.entity.Coupon;

import java.util.List;

public interface CouponService {
    List<Coupon> getUserCoupon(String wxOpenid);
}
