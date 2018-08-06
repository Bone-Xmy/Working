package org.saas.app.action;

import com.opensymphony.xwork2.ActionSupport;

//仅仅为了切换页面
public class SecondPageAction1 extends ActionSupport{
	public String execute() throws Exception{
		System.out.println("单机了我！！");
		return SUCCESS;
	}
}

