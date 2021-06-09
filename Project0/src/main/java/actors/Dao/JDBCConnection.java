package actors.Dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class JDBCConnection {
	private static Connection conn=null;
	public static Connection getConnection() {
		try {
			if(conn==null) {
				Class.forName("org.postgresql.Driver");
				String url="jdbc:postgresql://test.cdnshglvrgng.us-east-2.rds.amazonaws.com:5432/postgres";
				String user="Cory";
				String pass="password";
				conn= DriverManager.getConnection(url,user,pass);
				return conn;
				
			}
			else {
				return conn;
			}
		}
		catch(SQLException |ClassNotFoundException e) {
			e.printStackTrace();
	
		}
		return null;
	}
}
