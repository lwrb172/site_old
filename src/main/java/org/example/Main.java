package org.example;

import org.example.database.DatabaseConnection;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws SQLException {
        String createSQLTable = "CREATE TABLE IF NOT EXISTS accounts( " +
                "id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT," +
                "username TEXT NOT NULL," +
                "password TEXT NOT NULL)";

        DatabaseConnection dbconnection = new DatabaseConnection();
        dbconnection.connect("database.db");
        PreparedStatement preparedStatement = dbconnection.getConnection().prepareStatement(createSQLTable);
        preparedStatement.executeUpdate();
        dbconnection.disconnect();
    }
}