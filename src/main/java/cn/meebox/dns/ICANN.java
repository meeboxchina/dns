package cn.meebox.dns;

import java.io.IOException;

import org.apache.http.client.ClientProtocolException;

import cn.meebox.commons.HttpDownload;

public class ICANN {
	
	private String RIR;
	private static String APNIC = "http://ftp.apnic.net/apnic/stats/apnic/delegated-apnic-latest";
	
	public ICANN() {
	}
	
	public ICANN(String RIP) {
		this.RIR = RIP;
	}
	
	public void get(String RIP) throws ClientProtocolException, IOException{
		
		HttpDownload apnic = new HttpDownload(APNIC,"/Users/sunsunny/");
		apnic.down();
	}
}
