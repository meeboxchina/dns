package cn.meebox.dns;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Hashtable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.client.ClientProtocolException;

import cn.meebox.commons.HttpDownload;

public class ICANN {
	
	private String RIR;
	private String dest;
	private String filepath;
	private Hashtable[] rir;
	
	public ICANN() {
	}
	
	public ICANN(String RIP) {
		this.RIR = RIP;
	}
	
	public void getStatsfile(){
		
		try {
			Class.forName("org.postgresql.Driver");
			Connection conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/meebox", "meebox", "meebox");
			Statement stmt = conn.createStatement();
			String sql = "select icann.registry,icann.dl,icann.chkmd5,statsfile.etag from icann left join statsfile on icann.registry = statsfile.registry";
			ResultSet rs = stmt.executeQuery(sql);
			int i=0;
			while(rs.next()){
				rir[i].put("registry", rs.getString("registry"));
				rir[i].put("dl", rs.getString("dl"));
				rir[i].put("chkmd5", rs.getString("chkmd5"));
				rir[i].put("etag", rs.getString("etag"));
				i++;
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
