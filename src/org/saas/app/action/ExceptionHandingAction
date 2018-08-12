package org.saas.app.action;

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
		return this.isHis;
	}

	public String execute() throws Exception{
		if(getExTypes.equals("所支付金额与应收金额不符")){
			if(getIsHis().equals("否")){
				mySql = "delete from tbl_saas_order_pay where saasOrderKey in(" + getSaasOrderKey() + ");";
				return SUCCESS;
			}
			else{
				mySql = "delete from tbl_saas_order_pay_his where saasOrderKey in(" + getSaasOrderKey() + ");"
			}
		}
		else if(getExTypes.equals("删除订单号为空的数据(单号很长)")){
			return SUCCESS;
		}
		else if(getExTypes.equals("会员消费补单子")){
			return SUCCESS;
		}
	}
}