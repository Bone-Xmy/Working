package org.saas.app.action;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ActionContext;

import org.apache.struts2.ServletActionContext;

import java.io.File;
import java.io.*;

import java.util.*;

import org.saas.app.Tools.MyDir;
import org.saas.app.Tools.Journal;

public class UploadAction extends ActionSupport{
	//封装文件上传地址的成员变量
	private String uploadDir;
	//封装文件标题请求参数的成员变量
	private String title;
	//封装上传文件域的成员变量
	private File[] upload;
	//封装上传文件类型的成员变量
	private String[] uploadContentType;
	//封装上传文件名的属性
	private String[] uploadFileName;
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
	//uploadDir的setter和getter方法
	public String getUploadDir(){
		if(uploadDir == null){
			uploadDir = new MyDir().getDir();
		}
		return uploadDir;
	}
	//title的setter和getter方法
	public void setTitle(String title){
		this.title = title;
	}
	public String getTitle(){
		return (this.title);
	}
	//upload的setter和getter方法
	public void setUpload(File[] upload){
		this.upload = upload;
	}
	public File[] getUpload(){
		return (this.upload);
	}
	//uploadContentType的setter和getter方法
	public void setUploadContentType(String[] uploadContentType){
		this.uploadContentType = uploadContentType;
	}
	public String[] getUploadContentType(){
		return (this.uploadContentType);
	}
	//uploadFileName的setter和getter方法
	public void setUploadFileName(String[] uploadFileName){
		this.uploadFileName = uploadFileName;
	}
	public String[] getUploadFileName(){
		return (this.uploadFileName);
	}

	//上传文件的方法
	public String uploadBatch() throws Exception{
		Journal.writeLog("uploadFileName 的length为：" + uploadFileName.length);
		for(int i = 0; i < uploadFileName.length; i++){
			Journal.writeLog("uploadBatch上传的文件为：" + uploadFileName[i]);
			Journal.writeLog("uploadBatch上传的fis为：" + upload[i]);
			//上传到的文件名
			String uploadedFile = getUploadDir() + "/" + uploadFileName[i];
			Journal.writeLog("uploadBatch上传的fos为：" + uploadedFile);
			FileOutputStream fos = new FileOutputStream(uploadedFile);
			FileInputStream fis = new FileInputStream(upload[i]);

			byte[] buffer = new byte[1024];
			int len = 0;

			try{
				while((len = fis.read(buffer)) > 0){
					fos.write(buffer,0,len);
				}

				/*
				//获取ActionContext
				ActionContext ctx = ActionContext.getContext();
				Map<String,Object> session = ctx.getSession();

				if(uploadFileName[i].startsWith("hll")){
					Journal.writeLog("上传成功！");
					session.put("uploadDir",uploadDir);
					Journal.writeLog("上传后的session为：" + (String)session.get("uploadDir"));
				}
				else if(uploadFileName[i].endsWith("Food")){
					session.put("uploadedFoodLst",uploadDir);
				}*/	
			}
			catch (Exception e){
				e.printStackTrace();
				return ERROR;
			}
			finally{
				if(fos != null){
					fos.flush();
					fos.close();
				}
				if(fis != null){
					fis.close();
				}
			}
		}
		return SUCCESS;
	}

	@Override
	public String execute() throws Exception{

		//如何保证三个文件上传到同一个路径下呢？（session里面获取uploaded的value?）

		//获取ActionContext
		//ActionContext ctx = ActionContext.getContext();
		//Map<String,Object> session = ctx.getSession();
		
		
		//Journal.writeLog("uploadAction 获取到的uploadFile为：" + uploadedFile);
 
		/*
		if((String)session.get("uploadDir") != null){
			//hlldata和foodLst可能上传到同一个路径，也可能不同，暂时不考虑
			Journal.writeLog("session信息为：" + (String)session.get("uploadDir"));
			uploadDir = (String)session.get("uploadDir");
			uploadedFile = uploadDir + "/" + getUploadFileName();
		}
		else{
			//以服务器的文件保存地址和原文件名建议上传文件输出流
			////FileOutputStream fos = new FileOutputStream(getSavePath() + "\\" + getUploadFileName());
			uploadDir = new MyDir().getDir();
			uploadedFile = uploadDir + "/" + getUploadFileName();
		} */
		//FileOutputStream fos = new FileOutputStream(uploadedFile);
		//FileInputStream fis = new FileInputStream(getUpload());
		//byte[] buffer = new byte[1024];
		//int len = 0;
		//try{
			//while((len = fis.read(buffer)) > 0){
				//fos.write(buffer,0,len);
			//}
			
			

			return this.uploadBatch();
		//}
		//catch (Exception e){
			//e.printStackTrace();
			//return ERROR;
		//}
		
	}
}
