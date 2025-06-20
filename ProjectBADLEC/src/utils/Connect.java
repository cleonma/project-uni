package utils;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

public class Connect {
	private static final String URL = "jdbc:mysql://localhost:3306/movie_db";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "";
    
    public static Connection getConnection() throws SQLException {
    	try {
    		Class.forName("com.mysql.cj.jdbc.Driver");
    		return DriverManager.getConnection(URL, USERNAME, PASSWORD);
    }	catch (ClassNotFoundException e) {
    	throw new SQLException("MySQL JDBC Driver not found.", e);
    	}
    }
}

