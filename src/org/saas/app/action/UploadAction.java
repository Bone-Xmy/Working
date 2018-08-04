package org.saas.app.action;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ActionContext;

import org.apache.struts2.ServletActionContext;

import java.io.File;
import java.io.*;

import java.util.*;

import org.saas.app.Tools.MyDir;

public class UploadAction extends ActionSupport{
	//封装文件标题请求参数的成员变量
	private String title;
	//封装上传文件域的成员变量
	private File upload;
	//封装上传文件类型的成员变量
	private String uploadContentType;
	//封装上传文件名的属性
	private String uploadFileName;
	//直接在struts.xml文件配置值的方法
	private String savePath;
	//接受struts.xml文件配置的方法
	public void setSavePath(String value){
		this.savePath = value;
	}
	//获取上传文件的保存位置
	private String getSavePath() throws Exception{
		return ServletActionContext.getServletContext().getRealPath(savePath);
	}
	//title的setter和getter方法
	public void setTitle(String title){
		this.title = title;
	}
	public String getTitle(){
		return (this.title);
	}
	//upload的setter和getter方法
	public void setUpload(File upload){
		this.upload = upload;
	}
	public File getUpload(){
		return (this.upload);
	}
	//uploadContentType的setter和getter方法
	public void setUploadContentType(String uploadContentType){
		this.uploadContentType = uploadContentType;
	}
	public String getUploadContentType(){
		return (this.uploadContentType);
	}
	//uploadFileName的setter和getter方法
	public void setUploadFileName(String uploadFileName){
		this.uploadFileName = uploadFileName;
	}
	public String getUploadFileName(){
		return (this.uploadFileName);
	}

	@Override
	public String execute() throws Exception{
		//如何保证三个文件上传到同一个路径下呢？（session里面获取uploaded的value?）
		//以服务器的文件保存地址和原文件名建议上传文件输出流
		////FileOutputStream fos = new FileOutputStream(getSavePath() + "\\" + getUploadFileName());
		String uploadedFile = new MyDir().getDir() + "/" + getUploadFileName();
		FileOutputStream fos = new FileOutputStream(uploadedFile);
		FileInputStream fis = new FileInputStream(getUpload());
		byte[] buffer = new byte[1024];
		int len = 0;
		try{
			while((len = fis.read(buffer)) > 0){
				fos.write(buffer,0,len);
			}

			//获取ActionContext
			ActionContext ctx = ActionContext.getContext();
			Map<String,Object> session = ctx.getSession();
			if(getUploadFileName().startsWith("hll")){
				session.put("uploaded",uploadedFile);
			}
			else if(getUploadFileName().startsWith("Food")){
				session.put("uploadedFoodLst",uploadedFile);
			}

			return SUCCESS;
		}
		catch (Exception e){
			e.printStackTrace();
			return ERROR;
		}
		
	}
}
