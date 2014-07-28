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
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Hashtable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.client.ClientProtocolException;

import cn.meebox.commons.Downloader;
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
	
	public void getStatsfile() throws ClientProtocolException, IOException{
		String registry;
		String dl;
		String chkmd5;
		String etag;
		int code;
		int length;
		String now;
		
		try {
			Class.forName("org.postgresql.Driver");
			Connection conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/meebox", "meebox", "meebox");
			Statement stmt = conn.createStatement();
			String sqlQuery = "select icann.registry,icann.dl,icann.chkmd5,statsfile.etag from icann left join statsfile on icann.registry = statsfile.registry";
			ResultSet rs = stmt.executeQuery(sqlQuery);
			while(rs.next()){
				registry = rs.getString("registry");
				dl = rs.getString("dl");
				chkmd5 = rs.getString("chkmd5");
				etag = rs.getString("etag");
				
				Downloader downloader = new Downloader(dl, "/Users/sunsunny/");
				downloader.down(etag);
				
				etag = downloader.getEtag();
				code = downloader.getCode();
				if(code ==200){
					length = downloader.getLength();
					Date date = new Date();
					SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd HH:mm:ss");
					now = sdf.format(date);
					
					String sqlUpdate = "update statsfile set etag='" + etag + "',"
							+ "lastcode=" + code + ","
							+ "length=" + length + ","
							+ "downloadtime='" + now + "',"
							+ "trytime='" + now + "'"
							+ "where registry='" + registry + "'";
					
					Statement stmtUpdate = conn.createStatement();
					stmtUpdate.executeUpdate(sqlUpdate);
				}else{
					Date date = new Date();
					SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd hh:mm:ss");
					now = sdf.format(date);
					
					String sqlUpdate = "update statsfile set lastcode=" + code + ","
							+ "trytime='" + now + "'"
							+ "where registry='" + registry + "'";
					
					Statement stmtUpdate = conn.createStatement();
					stmtUpdate.executeUpdate(sqlUpdate);
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
