package baitap.com.connection;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection {
	private final String serverName = "localhost";
	private final String dbName = "web_bai01";
	private final String portNumber = "3306";
	private final String userID = "root";
	private final String password = System.getProperty("MYSQL_PASSWORD",
			System.getenv().getOrDefault("MYSQL_PASSWORD", "YOUR_MYSQL_PASSWORD"));

	public Connection getConnection() throws Exception {
		String url = "jdbc:mysql://"
                + serverName
                + ":"
                + portNumber
                + "/"
                + dbName
                + "?useSSL=false"
                + "&serverTimezone=Asia/Ho_Chi_Minh"
                + "&allowPublicKeyRetrieval=true";

        Class.forName("com.mysql.cj.jdbc.Driver");
        
		return DriverManager.getConnection(url, userID, password);
	}

}