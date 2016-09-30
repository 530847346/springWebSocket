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
  
    // ����һ�����ϴ��websocketsession  
    private static final ArrayList<WebSocketSession> users;  
  
    static  
    {  
        users = new ArrayList<>();  
    }  
  
    //�����Դ����Ժ�ִ�еķ���  
    @Override  
    public void afterConnectionEstablished(WebSocketSession session)  
            throws Exception  
    {  
        users.add(session);  
        String userName = (String) session.getAttributes().get(  
                Constants.WEBSOCKET_USERNAME);  
        System.out.println(userName+" ���ӳɹ�... ");  
        if (userName != null)  
        {  
            // ��ѯδ����Ϣ  
        }  
    }  
   //���տͻ��˷��͹����ķ���  
    @Override  
    public void handleMessage(WebSocketSession session,  
            WebSocketMessage<?> message) throws Exception  
    {  
        String userName = (String) session.getAttributes().get(  
                Constants.WEBSOCKET_USERNAME);  
        System.out.print(userName+" �û���������Ϣ:");  
        System.out.println(message.getPayload().toString());  
        MessageModel messageModel = JSON.parseObject(message.getPayload()+"", MessageModel.class);
        TextMessage message1=new TextMessage(userName+":"+messageModel.getMsg());  
        sendMessageToUsers(message1);  
        sendMessageToUser("����", message1);  
    }  
  
    //�������ʱ����  
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
  
    //���ӹر�ʱ����  
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
     * �����������û�������Ϣ  
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
     * ��ĳ���û�������Ϣ  
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