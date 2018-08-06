package org.saas.app.action;

import com.opensymphony.xwork2.ActionSupport;

//仅仅为了切换页面
public class PageDispatchAction extends ActionSupport{
	public String execute() throws Exception{
		System.out.println("这是第一页！");
		return SUCCESS;
	}
	public String getSecondPage() throws Exception{
		System.out.println("这是第二页！");
		return INPUT;
	}
}
