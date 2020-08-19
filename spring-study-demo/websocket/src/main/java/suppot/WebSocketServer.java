package com.encdata.lf.activity.support.websocket.mywebsocket;

import org.springframework.stereotype.Component;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @ProjectName: lf-activity-main
 * @Package: com.encdata.lf.activity.support.websocket.mywebsocket
 * @ClassName: WebSocketServer
 * @Description: websocket服务端代码
 * @Author: wangdehonga
 * @Date: 2020/7/21 9:21
 * @Version: 1.0
 */
@Component
@ServerEndpoint(value = "/socket/{userId}")
public class WebSocketServer {

    //concurrent包的线程安全Set，用来存放每个客户端对应的WebSocketServer对象。
    private static Map<String, Session> sessionPools = new ConcurrentHashMap<>();

    /**
     * 发送消息方法
     *
     * @param session 客户端与socket建立的会话
     * @param message 消息
     * @throws IOException
     */
    public void sendMessage(Session session, String message) throws IOException {
        if (session != null) {
            session.getBasicRemote().sendText(message);
        }
    }


    /**
     * 连接建立成功调用
     *
     * @param session 客户端与socket建立的会话
     * @param userId  userId
     */
    @OnOpen
    public void onOpen(Session session, @PathParam(value = "userId") String userId) {
        sessionPools.put(userId, session);
    }

    /**
     * 关闭连接时调用
     *
     * @param userId 关闭连接的客户端的姓名
     */
    @OnClose
    public void onClose(@PathParam(value = "userId") String userId) {
        sessionPools.remove(userId);
    }

    /**
     * 发生错误时候
     *
     * @param session
     * @param throwable
     */
    @OnError
    public void onError(Session session, Throwable throwable) {
        throwable.printStackTrace();
    }

    /**
     * 给指定用户发送消息
     *
     * @param userIds  用户名
     * @param message 消息
     * @throws IOException
     */
    public void sendInfo(List<String> userIds, String message) {
        List<Session> session = new ArrayList<>();
        userIds.forEach( u -> {
            session.add(sessionPools.get(u));
        });
        if (session.size() != 0) {
            session.forEach(s -> {
                try {
                    sendMessage(s, message);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        }
    }

}
