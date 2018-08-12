package org.saas.app.action;

import com.opensymphony.xwork2.ActionSupport;

public class ProduceTargetSQLAction extends ActionSupport{
    private String errorType;
    private String targetSQL;

    public void setErrorType(String errorType){
	this.errorType = errorType;
    }
    public String getErrorType(){
	return this.errorType;
    }

    public String execute() throws Exception{
    	if(errorType.equals("所支付金额与应收金额不符")){
    		targetSQL = "";
    		return SUCCESS;
    	}
    	else if(errorType.equals("")){
    		return SUCCESS;
    	}
    	return ERROR;
    }
}
