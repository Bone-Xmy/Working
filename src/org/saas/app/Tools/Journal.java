package org.saas.app.Tools;
import java.time.*;
import java.time.format.*;
import java.io.*;

public class Journal{
	
	public static String getTime(){
		String dt = "";
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd HH:mm:ss");
		LocalDateTime date = LocalDateTime.now();
		//格式化LocalDateTime
		dt = date.format(formatter);
		return dt;
	}
	public void writeLog(String logs){
		try(
			//以读、写方式打开一个RandoAccessFile对象
			RandomAccessFile raf = new RandomAccessFile("/Users/bone/myWork/logs/saas.cdata","rw"))
		{
			//将记录指针移动到saas.cdata文件最后
			raf.seek(raf.length());
			raf.write((getTime() + " : " + logs + "\r\n").getBytes());
		}
		catch(IOException ioe){
			ioe.printStackTrace();
		}
	}
}
