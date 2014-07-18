package cn.meebox.commons;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

public class HttpDownload {

	private String uri;
	private String dest;
	
	public HttpDownload() {
		// TODO Auto-generated constructor stub
	}

	public HttpDownload(String uri, String dest) {
		super();
		this.uri = uri;
		this.dest = dest;
	}
	
	public String down() throws ClientProtocolException, IOException{
		//String url = "http://ww2.sinaimg.cn/large/9d57a855jw1dqpv9fp4yuj.jpg";
        String[] path = uri.split("/");
        String filename = path[path.length-1];
		
		CloseableHttpClient httpclient = HttpClients.createDefault();

        HttpGet httpget = new HttpGet(uri);
        HttpResponse response = httpclient.execute(httpget);
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
        return dest+filename;
	}
}
