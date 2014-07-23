package com.meebox;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

import cn.meebox.dns.IPv4;

public class Test {

	public Test() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		/*
		try {
			Class.forName("org.postgresql.Driver");
			Connection conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/meebox", "meebox", "meebox");
			Statement stmt = conn.createStatement();
			String sql = "insert into icann values (nextval('icann_id_seq'),'apnic','CN')";
			sql = "select * from icann";
			ResultSet rs = stmt.executeQuery(sql);

			while (rs.next())
            {
               System.out.println(rs.getString("start"));
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
		*/
		
		IPv4 ipv4 = new IPv4();
		ipv4.setRegistry("apnic");
		ipv4.setCc("CN");
		ipv4.setStart("10.20.30");
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
