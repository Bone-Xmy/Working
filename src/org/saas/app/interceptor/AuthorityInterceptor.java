package org.saas.app.interceptor;

import com.opensymphony.xwork2.*;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

import java.util.*;


public class AuthorityInterceptor extends AbstractInterceptor{
	//拦截Action处理的拦截方法
	public String intercept(ActionInvocation invocation) throws Exception{
		//获得请求相关的ActionContext实例
		ActionContext ctx = invocation.getInvocationContext();
		Map session = ctx.getSession();
		//取出Session里的user属性
		String user = (String)session.get("user");
		//如果user为null,则返回登陆页面
		//--根据从session里面获取到的user属性是否为null来判断是否允许登陆，是否会造成如果有一个人登陆了，别的人也可以登陆的情况呢
		if(user != null){
			return invocation.invoke();
		}
		//如果没有登陆，将服务器提示放入ActionContext中
		ctx.put("tip","您还没有登陆，请输入用户名、密码登陆系统");
		//直接返回login的逻辑视图
		return Action.LOGIN;
	}
}


