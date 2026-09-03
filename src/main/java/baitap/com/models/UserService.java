package baitap.com.models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;

import baitap.com.connection.DBConnection;
import baitap.com.entity.UserModel;
import baitap.com.util.MailService;

import java.security.SecureRandom;

public class UserService {

    public boolean register(String username, String password) {
        return register(username, "", password);
    }

    public boolean register(String username, String email, String password) {
        if (username == null || username.trim().isEmpty()
                || email == null || email.trim().isEmpty()
                || password == null || password.trim().isEmpty()) {
            return false;
        }

        String normalizedUsername = username.trim();
        if (usernameExists(normalizedUsername)) {
            return false;
        }

        String otp = String.format("%06d", new SecureRandom().nextInt(1_000_000));
        String sql = "INSERT INTO `user` (username, email, password, roleid, enabled, otp, otp_expiry) VALUES (?, ?, ?, 2, 0, ?, ?)";
        try (Connection connection = new DBConnection().getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, normalizedUsername);
            statement.setString(2, email.trim());
            statement.setString(3, password);
            statement.setString(4, otp);
            statement.setLong(5, System.currentTimeMillis() + 10 * 60 * 1000L);
            boolean inserted = statement.executeUpdate() == 1;
            if (inserted) {
                try {
                    MailService.send(email.trim(), "Kich hoat tai khoan", "Ma OTP kich hoat cua ban: " + otp);
                } catch (Exception mailException) {
                    deleteUser(normalizedUsername);
                    return false;
                }
            }
            return inserted;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private void deleteUser(String username) {
        try (Connection connection = new DBConnection().getConnection();
             PreparedStatement statement = connection.prepareStatement("DELETE FROM `user` WHERE username = ?")) {
            statement.setString(1, username);
            statement.executeUpdate();
        } catch (Exception exception) {
            exception.printStackTrace();
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

        String sql = "SELECT * FROM `user` WHERE username = ? AND password = ? AND enabled = 1 LIMIT 1";
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

    public boolean activate(String username, String otp) {
        return updateOtp(username, otp, true, null);
    }

    public boolean requestPasswordReset(String username) {
        String otp = String.format("%06d", new SecureRandom().nextInt(1_000_000));
        String sql = "UPDATE `user` SET otp = ?, otp_expiry = ? WHERE username = ? AND email IS NOT NULL";
        try (Connection connection = new DBConnection().getConnection(); PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, otp); statement.setLong(2, System.currentTimeMillis() + 10 * 60 * 1000L); statement.setString(3, username.trim());
            if (statement.executeUpdate() != 1) return false;
            try (PreparedStatement emailQuery = connection.prepareStatement("SELECT email FROM `user` WHERE username = ?")) {
                emailQuery.setString(1, username.trim());
                try (ResultSet result = emailQuery.executeQuery()) {
                    if (result.next()) MailService.send(result.getString(1), "Dat lai mat khau", "Ma OTP cua ban: " + otp);
                }
            }
            return true;
        } catch (Exception exception) { exception.printStackTrace(); return false; }
    }

    public boolean resetPassword(String username, String otp, String password) {
        return updateOtp(username, otp, false, password);
    }

    private boolean updateOtp(String username, String otp, boolean activation, String password) {
        String sql = activation
                ? "UPDATE `user` SET enabled = 1, otp = NULL, otp_expiry = NULL WHERE username = ? AND otp = ? AND otp_expiry > ?"
                : "UPDATE `user` SET password = ?, otp = NULL, otp_expiry = NULL WHERE username = ? AND otp = ? AND otp_expiry > ?";
        try (Connection connection = new DBConnection().getConnection(); PreparedStatement statement = connection.prepareStatement(sql)) {
            int index = 1;
            if (!activation) statement.setString(index++, password);
            statement.setString(index++, username.trim()); statement.setString(index++, otp.trim()); statement.setLong(index, System.currentTimeMillis());
            return statement.executeUpdate() == 1;
        } catch (Exception exception) { exception.printStackTrace(); return false; }
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