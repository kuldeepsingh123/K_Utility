package org.ngi.json;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class K_CreateJson {

	public static  void main(String []str){
		
	
	JSONObject jo = new JSONObject();
	jo.put("firstName", "John");
	jo.put("lastName", "Doe");

	JSONArray ja = new JSONArray();
	ja.add(jo);
	JSONObject mainObj = new JSONObject();
	mainObj.put("employees", ja);
	
	System.out.println(mainObj.toJSONString());
	}
	
}
