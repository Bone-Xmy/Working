package org.saas.app.action;

import org.saas.app.Tools.Journal;
import com.opensymphony.xwork2.ActionSupport;

public class ExceptionHandingAction extends ActionSupport{
	private String mySql;
	private String exTypes;
	private String saasOrderKey;
	private String isHis;

	public void setMySql(String mySql){
		this.mySql = mySql;
	}
	public String getMySql(){
		return this.mySql;
	}
	public void setSaasOrderKey(String saasOrderKey){
		this.saasOrderKey = saasOrderKey;
	}
	public String getSaasOrderKey(){
		return this.saasOrderKey;
	}
	public void setExTypes(String exTypes){
		this.exTypes = exTypes;
	}
	public String getExTypes(){
		return this.exTypes;
	}
	public void setIsHis(String isHis){
		this.isHis = isHis;
	}
	public String getIsHis(){
		if(isHis == null){
			isHis = "否";
		}
		return this.isHis;
	}

	public String execute() throws Exception{
		Journal.writeLog("exHanding开始生成Sql...");
		if(getExTypes().equals("所支付金额与应收金额不符")){
			if(getIsHis().equals("否")){
				mySql = "delete from tbl_saas_order_pay where saasOrderKey in(" + getSaasOrderKey() + ");";
				Journal.writeLog("生成指定Sql：" + mySql);
				return SUCCESS;
			}
			else{
				mySql = "delete from tbl_saas_order_pay_his where saasOrderKey in(" + getSaasOrderKey() + ");";
				Journal.writeLog("生成指定Sql：" + mySql);
				return SUCCESS;
			}
		}
		else if(getExTypes().equals("删除订单号为空的数据(单号很长)")){
			mySql = "delete from tbl_saas_order_master where saasOrderKey = '';update tbl_saas_table set tableStatus = 0 where tableStatus = 1 and saasOrderKey = '';";
		}
		else if(getExTypes().equals("会员消费补单子")){
			return SUCCESS;
		}
		return ERROR;
	}
}
