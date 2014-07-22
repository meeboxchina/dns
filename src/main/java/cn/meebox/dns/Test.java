package cn.meebox.dns;

import java.net.Inet4Address;
import java.net.UnknownHostException;

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

	public static void main(String[] args) throws UnknownHostException {
		// TODO Auto-generated method stub
		IPv4 ipv4 = new IPv4();
		ipv4.setRegistry("apnic");
		ipv4.setCc("CN");
		ipv4.setStart(Inet4Address.getLocalHost());
		Transaction tx = null;
		
		
		Configuration cfg = new Configuration().configure();

        StandardServiceRegistryBuilder srb = new StandardServiceRegistryBuilder().applySettings(cfg.getProperties()); 
        StandardServiceRegistry sr = srb.build();             
        SessionFactory factory = cfg.buildSessionFactory(sr);

        Session session = factory.openSession();             
        tx = session.beginTransaction();

        session.save(ipv4);
        tx.commit();
        session.close();

	}

}
