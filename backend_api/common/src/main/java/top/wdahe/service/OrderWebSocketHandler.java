package top.wdahe.service;

import jakarta.websocket.OnClose;
import jakarta.websocket.OnError;
import jakarta.websocket.OnOpen;
import jakarta.websocket.Session;
import jakarta.websocket.server.PathParam;
import jakarta.websocket.server.ServerEndpoint;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;

@ServerEndpoint("/order-server/{userId}")
@Component
@Slf4j
public class OrderWebSocketHandler {

    private  static  int onlineCount = 0;

    private static final ConcurrentHashMap<String,OrderWebSocketHandler> websocketMap = new ConcurrentHashMap<String,OrderWebSocketHandler>();

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


    public void sendInfo(String message,@PathParam("userId") String userId) throws IOException {
        log.info("发送消息到:" + userId + ",报文:" + message);
        if(StringUtils.isNotBlank(userId) && websocketMap.containsKey(userId)) {
            websocketMap.get(userId).sendMessage(message);
        } else {
            log.error("用户"+userId+"不在线");
        }
    }

    public void sendMessageToAll(String message) {
        for (OrderWebSocketHandler handler: websocketMap.values()) {
            try {
                log.info("发送消息到:" + userId + ",报文:" + message);
                handler.sendMessage(message);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    public void sendMessage(String sendMessage) throws IOException {
        this.session.getBasicRemote().sendText(sendMessage);
    }
    public static synchronized void addOnlineCount() {
        OrderWebSocketHandler.onlineCount++;
    }

    public static synchronized int getOnlineCount() {
        return onlineCount;
    }

    public static synchronized void subOnlineCount() {
        OrderWebSocketHandler.onlineCount--;
    }


}
