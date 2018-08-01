package org.saas.app.action;

import com.opensymphony.xwork2.ActionSupport;

import java.util.*;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.ResultSetMetaData;
import java.sql.PreparedStatement;
import org.saas.app.sqlTools.SQLConn;

public class ShowReportsAction extends ActionSupport{
	private String startDate;
	private String endDate;
	private String checkTypes; //异常类型
	List<String> colList = new ArrayList<>(); //结果中的行
	List<List<String>> rsList = new ArrayList<>();	//结果集

	public void setStartDate(String startDate){
		this.startDate = startDate;
	}
	public String getStartDate(){
		return this.startDate;
	}
	public void setEndDate(String endDate){
		this.endDate = endDate;
	}
	public String getEndDate(){
		return this.endDate;
	}
	public void setCheckTypes(String checkTypes){
		this.checkTypes = checkTypes;
	}
	public String getCheckTypes(){
		return this.checkTypes;
	}

	public List<String> getColList(){
		return colList;
	}
	public List<List<String>> getRsList(){
		return rsList;
	}


	//执行对应的SQL语句，取得相应的结果
	public String doQuery(String sql,Object... args) throws SQLException{
		//新建连接
		try{
			new SQLConn();
		}
		catch(Exception e){
			return ERROR;
		}

		//结果集
		ResultSet rs = null;
		//结果集增加序号：
		int count = 1;

		//执行查询
		try{
			PreparedStatement pstmt = SQLConn.getConnection().prepareStatement(sql);
			for(int i = 0; i < args.length; i++){
				pstmt.setObject(i + 1,args[i]);
			}
			rs = pstmt.executeQuery();
			ResultSetMetaData rsmd = rs.getMetaData();
			colList.add("序号");
			//将列标题加入list
			for(int i = 0; i < rsmd.getColumnCount(); i++){
				colList.add(rsmd.getColumnName(i + 1));
			}
			//将结果集加入rsList
			while(rs.next()){
				//保存一行数据的list
				List<String> oneRow = new ArrayList<>();
				//增加序号列
				oneRow.add(count + "");
				for(int i = 0; i < rsmd.getColumnCount(); i++){
					oneRow.add(rs.getString(i + 1));
				}
				count++;
				rsList.add(oneRow);
			}
			return SUCCESS;
		} catch(SQLException ex){
			return ERROR;
		} finally{
			SQLConn.CloseConn();
		}
	}

	public String execute() throws Exception{
		String sql = "";
		//异常一：流水<>实收+优惠
		if(checkTypes.equals("流水<>实收+优惠")){
			sql = "select saasOrderKey as [订单号],foodAmount as [流水],promotionAmount as [优惠金额],paidAmount as [实收金额],round((foodAmount - promotionAmount - paidAmount),2) as [差值] from(select saasOrderKey,foodAmount,promotionAmount,paidAmount,round((foodAmount - promotionAmount - paidAmount),2) from tbl_saas_order_master where reportDate between ? and ? and round(foodAmount,4) <> round((promotionAmount + paidAmount),4) and orderStatus = 40 union all select saasOrderKey,foodAmount,promotionAmount,paidAmount,round((foodAmount - promotionAmount - paidAmount),2) from tbl_saas_order_master_his where reportDate between ? and ? and round(foodAmount,4) <> round((promotionAmount + paidAmount),4) and orderStatus = 40)";
			return this.doQuery(sql,getStartDate(),getEndDate(),getStartDate(),getEndDate());
		}
		//异常二：流水<>支付科目明细之和
		else if(checkTypes.equals("流水<>支付科目明细之和")){
			sql = "select saasOrderKey as [订单号],foodAmount as [流水],case when round(foodAmount,4) <> round((select sum(debitAmount) from tbl_saas_order_pay as p1 where p1.saasOrderKey = m.saasOrderKey),4) then (select sum(debitAmount) from tbl_saas_order_pay as p2 where p2.saasOrderKey = m.saasOrderKey) else '' end as [支付科目合计],round((foodAmount - (select sum(debitAmount) from tbl_saas_order_pay as p3 where p3.saasOrderKey = m.saasOrderKey)),4) as [差值] from tbl_saas_order_master as m where [支付科目合计] <> '' and reportDate between ? and ? and orderStatus = 40 union all select saasOrderKey as [订单号],foodAmount as [流水],case when round(foodAmount,4) <> round((select sum(debitAmount) from tbl_saas_order_pay_his as p1 where p1.saasOrderKey = m.saasOrderKey),4) then (select sum(debitAmount) from tbl_saas_order_pay_his as p2 where p2.saasOrderKey = m.saasOrderKey) else '' end as [支付科目合计],round((foodAmount - (select sum(debitAmount) from tbl_saas_order_pay_his as p3 where p3.saasOrderKey = m.saasOrderKey)),4) as [差值] from tbl_saas_order_master_his as m where [支付科目合计] <> '' and reportDate between ? and ? and orderStatus = 40";
			return this.doQuery(sql,getStartDate(),getEndDate(),getStartDate(),getEndDate());
		}
		//异常三：流水<>菜品销售金额合计
		else if(checkTypes.equals("流水<>菜品销售金额合计")){
			sql = "select saasOrderKey as [订单号],foodAmount as [流水],case when round(foodAmount,4) <> round((select sum(foodPriceAmount) from tbl_saas_order_food_his as f1 where f1.saasOrderKey = m.saasOrderKey),4) then (select sum(foodPriceAmount) from tbl_saas_order_food_his as f2 where f2.saasOrderKey = m.saasOrderKey) else '' end as [菜品销售金额合计],round((foodAmount - (select sum(foodPriceAmount) from tbl_saas_order_food_his as f3 where f3.saasOrderKey = m.saasOrderKey)),4) as [差值] from tbl_saas_order_master_his as m where [菜品销售金额合计] <> '' and reportDate between ? and ? and orderStatus = 40 union all select saasOrderKey as [订单号],foodAmount as [流水],case when round(foodAmount,4) <> round((select sum(foodPriceAmount) from tbl_saas_order_food as f1 where f1.saasOrderKey = m.saasOrderKey),4) then (select sum(foodPriceAmount) from tbl_saas_order_food as f2 where f2.saasOrderKey = m.saasOrderKey) else '' end as [菜品销售金额合计],round((foodAmount - (select sum(foodPriceAmount) from tbl_saas_order_food as f3 where f3.saasOrderKey = m.saasOrderKey)),4) as [差值] from tbl_saas_order_master as m where [菜品销售金额合计] <> '' and reportDate between ? and ? and orderStatus = 40";
			return this.doQuery(sql,getStartDate(),getEndDate(),getStartDate(),getEndDate());
		}
		//异常四：实收<>实收科目合计
		else if(checkTypes.equals("实收<>实收科目合计")){
			sql = "select saasOrderKey as [订单号],paidAmount as [实收],case when round(paidAmount,4) <> round((select sum(paySubjectRealAmount) from tbl_saas_order_pay as p1 where p1.saasOrderKey = m.saasOrderKey),4) then (select sum(paySubjectRealAmount) from tbl_saas_order_pay as p2 where p2.saasOrderKey = m.saasOrderKey) else '' end as [支付科目合计],round((paidAmount - (select sum(paySubjectRealAmount) from tbl_saas_order_pay as p3 where p3.saasOrderKey = m.saasOrderKey)),4) as [差值] from tbl_saas_order_master as m where [支付科目合计] <> '' and reportDate between ? and ? and orderStatus = 40 union all select saasOrderKey as [订单号],paidAmount as [实收],case when round(paidAmount,4) <> round((select sum(paySubjectRealAmount) from tbl_saas_order_pay_his as p1 where p1.saasOrderKey = m.saasOrderKey),4) then (select sum(paySubjectRealAmount) from tbl_saas_order_pay_his as p2 where p2.saasOrderKey = m.saasOrderKey) else '' end as [支付科目合计],round((paidAmount - (select sum(paySubjectRealAmount) from tbl_saas_order_pay_his as p3 where p3.saasOrderKey = m.saasOrderKey)),4) as [差值] from tbl_saas_order_master_his as m where [支付科目合计] <> '' and reportDate between ? and ? and orderStatus = 40";
			return this.doQuery(sql,getStartDate(),getEndDate(),getStartDate(),getEndDate());
		}
		//异常五：实收<>菜品实收金额合计
		else if(checkTypes.equals("实收<>菜品实收金额合计")){
			sql = "select saasOrderKey as [订单号],paidAmount as [实收],case when round(paidAmount,4) <> round((select sum(foodRealAmount) from tbl_saas_order_food as f1 where f1.saasOrderKey = m.saasOrderKey),4) then (select sum(foodRealAmount) from tbl_saas_order_food as f2 where f2.saasOrderKey = m.saasOrderKey) else '' end as [菜品实收合计],round((paidAmount - (select sum(foodRealAmount) from tbl_saas_order_food as p3 where p3.saasOrderKey = m.saasOrderKey)),4) as [差值] from tbl_saas_order_master as m where [菜品实收合计] <> '' and reportDate between ? and ? and orderStatus = 40 union all select saasOrderKey as [订单号],paidAmount as [实收],case when round(paidAmount,4) <> round((select sum(foodRealAmount) from tbl_saas_order_food_his as f1 where f1.saasOrderKey = m.saasOrderKey),4) then (select sum(foodRealAmount) from tbl_saas_order_food_his as f2 where f2.saasOrderKey = m.saasOrderKey) else '' end as [菜品实收合计],round((paidAmount - (select sum(foodRealAmount) from tbl_saas_order_food_his as f3 where f3.saasOrderKey = m.saasOrderKey)),4) as [差值] from tbl_saas_order_master_his as m where [菜品实收合计] <> '' and reportDate between ? and ? and orderStatus = 40";
			return this.doQuery(sql,getStartDate(),getEndDate(),getStartDate(),getEndDate());
		}
		//六：会员储值明细
		else if(checkTypes.equals("会员储值明细")){
			sql = "select cardID as [卡号],cardTransID as [交易ID],case when transType <>  30 then transAmount else case when transType = 30 then (0 - transAmount) else '' end end as [实收金额],saveReturnAmount as [赠送金额],saveReturnPoint as [赠送积分],createTime as [交易时间] from tbl_saas_card_trans_detail where substr(createTime,1,8) between ? and ? and (transAmount <> 0 or saveReturnAmount <> 0 or saveReturnPoint <> 0)";
			return this.doQuery(sql,getStartDate(),getEndDate());
		}
		//七：综合营业报表账单明细
		else if(checkTypes.equals("综合营业报表账单明细")){
			sql = "select saasOrderKey as [订单号],tableName as [桌台号],foodAmount as [流水],promotionAmount as [优惠金额],paidAmount as [实收金额] from tbl_saas_order_master where checkoutTime between ? and ? and orderStatus = 40 union all select saasOrderKey as [订单号],tableName as [桌台号],foodAmount as [流水],promotionAmount as [优惠金额],paidAmount as [实收金额] from tbl_saas_order_master_his where checkoutTime between ? and ? and orderStatus = 40 order by [订单号]";
			return this.doQuery(sql,getStartDate(),getEndDate(),getStartDate(),getEndDate());
		}
		//八：综合营业报表支付明细
		else if(checkTypes.equals("综合营业报表支付明细")){
			sql = "select saasOrderKey as [订单号],paySubjectName as [科目名称],debitAmount as [抵扣金额],paySubjectRealAmount as [实收金额],case when isJoinReceived = 1 then '是' when isJoinReceived = 0 then '否' end as [是否计入实收] from tbl_saas_order_pay where saasOrderKey in(select saasOrderKey from tbl_saas_order_master where checkoutTime between ? and ? and orderStatus = 40) union all select saasOrderKey as [订单号],paySubjectName as [科目名称],debitAmount as [抵扣金额],paySubjectRealAmount as [实收金额],case when isJoinReceived = 1 then '是' when isJoinReceived = 0 then '否' end as [是否计入实收] from tbl_saas_order_pay_his where saasOrderKey in(select saasOrderKey from tbl_saas_order_master_his where checkoutTime between ? and ? and orderStatus = 40) order by saasOrderKey";
			return this.doQuery(sql,getStartDate(),getEndDate(),getStartDate(),getEndDate());
		}
		//九：综合营业报表菜品明细
		else if(checkTypes.equals("综合营业报表菜品明细")){
			sql = "select saasOrderKey as [订单号],foodName as [菜品名称],foodPriceAmount as [折前金额],foodRealAmount as [折后实收] from tbl_saas_order_food where saasOrderKey in(select saasOrderKey from tbl_saas_order_master where checkoutTime between ? and ? and orderStatus = 40) union all select saasOrderKey as [订单号],foodName as [菜品名称],foodPriceAmount as [折前金额],foodRealAmount as [折后实收] from tbl_saas_order_food_his where saasOrderKey in(select saasOrderKey from tbl_saas_order_master_his where checkoutTime between ? and ? and orderStatus = 40)";
			return this.doQuery(sql,getStartDate(),getEndDate(),getStartDate(),getEndDate());
		}
		else{
			return ERROR;
		}
	}
}
