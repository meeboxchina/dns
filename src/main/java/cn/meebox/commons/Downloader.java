package cn.meebox.commons;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.http.Header;
import org.apache.http.HeaderIterator;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

public class Downloader {

	private String uri;
	private String dest;
	private int code;
	private int length;
	private String etag;
	
	
	public Downloader() {
		// TODO Auto-generated constructor stub
	}

	public Downloader(String uri, String dest) {
		super();
		this.uri = uri;
		this.dest = dest;
	}
	
	public String down(String etag) throws ClientProtocolException, IOException{
		Header header;
        String[] path = uri.split("[/]");
        String filename = path[path.length-1];
		
		CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpGet httpget = new HttpGet(uri);
        httpget.addHeader("ETag", etag);
        
        HttpResponse response = httpclient.execute(httpget);
        this.code = response.getStatusLine().getStatusCode();

        HeaderIterator headerIterator = response.headerIterator();
        
        
        if(response.getStatusLine().getStatusCode() == 200){
        	
        	while(headerIterator.hasNext()){
            	header = headerIterator.nextHeader();
            	
            	
            }
        	
	        HttpEntity entity = response.getEntity();
	        InputStream in = entity.getContent();
	        File file = new File(dest+filename);
	        try {
	                FileOutputStream fout = new FileOutputStream(file);
	                int l = -1;
	                byte[] tmp = new byte[1024];
	                while ((l = in.read(tmp)) != -1) {
	                        fout.write(tmp,0,l);
	                }
	                fout.flush();
	                fout.close();
	         } finally {
	        	 in.close();
	        }
	        httpclient.close();
        }
        return dest+filename;
	}
}
