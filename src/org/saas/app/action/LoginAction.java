package org.saas.app.action;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ActionContext;

import java.util.*;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.saas.app.sqlTools.SQLConn;

public class LoginAction extends ActionSupport{
	private String username;
	private String password;

	public void setUsername(String username){
		this.username = username;
	}
	public String getUsername(){
		return this.username;
	}
	public void setPassword(String password){
		this.password = password;
	}
	public String getPassword(){
		return this.password;
	}

	public String execute() throws Exception{
		//新建连接
		try{
			new SQLConn();
		}
		catch(Exception e){
			e.printStackTrace();
			return LOGIN;
		}
		//查询账号的SQL
		String sql = "select count(*) as amount from tbl_saas_emp where userName = '" + username + "' and password = '" + password + "'";
		ResultSet rs = null;

		//获取ActionContext
		ActionContext ctx = ActionContext.getContext();
		Map<String,Object> session = ctx.getSession();

		//执行查询
		try{
			rs = SQLConn.getConnection().createStatement().executeQuery(sql);
			if(rs.next()){
				if(Integer.parseInt(rs.getString("amount")) >= 1){
					session.put("user",getUsername());
					return SUCCESS;
				}
				else{
					return LOGIN;
				}
			}
		} catch(SQLException ex){
			ex.printStackTrace();
			return LOGIN;
		}	finally{
			SQLConn.CloseConn();
		}
		return ERROR;
	}
}
