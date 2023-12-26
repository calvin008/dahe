package top.wdahe.food_app.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.transaction.annotation.Transactional;
import top.wdahe.common.exception.ServiceException;
import top.wdahe.common.util.result.Result;
import top.wdahe.entity.OrderInfo;
import top.wdahe.entity.form.CreateOrderForm;
import top.wdahe.entity.vo.HistoryOrderVo;

import java.util.List;

public interface OrderService {
    //创建订单
    @Transactional(rollbackFor = Exception.class)
    Result<String> createOrder(CreateOrderForm orderForm, String wxOpenid);

    Object wxPrepay(String wxOpenid, String orderNo, String ip) throws ServiceException;

    List<OrderInfo> getHandlingOrders(String wxOpenid) throws ServiceException;

    Page<HistoryOrderVo> getHistoryOrderByPage(Integer pageNo, Integer pageSize, String wxOpenid) throws ServiceException;

    OrderInfo getOrderDetail(String orderNo)throws ServiceException;
}
