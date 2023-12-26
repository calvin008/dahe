package top.wdahe.food_app.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.binarywang.wxpay.bean.request.WxPayUnifiedOrderRequest;
import com.github.binarywang.wxpay.constant.WxPayConstants;
import com.github.binarywang.wxpay.exception.WxPayException;
import com.github.binarywang.wxpay.service.WxPayService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import top.wdahe.common.config.weixinProperty.WeixinProperty;
import top.wdahe.common.constant.Const;
import top.wdahe.common.enums.OrderStatus;
import top.wdahe.common.enums.OrderTakeType;
import top.wdahe.common.exception.ServiceException;
import top.wdahe.common.util.GeneratorUtil;
import top.wdahe.common.util.result.Result;
import top.wdahe.entity.Coupon;
import top.wdahe.entity.OrderInfo;
import top.wdahe.entity.StoreInfo;
import top.wdahe.entity.common.AppConfig;
import top.wdahe.entity.form.CreateOrderForm;
import top.wdahe.entity.vo.HistoryOrderVo;
import top.wdahe.food_app.mapper.CouponMapper;
import top.wdahe.food_app.mapper.OrderMapper;
import top.wdahe.food_app.mapper.StoreInfoMapper;
import top.wdahe.food_app.service.OrderService;
import top.wdahe.service.AppConfigService;
import top.wdahe.service.LockService;
import top.wdahe.service.RedisService;

import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@Slf4j
public class OrderServiceImpl implements OrderService {

    @Resource
    private RedisService redisService;
    @Resource
    private OrderMapper orderMapper;

    @Resource
    private AppConfigService appConfigService;

    @Resource
    private LockService lockService;

    @Resource
    private StoreInfoMapper storeInfoMapper;

    @Resource
    private CouponMapper couponMapper;

    @Resource
    private WeixinProperty weixinProperty;

    @Resource
    private WxPayService wxPayService;

    //创建订单
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result<String> createOrder(CreateOrderForm orderForm, String wxOpenid) {
        //订单验证
        if(orderForm.getTotalPrice() < 0) {
            return Result.fail("订单价格异常");
        }

        if(orderForm.getGoodsTotalNum() <=0) {
            return Result.fail("不能生成空订单,请选择商品");
        }

        if(OrderTakeType.ENUM_take_out.value.equals(orderForm.getTakeType())) {
                if(StringUtils.isEmpty(orderForm.getAddressDetail())
                || orderForm.getAddressDetail().trim().length() < 3
                || StringUtils.isEmpty(orderForm.getUserPhone())
                || StringUtils.isEmpty(orderForm.getReceiver()))
                    return Result.fail("收货信息错误,请检查收货地址和手机姓名");
        }
        AppConfig appConfig = appConfigService.getAppConfig();
        if(!appConfig.getShopStatus()){
            return  Result.fail("商家休息中,下单失败");
        }

        Date currentTime = new Date();
        int nowHour = LocalTime.now(ZoneId.of("Asia/Shanghai")).getHour();
        if(!(nowHour >= appConfig.getBusinessStartTime() && nowHour < appConfig.getBusinessEndTime()))
            return Result.fail("未到营业时间,下单失败");

        //redis锁,确保只有一个线程在处理订单,避免重复提交
        if(!lockService.tryLock(Const.CONST_lock_redis_prefix + wxOpenid,"",15))
                    return Result.fail("正在下单,请勿重复下单");


        try{
            //添加数据库
            OrderInfo orderInfo = new OrderInfo();
            StoreInfo storeInfo = storeInfoMapper.selectOne(new QueryWrapper<StoreInfo>().eq("store_name",orderForm.getStoreName()));
            BeanUtils.copyProperties(orderForm,orderInfo);
            orderInfo.setWxOpenid(wxOpenid);
            orderInfo.setOrderNo(GeneratorUtil.generateOrderNo());
            orderInfo.setOrderStatus(OrderStatus.ENUM_has_not_pay_money.value);
            orderInfo.setCreateTime(currentTime);
            orderInfo.setGoodsPreview(orderForm.getGoodsPreview());
            orderInfo.setGoodsTotalNum(orderForm.getGoodsTotalNum());
            orderInfo.setTotalPrice(orderForm.getTotalPrice());
            orderInfo.setStoreId(storeInfo.getStoreId());
            orderInfo.setReceiver(orderForm.getReceiver());
            orderInfo.setPayPrice(null);
            //判断用户是否使用优惠卷
            if(StringUtils.isEmpty(orderForm.getCouponId())) {
                orderInfo.setCouponId(null);
            } else {
                UpdateWrapper<Coupon> wrapper = new UpdateWrapper<>();
                wrapper.eq("coupon_id",orderForm.getCouponId()).set("coupon_status",1);
                couponMapper.update(null,wrapper);
                orderInfo.setCouponId(orderForm.getCouponId());
            }

            orderMapper.insert(orderInfo);
            return Result.ok("下单成功",orderInfo.getOrderNo());

        } finally {
            lockService.releaseLock(Const.CONST_lock_redis_prefix + wxOpenid);
        }

    }

    @Override
    public Object wxPrepay(String wxOpenid, String orderNo, String ip) throws ServiceException {
        Integer totalPrice = orderMapper.getOrderTotalPriceByOrderNo(orderNo);
        try {
            WxPayUnifiedOrderRequest orderRequest = new WxPayUnifiedOrderRequest();
            orderRequest.setAppid(weixinProperty.getAppid());
            orderRequest.setMchId(weixinProperty.getMchId());
            orderRequest.setOutTradeNo(orderNo);
            orderRequest.setBody("大合家");
            Date expireDate = new Date(System.currentTimeMillis() + 24* 3600 * 1000);
            orderRequest.setTimeExpire(new SimpleDateFormat("yyyyMMddHHmmss").format(expireDate));
            orderRequest.setTotalFee(totalPrice);
            orderRequest.setOpenid(wxOpenid);
            orderRequest.setSpbillCreateIp(ip);
            orderRequest.setTradeType(WxPayConstants.TradeType.JSAPI);
            return wxPayService.createOrder(orderRequest);

        } catch (WxPayException e) {
            log.error("[微信支付]异常",e);
            throw ServiceException.CONST_weixin_pay_exception;
        } catch (Exception e) {
            log.error("[微信预支付]异常",e);
            throw ServiceException.CONST_weixin_pay_exception;
        }
    }

    @Override
    public List<OrderInfo> getHandlingOrders(String wxOpenid) throws ServiceException {
       try {
           List<String> params = new ArrayList<>();
           params.add(OrderStatus.ENUM_has_not_pay_money.value);
           params.add(OrderStatus.ENUM_has_canceled.value);
           params.add(OrderStatus.ENUM_on_refunding.value);
           params.add(OrderStatus.ENUM_has_completed.value);
           return orderMapper.getHandlingOrders(wxOpenid, params);
       } catch (Exception e) {
           log.error("查询进行中订单异常:",e);
           throw  ServiceException.CONST_order_handling_exception;
       }
    }

    @Override
    public Page<HistoryOrderVo> getHistoryOrderByPage(Integer pageNo, Integer pageSize, String wxOpenid) throws ServiceException {
        try {
            Page<HistoryOrderVo> page = new Page<>(pageNo,pageSize);
            page.setRecords(orderMapper.getHistoryOrderByPage(wxOpenid,(pageNo - 1)* pageSize,pageSize));
            page.setTotal(orderMapper.getHistoryOrderTotalCount(wxOpenid));
            return page;
        } catch (Exception e) {
            log.error("查询用户历史订单异常:",e);
            throw  ServiceException.CONST_order_page_exception;
        }
    }


    @Override
    public OrderInfo getOrderDetail(String orderNo)throws ServiceException {

       try {
           return orderMapper.getOrderDetail(orderNo);
       } catch (Exception e) {
           log.error("查询用户订单详情异常:",e);
           throw ServiceException.CONST_order_detail_exception;
       }
    }

}
