package org.saas.app.Tools;

import org.saas.app.Tools.MyUUID;
import java.io.File;

import org.apache.struts2.ServletActionContext;
import com.opensymphony.xwork2.ActionContext;

import java.util.*;

public class MyDir{
	public String getDir(){
		File newFile;

		//获取ActionContext
		ActionContext ctx = ActionContext.getContext();
		Map<String,Object> session = ctx.getSession();

		//获取配置的entType
		if(ServletActionContext.getServletContext().getInitParameter("envType").equals("mac")){
			//在/Users/bone/myWork/source/下创建一个以UUID为名称的文件,此时只有File对象，不会创建文件
			newFile = new File("/Users/bone/myWork/source/" + session.get("user"));
		}
		else{
			newFile = new File("E:/source/" + session.get("user"));
		}
		//在/Users/bone/myWork/source/下创建一个以UUID为名称的文件,此时只有File对象，不会创建文件
		//File  newFile = new File("/Users/bone/myWork/source/" + new MyUUID().getUUID());
		//创建目录
		if(!newFile.exists()){
			newFile.mkdir();
		}
		return newFile.getAbsolutePath();
	}
}
