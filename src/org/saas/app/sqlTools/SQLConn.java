package org.saas.app.sqlTools;

import org.saas.app.Tools.Journal;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import com.opensymphony.xwork2.ActionContext;
import org.apache.struts2.ServletActionContext;

import java.util.*;

public class SQLConn{

	private static Connection conn = null;
	//For Mysql
	//private String driver = "com.mysql.jdbc.Driver";
	//private String url = "jdbc:mysql://127.0.0.1:3306/db_saas?useSSL=false";
	//priv//ate String user = "root";
	//private String pass = "123000";

	//构造方法
	public SQLConn() throws Exception{
		String sqlFile;
		//For Mysql
		//Class.forName(driver);
		//conn = DriverManager.getConnection(url,user,pass);
		Class.forName("org.sqlite.JDBC");
		//conn for linux
		//conn = DriverManager.getConnection("jdbc:sqlite:/Users/bone/myWork/source/hlldata.dll");
		//conn for Win

		//获取ActionContext
		ActionContext ctx = ActionContext.getContext();
		Map<String,Object> session = ctx.getSession();
		//如果还未登录或者没有上传数据，需要线连接到登陆的数据库
		//如果已经登录并且上传数据了，则连接上传的数据库
		Journal.writeLog("SQLConn:session.get(\"user\")是否为空：" + (String)session.get("user"));
		Journal.writeLog("SQLConn:session.get(\"uploadedDir\")是否为空：" + ((String)session.get("uploadDir")));
		Journal.writeLog("first is :" + String.valueOf(((String)session.get("user")) != null) + "&& second is:" + String.valueOf(((String)session.get("uploadDir")) != null));
		Journal.writeLog("是否连接到上传的文件：" + String.valueOf(((String)session.get("user")) != null && ((String)session.get("uploadDir")) != null));
		if(((String)session.get("user")) != null && ((String)session.get("uploadDir")) != null){
			sqlFile = (String)session.get("uploadDir") + "/hlldata.dll";
			Journal.writeLog("连接文件：" + sqlFile);
		}
		else{
			if(ServletActionContext.getServletContext().getInitParameter("envType").equals("mac")){
				Journal.writeLog("环境为mac");
				sqlFile = "/Users/bone/myWork/source/hlldata.dll";
			}
			else{
				Journal.writeLog("环境为win");
				sqlFile = "E:/source/hlldata.dll";
			}
			
		}
		String connSQLFile = "jdbc:sqlite:" + sqlFile;
		//连接
		conn = DriverManager.getConnection(connSQLFile);
	}

	//获得连接对象
	public static Connection getConnection(){
		Journal.writeLog("获取连接！！！");
		return conn;
	}

	//关闭连接
	public static void CloseConn() throws SQLException{
		conn.close();
	}
}
