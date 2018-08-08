package org.saas.app.Tools;
import java.time.*;
import java.time.format.*;
import java.io.*;

import org.apache.struts2.ServletActionContext;

public class Journal{
	
	public static String getTime(){
		String dt = "";
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd HH:mm:ss");
		LocalDateTime date = LocalDateTime.now();
		//格式化LocalDateTime
		dt = date.format(formatter);
		return dt;
	}
	public static void writeLog(String logs){
		RandomAccessFile raf = null;
		try{
			//以读、写方式打开一个RandoAccessFile对象
			//RandomAccessFile raf = new RandomAccessFile("/Users/bone/myWork/logs/saas.cdata","rw"))
			//win
			
			if(ServletActionContext.getServletContext().getInitParameter("envType").equals("mac")){
				raf = new RandomAccessFile("/Users/bone/myWork/logs/saas.cdata","rw");
			}
			else{
				raf = new RandomAccessFile("E:/source/logs/saas.log","rw");
			}
			//将记录指针移动到saas.cdata文件最后
			raf.seek(raf.length());
			raf.write((getTime() + " : " + logs + "\r\n").getBytes());
		}
		catch(IOException ioe){
			ioe.printStackTrace();
		}		
		finally{
			if(raf != null){
				try{
					raf.close();
				}
				catch (IOException ioe){
					ioe.printStackTrace();
				}
			
			}
		}
		
	}
}
