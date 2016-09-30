package com.web.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.socket.TextMessage;

import com.web.constant.Constants;
import com.web.socket.SystemWebSocketHandler;

@Controller
@RequestMapping("/web")
public class UserController {

	
	@Bean//这个注解会从Spring容器拿出Bean  
    public SystemWebSocketHandler infoHandler() {  
        return new SystemWebSocketHandler();  
    } 
	
	@RequestMapping(value="/websocket")
	public String webChat(Model model){
		
		return "chat";
	}
	
	@RequestMapping(value="/login")
	public String login(HttpServletRequest request, HttpServletResponse response){
		
		String username = request.getParameter("username");  
        HttpSession session = request.getSession();  
        session.setAttribute(Constants.SESSION_USERNAME, username); 		
		return "chat";
	}
	
	@RequestMapping(value="/serverLogin")
	public String serverPage(){
		
		return "server";
	}
	
	@RequestMapping("/serverSend")  
    @ResponseBody  
    public String send(HttpServletRequest request,String username,String message) {  
        infoHandler().sendMessageToUser(username, new TextMessage(message));  
        return null;  
    } 
}
