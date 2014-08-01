package cn.meebox.commons;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.SocketException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.net.ftp.FTPClient;

public class FTPDownloader {
	private String host;
	private int port;
	private String filepath;
	private String filename;
	private String uri;
	private String protocol;
	private String username;
	private String password;
	
	private String localpath;
	private int code;
	
	//private static String regex = "^(http|https|ftp){1}://([a-zA-Z0-9-.]{3,})(/.*)$";
	private static String regex = "^(ftp)://([a-zA-Z0-9-.]{3,})(/.*)$";
	
	public FTPDownloader(String host, String filepath) {
		// TODO Auto-generated constructor stub
		this.host = host;
		this.filepath = filepath;
	}

	public FTPDownloader(String uri) {
		// TODO Auto-generated constructor stub
		this.uri = uri;
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(uri);
		
		if(matcher.matches()){
			this.protocol = matcher.group(1);
			this.host = matcher.group(2);
			this.filepath = matcher.group(3);
			this.filename = filepath.split("[/]")[filepath.split("[/]").length - 1];
		}else{
			String[] splits = uri.split("[/]");
			int length = splits.length;
			this.host = splits[0];
			for(int i=1; i<length; i++){
				this.filename += splits[i];
			}
			this.filename = splits[length-1];
		}
	}

	public void down(){
		System.out.println(host);
		FTPClient ftp = new FTPClient();
		try {
			ftp.connect(host);
			ftp.enterLocalPassiveMode();
			ftp.login("anonymous", "");
			
			File file = new File("./" + filename);
			FileOutputStream fos = new FileOutputStream(file);
			
			System.out.println(filepath);
			if(ftp.retrieveFile(filepath, fos)){
				this.code = ftp.getReplyCode();
				//System.out.println(ftp.getReplyCode());
				//System.out.println(ftp.getReplyString());
				System.out.println("Download OK");
			}else{
				this.code = ftp.getReplyCode();
				//System.out.println(ftp.getReplyCode());
				//System.out.println(ftp.getReplyString());
				System.out.println("Download Failed");
			};
			
			fos.close();
			
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
}
