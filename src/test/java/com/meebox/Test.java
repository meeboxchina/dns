package com.meebox;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Test {

	public Test() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			Class.forName("org.postgresql.Driver");
			Connection conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/meebox", "meebox", "meebox");
			Statement stmt = conn.createStatement();
			String sql = "insert into icann values (nextval('icann_id_seq'),'apnic','CN')";
			sql = "select * from icann";
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next())
            {
               System.out.println(rs.getString("value"));
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
