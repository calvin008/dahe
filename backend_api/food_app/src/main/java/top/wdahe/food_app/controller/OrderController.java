package top.wdahe.food_app.controller;


import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.binarywang.wxpay.bean.notify.WxPayNotifyResponse;
import com.github.binarywang.wxpay.bean.notify.WxPayOrderNotifyResult;
import com.github.binarywang.wxpay.service.WxPayService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.web.bind.annotation.*;
import top.wdahe.common.annotation.NeedLogin;
import top.wdahe.common.enums.OrderStatus;
import top.wdahe.common.exception.ServiceException;
import top.wdahe.common.util.QRcodeUtil.QRCodeUtil;
import top.wdahe.common.util.ip.IpUtil;
import top.wdahe.common.util.queueUtil.QueueUtil;
import top.wdahe.common.util.result.Result;
import top.wdahe.entity.OrderInfo;
import top.wdahe.entity.StoreInfo;
import top.wdahe.entity.form.CreateOrderForm;
import top.wdahe.entity.vo.HistoryOrderVo;
import top.wdahe.food_app.mapper.OrderMapper;
import top.wdahe.food_app.service.impl.OrderServiceImpl;
import top.wdahe.food_app.util.session.SessionUtil;
import top.wdahe.service.OrderMessageService;
import top.wdahe.service.OrderWebSocketHandler;

import java.io.IOException;
import java.util.List;


@Tag(name = "订单服务")
@RestController
@Slf4j
@RequestMapping("/api-app/order")
public class OrderController {

    @Resource
    private OrderServiceImpl orderService;

    @Resource
    private WxPayService wxPayService;

    @Resource
    private OrderMapper orderMapper;

    @Resource
    private QueueUtil queueUtil;

    @Resource
    private OrderWebSocketHandler orderWebSocketHandler;

    @Resource
    private OrderMessageService orderMessageService;

    @Operation(summary = "下订单")
    @NeedLogin
    @PostMapping
    public Result<String> createOrder(@RequestBody CreateOrderForm orderForm, HttpServletRequest request) throws ServiceException {
        return orderService.createOrder(orderForm, SessionUtil.getCurrentWxOpenidFromRequest(request));
    }

    @Operation(summary = "微信小程序预支付")
    @NeedLogin
    @PostMapping("/pay/weixin")
    public Result wxPrepay(String orderNo,HttpServletRequest request) throws ServiceException {
        return Result.ok(orderService.wxPrepay(SessionUtil.getCurrentWxOpenidFromRequest(request),orderNo, IpUtil.getIp(request)));
    }


    /**
     * 回调,接收微信支付消息
     */
    @PostMapping("/orderNotifyUrl")
    public String parseOrderNotifyResult(HttpServletRequest request) {
        try {
            String xmlResult = IOUtils.toString(request.getInputStream(),request.getCharacterEncoding());
            WxPayOrderNotifyResult result = wxPayService.parseOrderNotifyResult(xmlResult);
            String orderNo = result.getOutTradeNo();
            if(!"SUCCESS".equalsIgnoreCase(result.getReturnCode()) || !"SUCCESS".equalsIgnoreCase(result.getResultCode()))
                return WxPayNotifyResponse.fail("订单" + orderNo + "处理失败");


            //给用户生成取餐号
            /**
             * 加入到redis队列中,是根据每个门店的集合
             * 根据每个门店生成自增的取餐号
             * 每个门店的取餐号,在新的一天重新开始计算
             */
            //查询订单对应的门店
            OrderInfo orderInfo = orderMapper.selectById(orderNo);
            Integer storeId = orderInfo.getStoreId();
            String verifyNum = queueUtil.addQueue(storeId,orderNo);

            int count = orderMapper.finishPayOrderByWxPayNotify(orderNo,
                    OrderStatus.ENUM_on_making.value,
                    result.getTransactionId(),
                    result.getTotalFee(),
                    result.getTimeEnd(),
                    verifyNum);

            if(count > 0) {
                /**
                 * 订单消息发送
                 */
                orderWebSocketHandler.sendMessageToAll(JSON.toJSONString(orderMessageService.getResentOrderMessages()));
                return WxPayNotifyResponse.success("订单" + orderNo + "已经成功支付!");
            } else {
                return WxPayNotifyResponse.success("订单"+ orderNo +"已经处理过了!");
            }


        } catch (Exception e) {
            log.error("微信回调支付结果异常,异常原因:" +e.getMessage());
            return WxPayNotifyResponse.fail(e.getMessage());
        }



    }

    @Operation(summary = "获取正在处理中的订单")
    @NeedLogin
    @GetMapping("/notCompleted")
    public Result<List<OrderInfo>> getHandlingOrders(HttpServletRequest request) throws ServiceException {
        return Result.ok(orderService.getHandlingOrders(SessionUtil.getCurrentWxOpenidFromRequest(request)));
    }

    @Operation(summary = "查询当前订单的排位")
    @NeedLogin
    @GetMapping("/queue/{orderNo}")
    public Result<List<Integer>> getPosition(@PathVariable String orderNo) throws ServiceException {
            OrderInfo orderInfo = orderMapper.selectById(orderNo);
            return Result.ok(queueUtil.getOnesPosition(orderInfo.getStoreId(),orderNo));
    }

    @Operation(summary = "分页获取历史订单")
    @NeedLogin
    @GetMapping("/history/page/{pageNo}/{pageSize}")
    public Result<Page<HistoryOrderVo>> getHistoryOrderByPage(
            @PathVariable Integer pageNo,
            @PathVariable Integer pageSize,
            HttpServletRequest request
    ) throws ServiceException {
        return Result.ok(orderService.getHistoryOrderByPage(pageNo,pageSize,SessionUtil.getCurrentWxOpenidFromRequest(request)));
    }

    @Operation(summary = "订单详情")
    @NeedLogin
    @GetMapping("/detail/{orderNo}")
    public Result<OrderInfo> getOrderDetail(@PathVariable String orderNo)throws ServiceException {
        return Result.ok(orderService.getOrderDetail(orderNo));
    }











}
