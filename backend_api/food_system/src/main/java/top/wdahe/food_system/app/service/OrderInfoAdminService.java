package top.wdahe.food_system.app.service;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.binarywang.wxpay.bean.result.WxPayOrderQueryResult;
import com.github.binarywang.wxpay.constant.WxPayConstants;
import com.github.binarywang.wxpay.exception.WxPayException;
import com.github.binarywang.wxpay.service.WxPayService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import top.wdahe.common.enums.OrderStatus;
import top.wdahe.common.enums.OrderTakeType;
import top.wdahe.common.exception.ServiceException;
import top.wdahe.entity.OrderInfo;
import top.wdahe.entity.dto.OrderQueryDTO;
import top.wdahe.food_system.app.mapper.OrderInfoAdminMapper;

import java.util.Date;

@Service
@Slf4j
public class OrderInfoAdminService {

    @Resource
    private OrderInfoAdminMapper orderInfoAdminMapper;

    @Resource
    private WxPayService wxPayService;


    public Page<OrderInfo> getOrderInfoAdminByPage(int pageNo, int pageSize,String orderStatus,int storeId) {
        Page<OrderInfo> page = new Page<>(pageNo,pageSize);
        if(StringUtils.isEmpty(orderStatus) ) {
            return orderInfoAdminMapper.selectPage(page,new QueryWrapper<OrderInfo>().eq("store_id",storeId).orderByDesc("create_time"));
        } else  {
            return orderInfoAdminMapper.selectPage(page,new QueryWrapper<OrderInfo>().eq("store_id",storeId).eq("order_status",orderStatus).orderByDesc("create_time"));
        }
    }


    public Page<OrderInfo> getOrderInfoList(int pageNo, int pageSize, OrderQueryDTO orderObj) {
        Page<OrderInfo> page = new Page<>(pageNo,pageSize);
        if(StringUtils.isEmpty(orderObj.getOrderNo())) {
            QueryWrapper<OrderInfo> queryWrapper = new QueryWrapper<OrderInfo>();
            String start = orderObj.getCreateTime();
            String end = orderObj.getFinishTime();
            queryWrapper.eq(StringUtils.isNotBlank(orderObj.getOrderStatus()),"order_status",orderObj.getOrderStatus()).eq("store_id",orderObj.getStoreId());
            queryWrapper.ge(StringUtils.isNotBlank(start),"date_format(create_time,'%Y-%m-%d %H:%i:%s')",start);
            queryWrapper.le(StringUtils.isNotBlank(end),"date_format(create_time,'%Y-%m-%d %H:%i:%s')",end);
            return orderInfoAdminMapper.selectPage(page,queryWrapper);
        } else {
            QueryWrapper<OrderInfo> queryWrapper = new QueryWrapper<OrderInfo>();
            queryWrapper.eq("order_no",orderObj.getOrderNo());
            return orderInfoAdminMapper.selectPage(page,queryWrapper);
        }
    }

    @Transactional(rollbackFor = Exception.class)
    public String toNextOrderStatus(String orderNo) throws ServiceException {
        OrderInfo orderInfo = orderInfoAdminMapper.selectById(orderNo);
        if(OrderStatus.ENUM_on_making.value.equalsIgnoreCase(orderInfo.getOrderStatus())) { //制作中
            if(OrderTakeType.ENUM_take_out.value.equals(orderInfo.getTakeType())) {//外卖
                orderInfo.setOrderStatus(OrderStatus.ENUM_on_sending.value);
            } else {
                orderInfo.setOrderStatus(OrderStatus.ENUM_please_take_meal.value);
            }

            orderInfo.setFinishTime(new Date());


        } else  if(OrderStatus.ENUM_on_sending.value.equalsIgnoreCase(orderInfo.getOrderStatus())) {
            orderInfo.setOrderStatus(OrderStatus.ENUM_has_received.value); //修改为已经送达
        } else  if(OrderStatus.ENUM_please_take_meal.value.equalsIgnoreCase(orderInfo.getOrderStatus())) {
            orderInfo.setOrderStatus(OrderStatus.ENUM_has_completed.value);
        }else  if(OrderStatus.ENUM_has_received.value.equalsIgnoreCase(orderInfo.getOrderStatus())) {
            orderInfo.setOrderStatus(OrderStatus.ENUM_has_completed.value); //修改为已经完成
            orderInfo.setFinishTime(new Date());
        } else {
            throw ServiceException.Const_current_order_status_can_not_change;
        }

        orderInfoAdminMapper.updateById(orderInfo);
        return orderInfo.getOrderStatus();


    }


    @Transactional(rollbackFor = Exception.class)
   public String cancelOrderNotPay (String orderNo) throws ServiceException {
        OrderInfo orderInfo = orderInfoAdminMapper.selectById(orderNo);
        if(orderInfo == null ){
            throw ServiceException.CONST_order_not_exist;
        }

        if(OrderStatus.ENUM_has_not_pay_money.value.equals(orderInfo.getOrderStatus())) {
            orderInfo.setOrderStatus(OrderStatus.ENUM_has_canceled.value);
            orderInfo.setExtraInfo("订单取消原因:[商家主动取消]");
            orderInfoAdminMapper.updateById(orderInfo);
            return "取消订单成功";
        }

        return "无法取消,用户已经完成支付";
   }


    public WxPayOrderQueryResult queryWeixinOrder(String orderNo) throws WxPayException {
        WxPayOrderQueryResult result = wxPayService.queryOrder(null,orderNo);
        String state = result.getTradeState();

        if(state.equalsIgnoreCase(WxPayConstants.WxpayTradeStatus.SUCCESS)) {
            orderInfoAdminMapper.updateOrderStatus(orderNo,result.getTransactionId(),OrderStatus.ENUM_on_making.value,result.getTotalFee(),result.getTimeEnd());
        } else  if(state.equalsIgnoreCase(WxPayConstants.WxpayTradeStatus.REFUND)) {
            orderInfoAdminMapper.updateOrderStatus(orderNo,result.getTransactionId(),OrderStatus.ENUM_on_refunding.value,result.getTotalFee(),result.getTimeEnd());
        } else  if(state.equalsIgnoreCase(WxPayConstants.WxpayTradeStatus.NOTPAY) || state.equalsIgnoreCase(WxPayConstants.WxpayTradeStatus.PAY_ERROR)) {
            orderInfoAdminMapper.updateOrderStatus(orderNo,result.getTransactionId(),OrderStatus.ENUM_has_not_pay_money.value,result.getTotalFee(),result.getTimeEnd());
        } else  if(state.equalsIgnoreCase(WxPayConstants.WxpayTradeStatus.CLOSED)) {
            orderInfoAdminMapper.updateOrderStatus(orderNo,result.getTransactionId(),OrderStatus.ENUM_has_canceled.value, result.getTotalFee(),result.getTimeEnd());
        }

        System.out.println("主动查询微信支付状态:" + state);
        return  result;
    }



}
