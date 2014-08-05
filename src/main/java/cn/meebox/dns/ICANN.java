package cn.meebox.dns;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
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
import cn.meebox.commons.FTPDownloader;
import cn.meebox.commons.HttpDownload;

public class ICANN {
	
	private String RIR;			//registry
	private String chkmd5;
	private String dest;
	private String filepath;
	private Hashtable[] rir;
	
	public ICANN() {
	}
	
	public ICANN(String RIR) {
		this.RIR = RIR;
	}
	
	public void getStatsfile(String localpath) throws Exception{
		String registry;
		String dlurl;
		String md5url;
		String md5;
		String etag;
		int code;
		int length;
		String now;
		
		Class.forName("org.postgresql.Driver");
		Connection conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/meebox", "meebox", "meebox");
		Statement stmt = conn.createStatement();
		String sqlQuery = "select icann.registry,icann.dlurl,icann.md5url,statsfile.md5 from icann left join statsfile on icann.registry = statsfile.registry";
		ResultSet rs = stmt.executeQuery(sqlQuery);
		while(rs.next()){
			registry = rs.getString("registry");
			dlurl = rs.getString("dlurl");
			md5url = rs.getString("md5url");
			md5 = rs.getString("md5");
				
			FTPDownloader ftpmd5 = new FTPDownloader(md5url);
			ftpmd5.down(localpath);
			File filemd5 = new File(localpath + ftpmd5.getFilename());
			FileReader frmd5 = new FileReader(filemd5);
			
			BufferedReader br = new BufferedReader(frmd5);
			String latestmd5 = br.readLine();
			frmd5.close();
			
			Pattern p = Pattern.compile(".*([a-z0-9]{32}).*");
			Matcher m = p.matcher(latestmd5);
			if(m.matches()){
				latestmd5 = m.group(1);
			}
			if(md5 != null && md5.matches(latestmd5)){
				Date date = new Date();
				SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd HH:mm:ss");
				now = sdf.format(date);
					
				String sqlUpdate = "update statsfile set "
						+ "trytime='" + now + "' "
						+ "where registry='" + registry + "'";
					
				Statement stmtUpdate = conn.createStatement();
				stmtUpdate.executeUpdate(sqlUpdate);
					
			}else{
				FTPDownloader ftp = new FTPDownloader(dlurl);
				String filename = ftp.down(localpath);
				code = ftp.getCode();
					
				//if(code ==226){
					Date date = new Date();
					SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd HH:mm:ss");
					now = sdf.format(date);
						
					String sqlUpdate = "update statsfile set "
							+ "downloadtime='" + now + "',"
							+ "trytime='" + now + "',"
							+ "md5='" + latestmd5 + "' "
							+ "file=lo_import('" + localpath + filename + "'), "
							+ "filepath='" + localpath + filename + "' "
							+ "where registry='" + registry + "'";
						
					Statement stmtUpdate = conn.createStatement();
					stmtUpdate.executeUpdate(sqlUpdate);
				//}
			}
			
		}
		
		stmt.close();
		conn.close();
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
