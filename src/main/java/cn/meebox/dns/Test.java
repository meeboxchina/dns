package cn.meebox.dns;

import java.io.IOException;
import java.net.Inet4Address;
import java.net.UnknownHostException;

import org.apache.http.client.ClientProtocolException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

public class Test {

	public Test() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		ICANN icann = new ICANN();
		icann.getStatsfile();
		
		//icann.getAPNIC("/Users/sunsunny/");
		//icann.import2db();

	}

}
