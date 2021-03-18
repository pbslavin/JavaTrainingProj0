package dev.slavin.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionUtility {
    private static Connection connection;

    private ConnectionUtility() {
        super();
    }

    public static Connection getConnection() throws SQLException {
        if(connection == null || connection.isClosed()) {
            String connectionUrl = System.getenv("PostgresLocalConnectionString");
            String username = System.getenv("PostgresLocalUserName");
            String password = System.getenv("PostgresLocalPassword");

            // create a connection
            connection = DriverManager.getConnection(connectionUrl, username, password);
        }
        return connection;
    }
}
