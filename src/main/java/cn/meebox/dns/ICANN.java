package cn.meebox.dns;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
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
	
	public void getAPNIC(String dest) throws ClientProtocolException, IOException{
		
		HttpDownload apnic = new HttpDownload(APNIC,dest);
		this.filepath = apnic.down();
	}
	
	public void import2db() throws IOException{
		//String regex = "^[a-z]{3,5}\\|[A-Z]{2}\\|(ipv4)\\|.*";
		String regex = "^[a-z]{3,5}\\|[A-Z]{2}\\|(ipv4)\\|.*";
		Pattern  pattern = Pattern.compile(regex);
		
		File file = new File(this.filepath);
		InputStreamReader isr = new InputStreamReader(new FileInputStream(file));
		BufferedReader br = new BufferedReader(isr);
		
		String line = null;
		Matcher matcher = null;
		
		try {
			Class.forName("org.postgresql.Driver");
			Connection conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/meebox", "meebox", "meebox");
			Statement stmt = conn.createStatement();
			
			String sql = null;
			
			while((line = br.readLine()) != null){
				matcher = pattern.matcher(line);
				if(matcher.find()){
					//System.out.print(line);
					String[] net = line.split("[|]");
					sql = "insert into ipv4 values (nextval('ipv4_id_seq'),'" + net[0] + "','" + net[1] + "','" + net[2] + "','" + net[3] + "','" + net[4] + "','" + net[5] + "','" + net[6] + "')";
					stmt.executeUpdate(sql); 
				}
			}
			
			stmt.close();
			conn.close();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
		
		
	
	}
	
}
