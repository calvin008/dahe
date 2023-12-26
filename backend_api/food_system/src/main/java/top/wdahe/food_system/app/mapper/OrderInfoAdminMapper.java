package top.wdahe.food_system.app.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Update;
import top.wdahe.entity.OrderInfo;

public interface OrderInfoAdminMapper extends BaseMapper<OrderInfo> {

    @Update("UPDATE order_info SET order_status =#{param3}," +
            "wx_pay_transactionId =#{param2}, pay_price =#{param4}, pay_time =#{param5} \n" +
            " WHERE order_no =#{param1}; ")
    void updateOrderStatus(String orderNo,String wxPayTransactionId,String orderStatus,Integer payPrice,String payTime);

}
