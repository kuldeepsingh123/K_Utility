package org.ngi.web;
/*
 * submitXMLExample.java
 *
 * Created on April 4, 2001, 5:06 PM
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * @author  Robert Kraal, WorldPay BV
 * $date$
 * $revision$ 010502 - a3 removed admincode
 * 010516 rk ordercode
 * This class requires jsse.jar, jnet.jar and jcert.jar in your classpath
 * More information can be found at http://java.sun.com/products/jsse/
 */
public class SubmitXMLExample{
	// Enter the MerchantCode you have retrieved from WorldPay
	private final static String merchantCode = "SPACEMHOLDINM1";

	// Enter the username you have retrieved from WorldPay
	// The username will generally be the merchantCode
	private final static String userName = "SPACEMHOLDINM1";//"admin@spacemholdin";
	
	// Enter the password you have retrieved from WorldPay
	private final static String passWord = "rM4mxPAt";

	// Enter here the URL
	// for the production environment use: https://secure.worldpay.com:443/jsp/merchant/xml/paymentService.jsp
	// for the test environment use: https://secure-test.worldpay.com:443/jsp/merchant/xml/paymentService.jsp
	private final static String location = "https://secure-test.worldpay.com:443/jsp/merchant/xml/paymentService.jsp";
	
	private static HttpURLConnection huc;
	
	/** Creates new submitXMLExample */
	public SubmitXMLExample() {
	}
	
	/**
	 * @param args the command line arguments
	 */
	public static void main (String args[]) {
		//Security.addProvider(new com.sun.net.ssl.internal.ssl.Provider());
		System.setProperty("java.protocol.handler.pkgs", "com.sun.net.ssl.internal.www.protocol");
		System.out.println(createXML());
		
		sendXml(createXML());
		System.out.println();
	}
	
	/*
	 * creates an XML message
	 * In this case an XML message is composed that sends a single order with a paymentmethod mask
	 */
	private static String createXML() {
		StringBuffer xmlMessage = new StringBuffer();
		// order code should be unique, the date is unique..
		String orderCode= ""+System.currentTimeMillis();
		xmlMessage.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
		xmlMessage.append("<!DOCTYPE paymentService PUBLIC \"-//WorldPay//DTD WorldPay PaymentService v1//EN\" \"http://dtd.worldpay.com/paymentService_v1.dtd\">");
		xmlMessage.append("<paymentService version=\"1.4\" merchantCode=\"" + merchantCode + "\">");
		xmlMessage.append("<submit>");
		xmlMessage.append("<order orderCode=\"" + orderCode + "\">");
		xmlMessage.append("<description>Your Order</description>");
		xmlMessage.append("<amount currencyCode=\"EUR\" value=\"2600\" exponent=\"2\"/>");

		
		xmlMessage.append("<paymentDetails>");
		xmlMessage.append("<VISA-SSL>");
		xmlMessage.append("<cardNumber>4444333322221111</cardNumber> ");
		xmlMessage.append("<expiryDate> <date month=\"09\" year=\"2019\"/> </expiryDate>");
		xmlMessage.append("<cardHolderName>J. Shopper</cardHolderName> ");
		xmlMessage.append("<cvc>123</cvc> ");
		xmlMessage.append("<cardAddress>");
		xmlMessage.append("<address>");
		xmlMessage.append("<street>47A Queensbridge Rd</street> ");
		xmlMessage.append("<postalCode>CB94BQ</postalCode>");
		xmlMessage.append("<countryCode>GB</countryCode> ");
		xmlMessage.append("</address> ");
		xmlMessage.append("</cardAddress> ");
		xmlMessage.append("</VISA-SSL>");
		xmlMessage.append("<session shopperIPAddress=\"100.100.100.100\" id=\"0215ui8ib1\" /> ");
		xmlMessage.append("</paymentDetails>");
		xmlMessage.append("<shopper> <shopperEmailAddress>jshopper@myprovider.int</shopperEmailAddress>");
		xmlMessage.append("<browser> <acceptHeader>text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8</acceptHeader>");
		xmlMessage.append("<userAgentHeader>Mozilla/5.0 (Windows; U; Windows NT 5.1; en-GB; rv:1.9.1.5) Gecko/20091102 Firefox/3.5.5 (.NET CLR 3.5.30729)</userAgentHeader>");
		xmlMessage.append("</browser> ");
		xmlMessage.append("</shopper>");

		
		/*xmlMessage.append("<orderContent>Uw order</orderContent>");
		xmlMessage.append("<paymentMethodMask>");
		xmlMessage.append("<include code=\"ALL\"/>");
		xmlMessage.append("</paymentMethodMask>");*/
		xmlMessage.append("</order>");
		xmlMessage.append("</submit>");
		xmlMessage.append("</paymentService>");
		return xmlMessage.toString();
	}
	
	/*
	 * Sends a xml message using a secure connection and username password authentication
	 */
	public static String sendXml(String theXMLMessage) {
		StringBuffer response = new StringBuffer();
		try{
			URL url = new URL(location);
			huc = (HttpURLConnection)url.openConnection();
			huc.setRequestMethod("POST");
			huc.setRequestProperty("Authorization", "Basic "+encodeBase64((userName+":"+passWord).getBytes()));
			huc.setRequestProperty("Host", url.getHost());
			huc.setDoOutput(true);
			
			System.out.println(encodeBase64((userName+":"+passWord).getBytes())+"\nurl:"+ huc);
			
			PrintWriter writer = new PrintWriter(huc.getOutputStream());
			writer.println(theXMLMessage);
			writer.flush();
			writer.close();
			
			BufferedReader br = new BufferedReader(new InputStreamReader(huc.getInputStream()));
			String line;
			while((line = br.readLine()) != null){
				response.append(line);
				response.append("\n");
			}
		}catch(MalformedURLException mfue){	
			response.append("The URL is incorrect : ");
			response.append(location);
		}catch(IOException ioe){ 
			response.append("A connection problem has occured, this could be caused by:\n");
			response.append(" - The userName is not correct : "+ userName+ "; Check with WorldPay support at support@worldpay.com\n");
			response.append(" - The password is not correct ; Check with WorldPay support at support@worldpay.com\n");
			response.append(" - The URL is not correct : "+ location +" ; Review the documentation\n");
			response.append(" - You are behind a firewall that does not allow secure connections; Contact your network administrator\n");
		}
		return response.toString().trim();
	}
	
	/*
	 * encodes a string to Base 64
	 */
	public static String encodeBase64(byte[] d){
		if (d == null) return null;
		byte data[] = new byte[d.length+2];
		System.arraycopy(d, 0, data, 0, d.length);
		byte dest[] = new byte[(data.length/3)*4];

		// 3-byte to 4-byte conversion
		for (int sidx = 0, didx=0; sidx < d.length; sidx += 3, didx += 4){
			dest[didx]   = (byte) ((data[sidx] >>> 2) & 077);
			dest[didx+1] = (byte) ((data[sidx+1] >>> 4) & 017 | (data[sidx] << 4) & 077);
			dest[didx+2] = (byte) ((data[sidx+2] >>> 6) & 003 | (data[sidx+1] << 2) & 077);
			dest[didx+3] = (byte) (data[sidx+2] & 077);
		}

		// 0-63 to ascii printable conversion
		for (int idx = 0; idx <dest.length; idx++)
		{
			if (dest[idx] < 26)     dest[idx] = (byte)(dest[idx] + 'A');
			else if (dest[idx] < 52)  dest[idx] = (byte)(dest[idx] + 'a' - 26);
			else if (dest[idx] < 62)  dest[idx] = (byte)(dest[idx] + '0' - 52);
			else if (dest[idx] < 63)  dest[idx] = (byte)'+';
			else dest[idx] = (byte)'/';
		}

		// add padding
		for (int idx = dest.length-1; idx > (d.length*4)/3; idx--){
			dest[idx] = (byte)'=';
		}
		return new String(dest);
	}
}

/*
	$log:$
 */

