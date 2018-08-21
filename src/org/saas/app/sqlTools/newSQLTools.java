package org.saas.app.sqlTools;

import org.saas.app.Tools.Journal;

import com.opensymphony.xwork2.ActionContext;
import org.apache.struts2.ServletActionContext;

import java.sql.*;


public class DbDao
{
	private Connection conn;
	private String sqlFile;

	public DbDao(){
	}
	
	//sqlFile的getter方法
	public String getSqlFile(){
		//获取ActionContext
		ActionContext ctx = ActionContext.getContext();
		Map<String,Object> session = ctx.getSession();
		
		if(((String)session.get("user")) != null && ((String)session.get("uploadDir")) != null){
			Journal.writeLog("连接文件：" + (String)session.get("uploadDir") + "/hlldata.dll");
			return (String)session.get("uploadDir") + "/hlldata.dll";
			
		}
		else{
			if(ServletActionContext.getServletContext().getInitParameter("envType").equals("mac")){
				Journal.writeLog("环境为mac");
				return "/Users/bone/myWork/source/hlldata.dll";
			}
			else{
				Journal.writeLog("环境为win");
				return "E:/source/hlldata.dll";
			}
		}
	}

	//获取连接
	public Connection getConnection(){
		if(conn == null){
			Class.forName("org.sqlite.JDBC");
			String connSQLFile = "jdbc:sqlite:" + getSqlFile();
			conn = DriverManager.getConnection(connSQLFile);
		}
		return conn;
	}

	// 插入记录
	public boolean insert(String sql , Object... args)
		throws Exception
	{
		PreparedStatement pstmt = getConnection().prepareStatement(sql);
		for (int i = 0; i < args.length ; i++ )
		{
			pstmt.setObject( i + 1 , args[i]);
		}
		if (pstmt.executeUpdate() != 1)
		{
			return false;
		}
		pstmt.close();
		return true;
	}
	// 执行查询
	public ResultSet query(String sql , boolean updatable , Object... args )
		throws Exception
	{
		PreparedStatement pstmt = null;
		if (updatable)
		{
			// 创建可更新的PreparedStatement
			pstmt = getConnection().prepareStatement(sql
				, ResultSet.TYPE_SCROLL_INSENSITIVE
				, ResultSet.CONCUR_UPDATABLE);
		}
		else
		{
			pstmt = getConnection().prepareStatement(sql);
		}
		for (int i = 0; i < args.length ; i++ )
		{
			pstmt.setObject( i + 1 , args[i]);
		}
		return pstmt.executeQuery();
	}
	// 执行修改
	public void modify(String sql , Object... args)
		throws Exception
	{
		PreparedStatement pstmt = getConnection().prepareStatement(sql);
		for (int i = 0; i < args.length ; i++ )
		{
			pstmt.setObject(i + 1 , args[i]);
		}
		pstmt.executeUpdate();
		pstmt.close();
	}
	// 关闭数据库连接的方法
	public void closeConn()
		throws Exception
	{
		if (conn != null && !conn.isClosed())
		{
			conn.close();
		}
	}
}