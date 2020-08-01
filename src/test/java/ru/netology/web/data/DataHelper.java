package ru.netology.web.data;

import lombok.Value;
import lombok.val;
import org.apache.commons.dbutils.QueryRunner;

import java.sql.DriverManager;
import java.sql.SQLException;

public class DataHelper {
    private DataHelper() {
    }

    public static void cleanAllTable() throws SQLException {
        val foreignCheckOff = "SET FOREIGN_KEY_CHECKS=0;";
        val foreignCheckOn = "SET FOREIGN_KEY_CHECKS=1;";
        val run = new QueryRunner();
        val auth_codes = "truncate table auth_codes;";
        val card_transactions = "truncate table card_transactions;";
        val cards = "truncate table cards;";
        val users = "truncate table users;";

        try (
                val connect = DriverManager.getConnection(
                        "jdbc:mysql://192.168.99.100:3306/app", "app", "pass"
                )
                ) {
            run.update(connect, foreignCheckOff);
            run.update(connect, auth_codes);
            run.update(connect, card_transactions);
            run.update(connect, cards);
            run.update(connect, users);
            run.update(connect, foreignCheckOn);
        }
    }

    @Value
    public static class AuthInfo {

        private String login;
        private String password;
    }

    public static AuthInfo getAuthInfo() {
        return new AuthInfo("vasya", "qwerty123");
    }

    public static AuthInfo getLoginWithBadPassword() {
        return new AuthInfo("vasya", "qwerty999");
    }

    public static AuthInfo getWrongAuthInfo() {
        return new AuthInfo("wrongUser","invalidPass");
    }

    @Value

    public static class VerificationCode {
        private String code;
    }

    public static String getUserId() throws SQLException {
        val getUserId = "SELECT id FROM users WHERE login = 'vasya';";
        try (
                val connect = DriverManager.getConnection(
                        "jdbc:mysql://192.168.99.100:3306/app", "app", "pass"
                );
                val createStmt = connect.createStatement();
        ) {
            try (val resultSet = createStmt.executeQuery(getUserId)) {
                if (resultSet.next()) {
                    val userId = resultSet.getString(1);
                    return userId;
                }
            }
        }
        return null;
    }

    public static String getVerificationCode() throws SQLException{
        val requestCode = "SELECT code FROM auth_codes WHERE user_id = ? ORDER BY created DESC LIMIT 1;";

        try (
                val connect = DriverManager.getConnection(
                        "jdbc:mysql://192.168.99.100:3306/app", "app", "pass"
                );
                val prepareStmt = connect.prepareStatement(requestCode);
        ) {
            prepareStmt.setString(1, getUserId());
            try (val resultSet = prepareStmt.executeQuery()) {
                if (resultSet.next()) {
                    val code = resultSet.getString(1);
                    return code;
                }
            }
        }
        return null;
    }

    public static String setStatusUserVasya() throws SQLException{
        val requestCode = "UPDATE users SET status = 'disable' WHERE login = 'vasya';";

        try (
                val connect = DriverManager.getConnection(
                        "jdbc:mysql://192.168.99.100:3306/app", "app", "pass"
                );
                val createStmt = connect.createStatement();
        ) {
            createStmt.executeQuery(requestCode);
        }
        return null;
    }

    public static String getInvalidVerificationCode() {
        return "159632";
    }

}
