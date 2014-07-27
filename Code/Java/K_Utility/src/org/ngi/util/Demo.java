package org.ngi.util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

public class Demo extends Animal{
	
	public static void main(String stree[])
	{
		
		
		try{
			BufferedReader br = new BufferedReader(new FileReader("source/temp.csv"));
			String str = br.readLine();
			String [] arr = str.split(",");
			String p = arr[1];
			System.out.println(arr[0]+""+arr[1]+""+arr[2]);
			
			/*String str2 = "\\\n";
			str2 = str2.replace("\\", "");
			System.out.println("hi"+str2+"kuldeep");
			*/
			
		}catch(Exception e){
			e.printStackTrace();
		}
		/*Map<String,Integer> map = new HashMap<String, Integer>();
		map.put("k1", 1);
		map.put("k2", 2);
		
		Iterator<Entry<String,Integer>> it = map.entrySet().iterator();
		while(it.hasNext())
		{
			Entry<String,Integer> en = (Map.Entry<String, Integer>) it.next();
			System.out.println(en.getKey()+","+en.getValue());
		}
		
		Demo d = new Demo();
		
		System.out.println(d.getA(null));*/
	}
	
	private String getA(Animal i){
		return i+"getA...Integer";
	}

	private String getA(Demo s){
		return s+"getA";
	}
	
	
}

class Animal{
	
}
