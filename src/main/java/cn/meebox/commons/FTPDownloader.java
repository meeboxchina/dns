package cn.meebox.commons;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.SocketException;

import org.apache.commons.net.ftp.FTPClient;

public class FTPDownloader {
	private FTPClient ftpclient;
	public FTPDownloader() {
		// TODO Auto-generated constructor stub
		
	}
	
	public String connect(String host) throws IOException{
		try {
			ftpclient.connect(host);
		} catch (SocketException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		try {
			System.out.println(ftpclient.getStatus());
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ftpclient.getStatus();
		
	}
	public static void main(String[] args) throws SocketException, IOException{
		FTPClient ftp = new FTPClient();
		ftp.connect("ftp.apnic.net");
		
		File file =  new File("/Users/sunsunny/apnic");
		FileInputStream fis = new FileInputStream(file);
		ftp.storeFile("/pub/stats/apnic/delegated-apnic-latest", fis);
		fis.
		
		
	}
}
