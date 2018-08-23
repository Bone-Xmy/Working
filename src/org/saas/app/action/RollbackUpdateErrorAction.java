package org.saas.app.action;

import org.saas.app.sqlTools.DbDao;

import com.opensymphony.xwork2.ActionSupport;

import java.sql.*;
import java.util.ArrayList;

public class RollbackUpdateErrorAction extends ActionSupport{
	public String execute() throws Exception{
		//保存targetSqls的List
		ArrayList<String> sqls = new ArrayList<>();
		DbDao dd = new DbDao();
		String sql_tables = "select name from sqlit。e_master where type='table' order by name;";
		String sql_columns = "PRAGMA table_info([?])";
		//处理的表
		String tableName;
		//表的列名（字段名）
		StringBuffer columns = new StringBuffer();

		ResultSet rs1 = dd.doQuery(sql_tables);
		
		while(rs1.next()){
			tableName = rs1.getString(1);
			if(tableName.endsWith("his_temp")){
				ResultSet rs2 = dd.doQuery(sql_columns,tableName.substring(1,tableName.length() - 5));
				while(rs2.next()){
					//StringBuffer后面追加字符串
					columns.append(rs2.getString(2) + ",");
				}
				//StringBuffer转换为String，且去掉最后吗的逗号
				String tempColumns = columns.toString().substring(1,columns.toString().length() - 1);
				String tempInsert = "insert into " 
					+ tableName.substring(1,tableName.length() - 5)
					+ "("
					+ tempColumns
					+ ")"
					+ "select" 
					+ tempColumns
					+ "from"
					+ tableName;
				String tempDrop = "drop table" + tableName;

				sqls.add(tempInsert);
				sqls.add(tempDrop);

				dd.batchProcessSqls(sqls);
				//只能在执行完成后再执行clear()
				sqls.clear();
			}
			else if(tableName.endsWith("temp")){
				ResultSet rs2 = dd.doQuery(sql_columns,tableName.substring(1,tableName.length() - 5));
				//记录指针移到第二行
				rs2.absolute(2);
				while(rs2.next()){
					columns.append(rs2.getString(2) + ",");
				}
				//StringBuffer转换为String，且去掉最后吗的逗号
				String tempColumns = columns.toString().substring(1,columns.toString().length() - 1);
				String tempInsert = "insert into " 
					+ tableName.substring(1,tableName.length() - 5)
					+ "("
					+ tempColumns
					+ ")"
					+ "select" 
					+ tempColumns
					+ "from"
					+ tableName;
				String tempDrop = "drop table" + tableName;

				sqls.add(tempInsert);
				sqls.add(tempDrop);

				dd.batchProcessSqls(sqls);
				//只能在执行完成后再执行clear()
				sqls.clear();
			}
		}
		dd.closeConn();
		return SUCCESS;
	}
}