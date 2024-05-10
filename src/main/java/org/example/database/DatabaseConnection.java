package org.example.database;

import java.nio.file.Path;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private Connection connection;

    public Connection getConnection() {
        return connection;
    }

    public void connect(String path)  {
        try {
            connection = DriverManager.getConnection(String.format("jdbc:sqlite:%s", path));
            System.out.println("Connected");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void disconnect() {
        try {
            if(connection != null && connection.isClosed() ) {
                connection.close();
            }
            System.out.println("Disconnected");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
