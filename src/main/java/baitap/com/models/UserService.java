package baitap.com.models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;

import baitap.com.connection.DBConnection;
import baitap.com.entity.UserModel;

public class UserService {

    public boolean register(String username, String password) {
        if (username == null || username.trim().isEmpty()
                || password == null || password.trim().isEmpty()) {
            return false;
        }

        String normalizedUsername = username.trim();
        if (usernameExists(normalizedUsername)) {
            return false;
        }

        String sql = "INSERT INTO `user` (username, password, roleid) VALUES (?, ?, 2)";
        try (Connection connection = new DBConnection().getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, normalizedUsername);
            statement.setString(2, password);
            return statement.executeUpdate() == 1;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private boolean usernameExists(String username) {
        String sql = "SELECT username FROM `user` WHERE username = ? LIMIT 1";
        try (Connection connection = new DBConnection().getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, username);
            try (ResultSet resultSet = statement.executeQuery()) {
                return resultSet.next();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return true;
        }
    }

    public UserModel login(String username, String password) {
        if (username == null || username.trim().isEmpty() || password == null) {
            return null;
        }

        String sql = "SELECT * FROM `user` WHERE username = ? AND password = ? LIMIT 1";
        try (Connection connection = new DBConnection().getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, username.trim());
            statement.setString(2, password);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    int roleid = readRoleId(resultSet);
                    return new UserModel(resultSet.getString("username"),
                        resultSet.getString("password"), roleid);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private int readRoleId(ResultSet resultSet) throws Exception {
        ResultSetMetaData metadata = resultSet.getMetaData();
        for (int column = 1; column <= metadata.getColumnCount(); column++) {
            String name = metadata.getColumnLabel(column);
            if ("roleid".equalsIgnoreCase(name) || "role_id".equalsIgnoreCase(name)) {
                return resultSet.getInt(column);
            }
        }
        return 0;
    }

    public boolean checkLogin(UserModel user) {
        return user != null && login(user.getUsername(), user.getPassword()) != null;
    }
}