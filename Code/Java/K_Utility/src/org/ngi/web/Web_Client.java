package org.ngi.web;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
 
public class Web_Client {
	
	private static String webServicePath="http://206.190.151.66:8080/stats_webservices/Simulation";
	private static String inputString="Data={\"DataPoints\": [{\"Price\":\"100\",\"Cost\":\"15\",\"Mean\":\"0\",\"StandardDeviation\":\"1\"},{\"Price\":\"10\",\"Cost\":\"1\",\"Mean\":\"0\",\"StandardDeviation\":\"1\"}]}&Model=Lognormal&PlotParameters={\"XLabel\":\"Commission %\",\"YLabel\":\"Profit\"}&IsTesting=False";
	private static int limit = 500;
	private static long sleepTime = 300;

	
	/**
	 * 
	 * @param args
	 */
	public static void main(String args[]){
		
		readParam(args);
		
		System.out.println("WebServicePath= "+webServicePath + "\ninputString="+inputString+" Limit="+limit+"\n\n");
		
		for(int i=0;i<limit;i++){
			try {
				Thread.sleep(sleepTime);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			String response = postClient(webServicePath,inputString);
			System.out.println("index="+i+"\nResponse= "+response+"\n");
		}
	}
	
	
	/**
	 * 
	 * @param args
	 */
	private static void readParam(String [] args)
	{
		if(args.length==3)
		{
			webServicePath = args[0];
			inputString = args[1];
			limit = Integer.parseInt(args[2]);
		}
	}
	
	
	/**
	 * 
	 * @param str
	 * @return
	 */
	private static String encode(String str)
	{
		String strArr[]=str.split("&");
		StringBuffer sb = new StringBuffer();
		for(String strParam : strArr){
			String keyValueArr[] = strParam.split("=");
			try {
				sb.append(keyValueArr[0]+"="+URLEncoder.encode(keyValueArr[1],"UTF-8")+"&");
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		return sb.toString();
	}
	
	
	/**
	 * 
	 * @param webServicePath
	 * @param input
	 * @return
	 */
	public static String postClient(String webServicePath,String input) 
	{
		StringBuilder responseMessage=new StringBuilder();
		
		try
		{
			URL url = new URL(webServicePath);
			URLConnection conn = url.openConnection();
			conn.setDoOutput(true); 
			conn.setRequestProperty("Accept-Charset", "UTF-8");
			conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=" + "UTF-8");
			
			OutputStream output = conn.getOutputStream();
			
			input = encode(input);
			output.write(input.getBytes("UTF-8"));
			
			BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream(),"UTF-8"));
			
			String tempText;
			
			while((tempText=reader.readLine()) !=null)
				responseMessage.append(tempText);
			
			reader.close();
			
		}catch(Exception e){
			e.printStackTrace();
		}
			
		return responseMessage.toString();
	}
}