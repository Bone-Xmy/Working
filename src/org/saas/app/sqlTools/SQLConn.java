package org.saas.app.sqlTools;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SQLConn{

	private static Connection conn = null;
	//For Mysql
	//private String driver = "com.mysql.jdbc.Driver";
	//private String url = "jdbc:mysql://127.0.0.1:3306/db_saas?useSSL=false";
	//priv//ate String user = "root";
	//private String pass = "123000";

	//构造方法
	public SQLConn() throws Exception{
		//For Mysql
		//Class.forName(driver);
		//conn = DriverManager.getConnection(url,user,pass);
		Class.forName("org.sqlite.JDBC");
		//conn for linux
		//conn = DriverManager.getConnection("jdbc:sqlite:/Users/bone/myWork/source/hlldata.dll");
		//conn for Win
		conn = DriverManager.getConnection("jdbc:sqlite:F:\\Study\\saasWeb\\0716\\test\\hlldata.dll");
	}

	//获得连接对象
	public static Connection getConnection(){
		return conn;
	}

	//关闭连接
	public static void CloseConn() throws SQLException{
		conn.close();
	}
}
