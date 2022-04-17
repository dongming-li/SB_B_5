import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.*;

public class P3 {

	
	
	public static void main(String[] args) {
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			
		} catch (Exception E) {
			System.err.println("Unable to load driver");
			E.printStackTrace();
		}
		
		try {
			Connection conn1;
			String dbUrl = "jdbc:mysql://csdb.cs.iastate.edu:3306/db363tfritzy";
			String user = "dbu363tfritzy";
			String password = "5EZce#Fa";
			conn1 = DriverManager.getConnection(dbUrl, user, password);
			System.out.println("*** Connected to the database ***");
		}catch (SQLException e) {
			System.out.println("SQLException: " + e.getMessage());
			System.out.println("SQLState: " + e.getSQLState());
			System.out.println("VendorError: " + e.getErrorCode());
		}
		
	}
	
}
