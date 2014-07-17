package cn.meebox.dns;

import java.io.IOException;

import org.apache.http.client.ClientProtocolException;

public class Main {

	public Main() {
		// TODO Auto-generated constructor stub
	}
	
	public static void main(String[] args) throws ClientProtocolException, IOException {
		// TODO Auto-generated method stub
		ICANN icann = new ICANN();
		icann.get("APNIC");
	}

}
