package databaseServer.controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class DataBaseController {

    private static final String URL = "jdbc:mysql://sql.yamazaki.se.shibaura-it.ac.jp:13308/db_group_a";
    private static final String DB_USER = "group_a";
    private static final String DB_PASSWORD = "group_a";
	private Connection connection;

    public boolean executeQueryForSignin(String sql) {
        try (Connection connection = DriverManager.getConnection(URL, DB_USER, DB_PASSWORD);
             Statement statement = connection.createStatement()) {

            int rowsAffected = statement.executeUpdate(sql);

            if (rowsAffected > 0) {
                return true;
            } else {
                return false;
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    
    // ログインの結果を取得するメソッド
    public boolean executeQueryForLogin(String sql) {
        try (Connection connection = DriverManager.getConnection(URL, DB_USER, DB_PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(sql);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            if (resultSet.next()) {
                return true;
            } else {
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    // ログアウトが成功したかどうかを返すメソッド
    public boolean executeQueryForLogout(String sql, String userName) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, userName);

            // SQLを実行
            int rowsAffected = preparedStatement.executeUpdate();
            preparedStatement.close();
            if (rowsAffected > 0) {
                return true;
            }
            else {
            	return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
}
