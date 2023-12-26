package top.wdahe.food_app.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.*;
import top.wdahe.entity.OrderInfo;
import top.wdahe.entity.vo.HistoryOrderVo;

import java.util.List;

public interface OrderMapper extends BaseMapper<OrderInfo> {

    //订单总价格
    @Select("select total_price from order_info where order_no = #{param1}; ")
    Integer getOrderTotalPriceByOrderNo(String orderNo);


    //微信支付成功回调,填入交易号,支付时间,更新订单状态
    @Update("UPDATE order_info SET order_status=#{param2}, wx_pay_transaction_id=#{param3}, pay_price=#{param4}, pay_time=#{param5} , verify_num=#{param6} " +
            " WHERE order_no=#{param1} and wx_pay_transaction_id is null; ")
    int finishPayOrderByWxPayNotify(String orderNo, String orderStatus, String wxPayTransactionId, Integer payPrice, String payTime, String verifyNum);

    @Select({
            "<script>",
            "SELECT o.*, s.store_name FROM order_info o",
            "LEFT JOIN store_info s ON o.store_id = s.store_id",
            "WHERE o.wx_openid = #{wxOpenid} AND o.order_status NOT IN (",
            "<foreach collection='statusList'  item='status' separator=',' close=')' >",
            "#{status}",
            "</foreach>",
            "ORDER BY o.create_time DESC",
            "</script>"

    })
    @Results({
            @Result(property = "storeName",column = "store_name")
    })
    List<OrderInfo> getHandlingOrders(@Param("wxOpenid") String wxOpenid, @Param("statusList") List<String> statusList);


    @Select("SELECT count(*) FROM order_info WHERE wx_openid = #{wxOpenid};")
    int getHistoryOrderTotalCount(@Param("wxOpenid") String wxOpenid);

    @Select(
            "SELECT order_no as orderNo," +
                    "order_status as orderStatus," +
                    "goods_preview as goodsPreview," +
                    "goods_total_num as goodsTotalNum," +
                    "create_time as createTime," +
                    "pay_Price as payPrice " +
                    "from order_info WHERE wx_openid = #{param1} order by create_time desc limit #{param2},#{param3}; "
    )
    List<HistoryOrderVo> getHistoryOrderByPage(String wxOpenid, Integer rowStart,Integer pageSize);


    @Select(
            "SELECT o.*, s.store_name FROM order_info o " +
            "LEFT JOIN store_info s ON o.store_id = s.store_id " +
                    "WHERE o.order_no = #{param}; "
    )
    OrderInfo getOrderDetail(String orderNo);


}
