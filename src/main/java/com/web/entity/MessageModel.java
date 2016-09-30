package com.web.entity;

import java.io.Serializable;

public class MessageModel implements Serializable{

	//用户ID
	private String frommcn;
	//服务器ID
	private String tomcn;
	//消息内容
	private String msg;
	public String getFrommcn() {
		return frommcn;
	}
	public void setFrommcn(String frommcn) {
		this.frommcn = frommcn;
	}
	public String getTomcn() {
		return tomcn;
	}
	public void setTomcn(String tomcn) {
		this.tomcn = tomcn;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	
	
}
