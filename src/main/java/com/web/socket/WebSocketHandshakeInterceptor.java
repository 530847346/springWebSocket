package com.web.socket;
import java.util.Map;  
  
import javax.servlet.http.HttpSession;  
  
import org.springframework.http.server.ServerHttpRequest;  
import org.springframework.http.server.ServerHttpResponse;  
import org.springframework.http.server.ServletServerHttpRequest;  
import org.springframework.web.socket.WebSocketHandler;  
import org.springframework.web.socket.server.support.HttpSessionHandshakeInterceptor;

import com.web.constant.Constants;  
  
public class WebSocketHandshakeInterceptor extends HttpSessionHandshakeInterceptor  
{  
    @Override  
    public void afterHandshake(ServerHttpRequest arg0, ServerHttpResponse arg1,  
            WebSocketHandler arg2, Exception arg3)  
    {  
        System.out.println("enter the afterHandshake");  
    }  
  
    /**  
     * ����Э�飬������֮�伴ͨ������֮ǰ�����۳ɹ���񶼻�ִ��  
     * @param handler Ŀ��handler����ʱ���Ǳ������е�SystemWebSocketHandler.java��������WebSocketConfig.java��ע���  
     * @param attribute ���ݴ��룬������WebSocketSession��ȡ��  
     */  
    @Override  
    public boolean beforeHandshake(ServerHttpRequest request,  
            ServerHttpResponse arg1, WebSocketHandler handler,  
            Map<String, Object> attribute) throws Exception  
    {  
        System.out.println("enter the beforeHandshake");  
        if(request instanceof ServerHttpRequest){  
            ServletServerHttpRequest servletRequest=(ServletServerHttpRequest) request;  
          HttpSession session=servletRequest.getServletRequest().getSession(false);  
          if(session!=null){  
              String userName=(String) session.getAttribute(Constants.SESSION_USERNAME);  
              System.out.println("userName:"+userName);  
              // ���ﲻ֪����ʲô��  
              attribute.put(Constants.WEBSOCKET_USERNAME, userName);  
          }  
        }  
        return true;  
    }  
}  