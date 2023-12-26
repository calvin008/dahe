package top.wdahe.food_system.app.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.binarywang.wxpay.exception.WxPayException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;
import top.wdahe.common.annotation.NeedPermission;
import top.wdahe.common.exception.ServiceException;
import top.wdahe.common.util.result.Result;
import top.wdahe.entity.OrderInfo;
import top.wdahe.entity.dto.OrderQueryDTO;
import top.wdahe.food_system.app.service.OrderInfoAdminService;

@Tag(name = "系统:订单管理")
@RestController
@RequestMapping("/orderInfoAdmin")
public class OrderInfoAdminController {
    @Resource
    private OrderInfoAdminService orderInfoAdminService;

    @Operation(summary = "分页查询")
    @NeedPermission("system:order:orderInfo:list")
    @GetMapping("/page")
    public Result<Page<OrderInfo>> getOrderInfoAdminByPage(@RequestParam(defaultValue = "1") int pageNo,
                                                           @RequestParam(defaultValue = "10") int pageSize,
                                                           @RequestParam(required = false) String orderStatus,
                                                           @RequestParam(required = false) int storeId) {
            return Result.ok(orderInfoAdminService.getOrderInfoAdminByPage(pageNo,pageSize,orderStatus,storeId));
    }


    @Operation(summary = "精准查询订单信息")
    @NeedPermission("system:order:orderInfo:query")
    @PostMapping("/query")
    public Result<Page<OrderInfo>> getOrderInfoList(@RequestParam(defaultValue = "1") int pageNo,
                                                    @RequestParam(defaultValue = "10") int pageSize,
                                                    @RequestBody OrderQueryDTO orderObj) {
        return Result.ok(orderInfoAdminService.getOrderInfoList(pageNo,pageSize,orderObj));
    }

    @Operation(summary = "进入订单的下一步")
    @NeedPermission("system:order:orderInfo:update")
    @PostMapping("/nextStatus/{orderNo}")
    public Result<String> toNextOrderStatus(@PathVariable String orderNo) throws ServiceException {
        return Result.ok(orderInfoAdminService.toNextOrderStatus(orderNo));
    }

    @Operation(summary = "商家取消没有付款的订单")
    @NeedPermission("system:order:orderInfo:cancel")
    @PutMapping("/cancelOrderNotPay/{orderNo}")
    public Result cancelOrderNotPay(@PathVariable String orderNo) throws ServiceException {
        return  Result.ok(orderInfoAdminService.cancelOrderNotPay(orderNo));
    }
    @Operation(summary = "主动查询微信支付的支付状态")
    @NeedPermission("system:order:orderInfo:status")
    @GetMapping("/wxPayStatus/{orderNo}")
    public Result<String> queryWeixinOrder(@PathVariable String orderNo) throws WxPayException {
        return Result.ok(orderInfoAdminService.queryWeixinOrder(orderNo).getTradeState());
    }





}
