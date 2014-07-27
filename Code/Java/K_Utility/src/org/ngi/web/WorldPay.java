package org.ngi.web;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
 
public class WorldPay {
 
	// http://localhost:8080/RESTfulExample/json/product/post
	public static void main(String[] args) {
 
	  try {
 
		URL url = new URL("https://secure-test.worldpay.com/jsp/merchant/xml/paymentService.jsp");
		//URL url = new URL("https://admin@spacemholdin:live2014jay@secure-test.wp3.rbsworldpay.com/jsp/merchant/xml/paymentService.jsp");
		//URL url = new URL("http://216.49.149.88:22003/stats_webservices/Clustering");
		//URL url = new URL("http://localhost:8080/stats_webservices/Clustering");
		  //URL url = new URL("http://localhost:8080/stats_webservices/Clustering");
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setDoOutput(true);
		conn.setRequestMethod("POST");
		
		conn.setRequestProperty("Content-Type", "text/xml; charset=utf-8");
		conn.setRequestProperty("Accept-Language", "en-us,en;q=0.5");
		conn.setRequestProperty("Accept-Encoding", "gzip,deflate");
		conn.setRequestProperty("Accept-Charset", "ISO-8859-1,utf-8;q=0.7,*;q=0.7");

		
 
		//String input = "Data={\"DataPoints\":[{ \"X\":\"0\" , \"Y\":\"0\" },{ \"X\":\"1\" , \"Y\":\"3\"},{ \"X\":\"5\" , \"Y\":\"33\"},{ \"X\":\"7\" , \"Y\":\"45\"},{ \"X\":\"11\" , \"Y\":\"83\"},{ \"X\":\"15\" , \"Y\":\"133\"}]}&NumberOfClusters=4&IsTesting=true";
		String input = "<?xml version=\"1.0\" encoding=\"UTF-8\"?> " +
				"<!DOCTYPE paymentService PUBLIC \"-//WorldPay//DTD WorldPay PaymentService v1//EN\" \"http://dtd.worldpay.com/paymentService_v1.dtd\"> " +
				"<paymentService version=\"1.4\" merchantCode=\"SPACEMHOLDINM1\"> <submit> " +
				"<order orderCode=\"T0211010\" installationId=\"1009530\"> " +
				"<description>20 English roses from MYMERCHANT Webshop</description> " +
				"<amount value=\"2600\" currencyCode=\"EUR\" exponent=\"2\"/> <paymentDetails> <VISA-SSL> " +
				"<cardNumber>4444333322221111</cardNumber> <expiryDate> <date month=\"09\" year=\"2019\"/> </expiryDate> " +
				"<cardHolderName>J. Shopper</cardHolderName> <cvc>123</cvc> <cardAddress> <address> " +
				"<street>47A Queensbridge Rd</street> <postalCode>CB94BQ</postalCode> " +
				"<countryCode>GB</countryCode> </address> </cardAddress> </VISA-SSL> " +
				"<session shopperIPAddress=\"100.100.100.100\" id=\"0215ui8ib1\" /> </paymentDetails> " +
				"<shopper> <shopperEmailAddress>jshopper@myprovider.int</shopperEmailAddress> " +
				"<browser> <acceptHeader>text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8</acceptHeader>" +
				"<userAgentHeader>Mozilla/5.0 (Windows; U; Windows NT 5.1; en-GB; rv:1.9.1.5) Gecko/20091102 Firefox/3.5.5 (.NET CLR 3.5.30729)</userAgentHeader> " +
				"</browser> </shopper> </order> </submit> </paymentService>";
		
		OutputStream os = conn.getOutputStream();
		os.write(input.getBytes());
		os.flush();
 
		if (conn.getResponseCode() != HttpURLConnection.HTTP_CREATED) {
			throw new RuntimeException("Failed : HTTP error code : "
				+ conn.getResponseCode());
		}
 
		BufferedReader br = new BufferedReader(new InputStreamReader(
				(conn.getInputStream())));
 
		String output;
		System.out.println("Output from Server .... \n");
		while ((output = br.readLine()) != null) {
			System.out.println(output);
		}
 
		conn.disconnect();
 
	  } catch (MalformedURLException e) {
 
		e.printStackTrace();
 
	  } catch (IOException e) {
 
		e.printStackTrace();
 
	 }
 
	}
	
	
	
	
 
}