package cn.meebox.dns;

import java.io.IOException;

import org.apache.http.client.ClientProtocolException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class Main {

	public Main() {
		// TODO Auto-generated constructor stub
	}
	
	public static void main(String[] args) throws ClientProtocolException, IOException {
		// TODO Auto-generated method stub
		
		IPv4 ipv4 = new IPv4();
		ipv4.setRegistry("APNIC");
		ipv4.setCc("CN");
		ipv4.setRecordtype("ipv4");
		ipv4.setStart("1.0.1.0");
		ipv4.setCount(256);
		ipv4.setReceiveddate("20110414");
		ipv4.setStatus("allocated");
		
		Configuration cfg = new Configuration();
        SessionFactory sf = cfg.configure().buildSessionFactory();
         
        Session session = sf.openSession();
        session.beginTransaction();
        session.save(ipv4);
        session.getTransaction().commit();
        session.close();
        sf.close();  
	}

}
