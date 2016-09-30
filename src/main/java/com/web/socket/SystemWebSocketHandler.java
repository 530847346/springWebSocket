package com.web.socket;
import java.io.IOException;
import java.util.ArrayList;

import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;

import com.alibaba.fastjson.JSON;
import com.web.constant.Constants;
import com.web.entity.MessageModel;  
  
public class SystemWebSocketHandler implements WebSocketHandler  
{  
  
    // 创建一个集合存放websocketsession  
    private static final ArrayList<WebSocketSession> users;  
  
    static  
    {  
        users = new ArrayList<>();  
    }  
  
    //连接以创建以后执行的方法  
    @Override  
    public void afterConnectionEstablished(WebSocketSession session)  
            throws Exception  
    {  
        users.add(session);  
        String userName = (String) session.getAttributes().get(  
                Constants.WEBSOCKET_USERNAME);  
        System.out.println(userName+" 链接成功... ");  
        if (userName != null)  
        {  
            // 查询未读消息  
        }  
    }  
   //接收客户端发送过来的方法  
    @Override  
    public void handleMessage(WebSocketSession session,  
            WebSocketMessage<?> message) throws Exception  
    {  
        String userName = (String) session.getAttributes().get(  
                Constants.WEBSOCKET_USERNAME);  
        System.out.print(userName+" 用户发送了消息:");  
        System.out.println(message.getPayload().toString());  
        MessageModel messageModel = JSON.parseObject(message.getPayload()+"", MessageModel.class);
        TextMessage message1=new TextMessage(userName+":"+messageModel.getMsg());  
        sendMessageToUsers(message1);  
        sendMessageToUser("王五", message1);  
    }  
  
    //传输错误时触发  
    @Override  
    public void handleTransportError(WebSocketSession session,  
            Throwable exception) throws Exception  
    {  
        if (session.isOpen())  
        {  
            session.close();  
        }  
        users.remove(session);  
    }  
  
    //连接关闭时触发  
    @Override  
    public void afterConnectionClosed(WebSocketSession session,  
            CloseStatus closeStatus) throws Exception  
    {  
        users.remove(session);  
    }  
  
    @Override  
    public boolean supportsPartialMessages()  
    {  
        return false;  
    }  
  
    /**  
     * 给所有在线用户发送消息  
     * @param message  
     */  
    public void sendMessageToUsers(TextMessage message)  
    {  
        for (WebSocketSession user : users)  
        {  
            try  
            {  
                if (user.isOpen())  
                {  
                    user.sendMessage(message);  
                }  
            } catch (IOException e)  
            {  
                e.printStackTrace();  
            }  
        }  
    }  
  
    /**  
     * 给某个用户发送消息  
     * @param userName  
     * @param message  
     */  
    public void sendMessageToUser(String userName, TextMessage message)  
    {  
        for (WebSocketSession user : users)  
        {  
            if (user.getAttributes().get(Constants.WEBSOCKET_USERNAME)  
                    .equals(userName))  
            {  
                try  
                {  
                    if (user.isOpen())  
                    {  
                        user.sendMessage(message);  
                    }  
                } catch (IOException e)  
                {  
                    e.printStackTrace();  
                }  
                break;  
            }  
        }  
    }  
}  