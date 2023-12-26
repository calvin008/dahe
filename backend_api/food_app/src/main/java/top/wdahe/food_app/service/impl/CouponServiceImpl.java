package top.wdahe.food_app.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import top.wdahe.entity.Coupon;
import top.wdahe.food_app.mapper.CouponMapper;
import top.wdahe.food_app.service.CouponService;

import java.util.List;


@Service
@Slf4j
public class CouponServiceImpl implements CouponService {

    @Resource
    private CouponMapper couponMapper;

    @Override
    public List<Coupon> getUserCoupon(String wxOpenid) {
        return couponMapper.selectList(
                new QueryWrapper<Coupon>().eq("wx_openid",wxOpenid).eq("coupon_status",0)
        );
    }
}
