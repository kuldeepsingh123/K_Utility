package org.ngi.db;

import java.net.InetAddress;

public class GetHostName {
	public static String HostName(){
		String Hostname = null;
		try{
			InetAddress Addr = InetAddress.getLocalHost();
			Hostname = Addr.getHostName();
		}catch(Exception e){
			System.out.println(e.getMessage());
		}
		return Hostname;
	}
}
