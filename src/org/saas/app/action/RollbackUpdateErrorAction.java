package org.saas.app.action;

import org.saas.app.sqlTools.SQLConn;

public class RollbackUpdateError extends ActionSupport{
	public String execute() throws Exception{
		String sql_tables = "select name from sqlit。e_master where type='table' order by name;";
		String sql_columns = "PRAGMA table_info([?])";

		try{
			new SQLConn();
		}
		catch(Exception e){
			return ERROR;
		}
		
		try{
			ResultSet rs1 = null;
			String tableName;
			StringBuffer columns;
			PreparedStatement pstmt = SQLConn.getConnection().prepareStatement(sql_tables);
			rs1 = pstmt.executeQuery();
			while(rs1.next()){
				tableName = rs1.getString(1);
				if(tableName.endsWith("temp")){
					ResultSet rs2 = null;
					//pstmt是否需要重新定义一个？？
					pstmt = SQLConn.getConnection().prepareStatement(sql_columns);
					pstmt.setObject(1,tableName.substring(1,tableName.length() - 5));
					rs2 = pstmt.executeQuery();
					while(rs2.next()){
                         columns += rs2.getString(2);
					}

				}
			}
		}


	}
}