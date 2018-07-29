package org.saas.app.action;

import com.opensymphony.xwork2.ActionSupport;

import java.io.FileNotFoundException;
import java.io.FileReader;
 
import com.google.gson.JsonElement;
import com.google.gson.JsonArray;
import com.google.gson.JsonIOException;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;

import java.util.*;

public class checkSetFoodAction extends ActionSupport{
	//List<String> titleList = new ArrayList<>(); //表头直接在jsp里面通过list实现
	List<List<String>> foodDetailList = new ArrayList<>();

	//public List<String> getTitleList(){
	//	return titleList;
	//}
	public List<List<String>> getFoodDetailList(){
		return foodDetailList;
	}

	public String execute() throws Exception{
		JsonParser parse = new JsonParser(); //创建Json解析器
		ArrayList<String> foodKeys = new ArrayList<>();
		ArrayList<String> foodNames = new ArrayList<>();

		try{
			JsonObject json = (JsonObject)parse.parse(new FileReader("F:\\Study\\SaaS\\saasJson\\0725\\src\\foodLst.cdata"));
			
			JsonArray array = json.get("data").getAsJsonObject().get("records").getAsJsonArray(); //获取foodLst数组
			//维护foodKeys
			for(int i = 0; i < array.size(); i++){
				JsonObject foodLst = array.get(i).getAsJsonObject();
				foodKeys.add(foodLst.get("foodKey").getAsString()); //获取菜品的foodKey，维护foodKeys
				foodNames.add(foodLst.get("foodName").getAsString()); //获取菜品的foodName,维护foodNames
			}
			
			for(int i = 0; i < array.size(); i++){
				JsonObject foodLst = array.get(i).getAsJsonObject();
				String foodName = foodLst.get("foodName").getAsString();
				JsonElement setFoodLst_temp = foodLst.get("setFoodDetailJson");
				//如果是JsonObject说明有套餐明细，是套餐
				if(setFoodLst_temp.isJsonObject()){
					JsonObject setFoodLst = setFoodLst_temp.getAsJsonObject();
					JsonArray foodLstArray = setFoodLst.get("foodLst").getAsJsonArray();
					for(int j = 0; j < foodLstArray.size(); j++){
						JsonArray SFItemArray = foodLstArray.get(j).getAsJsonObject().get("items").getAsJsonArray();
						//遍历套餐明细并且判断是否有对应的单菜品
						for(int k = 0; k < SFItemArray.size(); k++){
							//保存一行数据的list
							List<String> resultDesc = new ArrayList<>();
							String setfoodkey = SFItemArray.get(k).getAsJsonObject().get("foodKey").getAsString();
							if(!foodKeys.contains(setfoodkey)){
								String SFDetailName = SFItemArray.get(k).getAsJsonObject().get("foodName").getAsString();
								if(foodNames.contains(SFDetailName)){//
									resultDesc.add(foodName);
									resultDesc.add(SFDetailName);
									resultDesc.add(setfoodkey);
									resultDesc.add("是");
									//System.out.println("套餐" + foodName + "的明细菜品：" + SFDetailName + "缺失对应的单菜品，菜品的key为：" + setfoodkey + ",有同名的单菜品，但是foodKey不一致！！");
								}
								else{
									resultDesc.add(foodName);
									resultDesc.add(SFDetailName);
									resultDesc.add(setfoodkey);
									resultDesc.add("否");
									//System.out.println("套餐" + foodName + "的明细菜品：" + SFDetailName + "缺失对应的单菜品，菜品的key为：" + setfoodkey);
								}
							}
							foodDetailList.add(resultDesc);
						}
					}
				}
				else{
					continue;
					//System.out.println("不是套餐！！");
				}
			}
			return SUCCESS;
		} catch (JsonIOException e) {
            e.printStackTrace();
            return ERROR;
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
            return ERROR;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return ERROR;
        }
	}
}

