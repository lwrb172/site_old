package org.example.auth;

import org.example.database.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AccountManager {
    DatabaseConnection dbconnection;

    public AccountManager(DatabaseConnection dbconnection) {
        this.dbconnection = dbconnection;
    }

    public void init() {
        try {
            String createSQLTable = "CREATE TABLE IF NOT EXISTS accounts( " +
                    "id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT," +
                    "username TEXT NOT NULL," +
                    "password TEXT NOT NULL)";

            PreparedStatement preparedStatement = dbconnection.getConnection().prepareStatement(createSQLTable);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean register(String username, String password) {
        try {
            String insertSQL = "INSERT INTO accounts (username, password) VALUES (?, ?)";
            Connection connection = dbconnection.getConnection();
            Account account = getAccount(username);
            if (account == null) {
                PreparedStatement preparedStatement = connection.prepareStatement(insertSQL);
                preparedStatement.setString(1, username);
                preparedStatement.setString(2, password);
                preparedStatement.executeUpdate();
                return true;
            }
            return false;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Account getAccount(String username) {
        try {
            String query = "SELECT id, username FROM accounts WHERE username = ?";
            Connection connection = dbconnection.getConnection();
            PreparedStatement usernameStatement = connection.prepareStatement(query);
            usernameStatement.setString(1, username);
            ResultSet resultSet = usernameStatement.executeQuery();
            if(resultSet.next()) {
                int id = resultSet.getInt("id");
                String usernameNew = resultSet.getString("username");
                return new Account(id, usernameNew);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    public boolean authenticate(String username, String password) {
        try {
            String query = "SELECT password FROM accounts WHERE username = ?";
            Connection connection = dbconnection.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, username);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                String storePassword = resultSet.getString("password");
                if (password.equals(storePassword)) {
                    return true;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return false;
    }

}
