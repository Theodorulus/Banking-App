package config;

import java.sql.*;

public class DatabaseConnection {
    private static Connection connection;

    private DatabaseConnection () {

    }

    public static Connection getInstance() throws SQLException {
        if(connection == null) {
            String url = "jdbc:mysql://localhost:3306/banking_app_theo";
            String username = "root";
            String password = "password";
            connection = DriverManager.getConnection(url, username, password);
        }
        return connection;
    }
}
