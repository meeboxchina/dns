package cn.meebox.dns;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.client.ClientProtocolException;

import cn.meebox.commons.HttpDownload;

public class ICANN {
	
	private String RIR;
	private String dest;
	private String filepath;
	private static String APNIC = "http://ftp.apnic.net/apnic/stats/apnic/delegated-apnic-latest";
	
	public ICANN() {
	}
	
	public ICANN(String RIP) {
		this.RIR = RIP;
	}
	
	public void get(String RIP, String dest) throws ClientProtocolException, IOException{
		
		HttpDownload apnic = new HttpDownload(APNIC,dest);
		this.filepath = apnic.down();
	}
	
	public void zhengli() throws IOException{
		//String regex = "^[a-z]{3,5}\\|[A-Z]{2}\\|(ipv4)\\|.*";
		String regex = "^[a-z]{3,5}\\|CN\\|(ipv4)\\|.*";
		Pattern  pattern = Pattern.compile(regex);
		
		File file = new File(this.filepath);
		InputStreamReader isr = new InputStreamReader(new FileInputStream(file));
		BufferedReader br = new BufferedReader(isr);
		
		String line = null;
		Matcher matcher = null;
		while((line = br.readLine()) != null){
			matcher = pattern.matcher(line);
			if(matcher.find()){
				//System.out.print(line);
				String[] net = line.split("[|]");
				System.out.println(net[3]);
			}
		}
	
	}
	
}
