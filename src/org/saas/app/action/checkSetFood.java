import java.io.FileNotFoundException;
import java.io.FileReader;
 
import com.google.gson.JsonArray;
import com.google.gson.JsonIOException;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;

import java.util.*;

public class checkSetFood{
	public static void main(String[] args){
		JsonParser parse = new JsonParser(); //创建Json解析器
		ArrayList<String> foodKeys = new ArrayList<>();
		try{
			JsonObject json = (JsonObject)parse.parse(new FileReader("F:\\Study\\SaaS\\saasJson\\src\\foodLst.cdata"));
			
			JsonArray array = json.get("data").getAsJsonObject().get("records").getAsJsonArray(); //获取foodLst数组
			//维护foodKeys
			for(int i = 0; i < array.size(); i++){
				JsonObject foodLst = array.get(i).getAsJsonObject();
				foodKeys.add(foodLst.get("foodKey").getAsString()); //获取菜品的foodKey，维护foodKeys
			}
			
			for(int i = 0; i < array.size(); i++){
				JsonObject foodLst = array.get(i).getAsJsonObject();
				JsonObject setFoodLst = foodLst.get("setFoodDetailJson").getAsJsonObject();
				//如果有套餐明细，则获取套餐明细
				if(setFoodLst != null){
					JsonArray foodLstArray = setFoodLst.get("foodLst").getAsJsonArray();
					for(int j = 0; j < foodLstArray.size(); j++){
						JsonArray SFItemArray = foodLstArray.get(j).getAsJsonObject().get("items").getAsJsonArray();
						//遍历套餐明细并且判断是否有对应的单菜品
						for(int k = 0; k < SFItemArray.size(); k++){
							String setfoodkey = SFItemArray.get(k).getAsJsonObject().get("foodKey").getAsString();
							if(foodKeys.contains(setfoodkey)){
								System.out.println("不包含" + setfoodkey);
							}
						}
					}
				}
			}
		} catch (JsonIOException e) {
            e.printStackTrace();
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
	}
}