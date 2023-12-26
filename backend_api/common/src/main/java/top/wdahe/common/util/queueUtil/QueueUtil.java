package top.wdahe.common.util.queueUtil;

import jakarta.annotation.Resource;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

@Component
@EnableScheduling
public class QueueUtil {
    //记录每个门店的取餐号索引
    private static final String TICKET_INDEX_KEY = "ticketIndex-";

    @Resource
    private RedisTemplate<String,String> redisTemplate;

    private static Map<Integer, AtomicLong> orderNumbers = new HashMap<>();//存储门店取餐号的计数器


    //添加队列
    public String addQueue(Integer storeId, String orderNo) {
        redisTemplate.opsForList().rightPush(TICKET_INDEX_KEY + storeId,orderNo);
        return generateTicketNo(storeId);
    }


    //生成取餐号
    public String generateTicketNo(Integer storeId) {
        AtomicLong orderNumber = orderNumbers.get(storeId);
        if(orderNumber == null) {
            orderNumber = new AtomicLong(1L);
            orderNumbers.put(storeId,orderNumber);
        } else {
            orderNumber.incrementAndGet();
        }

        String str = String.format("%05d",orderNumber.get());
        return str;
    }


    @Scheduled(cron = "59 59 23 * * ?")
    public void cleanExpiredOrders() {
        orderNumbers.clear();
    }


    public List<String> getQueueData(Integer storeId) {
        return redisTemplate.opsForList().range(TICKET_INDEX_KEY+ storeId,0,-1);
    }


    public List<Integer>  getOnesPosition(Integer storeId,String orderNo) {
        List<String> queueData = getQueueData(storeId);
        int myPositionBeforeNum = queueData.indexOf(orderNo);
        int myPosition = myPositionBeforeNum + 1;
        int size = queueData.size();
        List<Integer> result = new ArrayList<>();
        result.add(size);
        result.add(myPosition);
        result.add(myPositionBeforeNum);
        return result;
    }

}
