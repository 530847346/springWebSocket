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
     * 握手协议，在握手之间即通话链接之前，无论成功与否都会执行  
     * @param handler 目标handler，此时就是本程序中的SystemWebSocketHandler.java，都是在WebSocketConfig.java中注册的  
     * @param attribute 数据存入，可以在WebSocketSession中取出  
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
              // 这里不知道有什么用  
              attribute.put(Constants.WEBSOCKET_USERNAME, userName);  
          }  
        }  
        return true;  
    }  
}  