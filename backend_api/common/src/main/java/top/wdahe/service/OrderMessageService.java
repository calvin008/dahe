package top.wdahe.service;

import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import top.wdahe.entity.common.Notification;

import java.util.Collection;

import static top.wdahe.common.constant.Const.CONST_recent_order_message_map;

@Service
public class OrderMessageService {

    @Resource
    private RedisService redisService;

    //查询最新的订单消息
    public Collection<Object> getResentOrderMessages() {
        return redisService.hmget(CONST_recent_order_message_map).values();
    }

    //确认收到的消息
    public String confirmReceiveOrderMessage(String orderNo) {
        Object orderMessage = redisService.hget(CONST_recent_order_message_map,orderNo);
        if(orderMessage == null) {
            return "已经确认过了";
        }

        redisService.hdel(CONST_recent_order_message_map,orderNo);
        return "已经确认过了";
    }

    //添加最新的订单消息
    public boolean addOrderMessage(Notification message) {
        return  redisService.hset(CONST_recent_order_message_map,message.getOrderNo(),message);
    }

    //全部已读
    public void receiveAllOrderMessage() {
        redisService.del(CONST_recent_order_message_map);
    }

}
