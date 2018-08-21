package org.saas.app.action;

import org.saas.app.sqlTools.DbDao;

import com.opensymphony.xwork2.ActionSupport;

import java.sql.*;

public class RollbackUpdateErrorAction extends ActionSupport{
	public String execute() throws Exception{
		DbDao dd = new DbDao();
		String sql_tables = "select name from sqlit。e_master where type='table' order by name;";
		String sql_columns = "PRAGMA table_info([?])";
		//处理的表
		String tableName;
		//表的列名（字段名）
		String columns = "";

		ResultSet rs1 = dd.doQuery(sql_tables);
		
		while(rs1.next()){
			tableName = rs1.getString(1);
			if(tableName.endsWith("temp")){
				ResultSet rs2 = dd.doQuery(sql_columns,tableName.substring(1,tableName.length() - 5));
				while(rs2.next()){
					columns += rs2.getString(2);
				}
			}
		}
		//test
		return SUCCESS;
	}
}