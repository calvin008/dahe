package top.wdahe.service;


import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import jakarta.websocket.*;
import jakarta.websocket.server.PathParam;
import jakarta.websocket.server.ServerEndpoint;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Component;
import top.wdahe.common.util.spring.SpringContextUtil;
import top.wdahe.entity.ChatRecord;

import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;

@ServerEndpoint("/chat-server/{userId}")
@Component
@EnableAutoConfiguration

public class ChatWebSocketService {

    private static final RedisService redisService = SpringContextUtil.getBeanByClass(RedisService.class);

    static Log log = LogFactory.get(ChatWebSocketService.class);
    private  static  int onlineCount = 0;

    private static final ConcurrentHashMap<String, ChatWebSocketService> websocketMap = new ConcurrentHashMap<String, ChatWebSocketService>();

    private Session session;

    private String userId = "";

    @OnOpen
    public void onOpen(Session session, @PathParam("userId") String userId) {
        this.session = session;
        this.userId = userId;
        if(websocketMap.containsKey(userId)) {
            websocketMap.remove(userId);
            websocketMap.put(userId,this);
        } else {
            websocketMap.put(userId,this);
            //在线加一
            addOnlineCount();
        }

        log.info("用户链接:"+ userId + ",当前人数为:" + getOnlineCount());

        /**
         * 向用户发送消息,连接成功
         */

    }


    @OnClose
    public  void  onClose() {
        if(websocketMap.containsKey(userId)) {
            websocketMap.remove(userId);
            subOnlineCount();
        }

        log.info("用户退出:"+ userId + ",当前人数为:" + getOnlineCount());
    }


    @OnError
    public void onError(Session session,Throwable error) {
        if(websocketMap.containsKey(userId)) {
            websocketMap.remove(userId);
            subOnlineCount();
        }
        log.info("用户错误:"+ userId + ",error原因:" + error.getMessage());
        error.printStackTrace();
    }

    /**
     * 收到客户端消息后调用的方法
     *
     */

    @OnMessage
    public void onMessage(String message,Session session) {
        log.info("用户消息"+ userId + ",报文" + message);
        if(StringUtils.isNotBlank(message)) {
            try {
                JSONObject jsonObject = JSON.parseObject(message);
                jsonObject.put("fromUserId",this.userId);
                String toUserId = jsonObject.getString("toUserId");
                String currentDate = jsonObject.getString("formatDate");
                String body = jsonObject.getString("body");
                String client = jsonObject.getString("client");
                //创送给对应的toUserId用户
                if(StringUtils.isNotBlank(toUserId) && websocketMap.containsKey(toUserId) && websocketMap.containsKey("service")) {
                    if(toUserId.equals("service")) {
                        String toClient = jsonObject.getString("toClient");
                        websocketMap.get(toClient).sendMessage(message);
                        //存储消息到redis
                        ChatRecord chatRecord = new ChatRecord();
                        chatRecord.setToUserId("service");
                        chatRecord.setBody(body);
                        chatRecord.setFormatDate(currentDate);
                        chatRecord.setToClient(client);
                        String chatRecordJson = JSON.toJSONString(chatRecord);
                        redisService.lRightPush("chat-record:" + toClient,chatRecordJson);
                    } else  {
                        websocketMap.get("service").sendMessage(message);
                        //存储消息到redis
                        ChatRecord chatRecord = new ChatRecord();
                        chatRecord.setToUserId(toUserId);
                        chatRecord.setBody(body);
                        chatRecord.setFormatDate(currentDate);
                        chatRecord.setToClient(client);
                        String chatRecordJson = JSON.toJSONString(chatRecord);
                        redisService.lRightPush("chat-record:" + toUserId,chatRecordJson);
                    }
                } else {
                    log.error("请求的userId:" + toUserId + "不在该服务器上!");
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 服务器主动推送消息
     */
    public void sendMessage(String message) throws IOException {
        this.session.getBasicRemote().sendText(message);
    }

    public static synchronized void addOnlineCount() {
        ChatWebSocketService.onlineCount++;
    }

    public static synchronized int getOnlineCount() {
        return onlineCount;
    }

    public static synchronized void subOnlineCount() {
        ChatWebSocketService.onlineCount--;
    }

}
