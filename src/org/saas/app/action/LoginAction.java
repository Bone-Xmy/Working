package org.saas.app.action;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ActionContext;

import java.util.*;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.saas.app.sqlTools.DbDao;
import org.saas.app.Tools.Journal;

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
		DbDao dd = new DbDao();
		String sql = "select count(*) as amount from tbl_saas_emp where userName = ? and password = ? ";
		//执行查询
		ResultSet rs = dd.doQuery(sql,false,getUsername(),getPassword());

		//获取ActionContext
		ActionContext ctx = ActionContext.getContext();
		Map<String,Object> session = ctx.getSession();

		if(rs.next()){
			if(Integer.parseInt(rs.getString("amount")) >= 1){
				session.put("user",getUsername());
				return SUCCESS;
			}
			return LOGIN;
		}
		dd.closeConn();
		return ERROR;
	}
}
