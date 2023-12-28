package databaseServer.controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class DataBaseController {
	
	private static final String sqlDriverName = "com.mysql.jdbc.Driver";

	// SQLサーバの指定
	private static final String url = "jdbc:mysql://sql.yamazaki.se.shibaura-it.ac.jp";
	private static final String sqlServerPort = "13308";

	// 以下は班ごとに違うことに注意
	private static final String sqlDatabaseName = "db_group_a";
	private static final String sqlUserId   = "group_a";
	private static final String sqlPassword = "group_a";
	
	
	public DataBaseController (){
		try {
			Class.forName(sqlDriverName);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	
    public boolean executeQueryForSignin(String sql) {
        try {
        	String target = url + ":" + sqlServerPort + "/" + sqlDatabaseName;
			System.out.println("target: " + target);

        	
        	Connection connection = DriverManager.getConnection(target, sqlUserId, sqlPassword);
        
            Statement statement = connection.createStatement();

            int rowsAffected = statement.executeUpdate(sql);
            
            //終了処理
            statement.close();
            connection.close();
            

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
        try {
        	String target = url + ":" + sqlServerPort + "/" + sqlDatabaseName;
			System.out.println("target: " + target);
			
        	Connection connection = DriverManager.getConnection(target, sqlUserId, sqlPassword);
	
             PreparedStatement preparedStatement = connection.prepareStatement(sql);
             ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                //終了処理
                preparedStatement.close();
                connection.close();
                return true;
            } else {
                //終了処理
                preparedStatement.close();
                connection.close();
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    //isLoggedInを更新
    public boolean executeUpdate(String sql) {
        try {
            String target = url + ":" + sqlServerPort + "/" + sqlDatabaseName;
            System.out.println("target: " + target);

            Connection connection = DriverManager.getConnection(target, sqlUserId, sqlPassword);

            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            int rowsAffected = preparedStatement.executeUpdate();

            System.out.println("sql文実行成功");
            
            /* isLoggedInの値を取得してprint(確認用)
            String selectIsLoggedInQuery = "SELECT isLoggedIn FROM UserList"; 
            try (PreparedStatement selectStatement = connection.prepareStatement(selectIsLoggedInQuery);
                 ResultSet resultSet = selectStatement.executeQuery()) {
            	
            	 boolean isLoggedIn = resultSet.getBoolean("isLoggedIn");
                 System.out.println("isLoggedIn: " + isLoggedIn);
                
            }*/
           
            // 終了処理
            preparedStatement.close();
            connection.close();
            
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

    
    // ログアウトが成功したかどうかを返すメソッド
    public boolean executeQueryForLogout(String sql) {
        try {
        	String target = url + ":" + sqlServerPort + "/" + sqlDatabaseName;
			System.out.println("target: " + target);
			
        	Connection connection = DriverManager.getConnection(target, sqlUserId, sqlPassword);
        	
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            // SQLを実行
            int rowsAffected = preparedStatement.executeUpdate();
            
            //終了処理
            preparedStatement.close();
            connection.close();
            

            if (rowsAffected > 0) {
            	//デバック
            	System.out.println("ログアウト成功");
                
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
    
    public void insertInitialValues(String userName, int initialRate, int initialWinCount, int initialLoseCount, int initialDrawCount) {
        String insertQuery = "INSERT INTO UserList (UserName, rate, winCount, loseCount, drawCount) VALUES (?, ?, ?, ?, ?)";

    	try {
        	String target = url + ":" + sqlServerPort + "/" + sqlDatabaseName;
			System.out.println("target: " + target);
			
        	Connection connection = DriverManager.getConnection(target, sqlUserId, sqlPassword);
            PreparedStatement preparedStatement = connection.prepareStatement(insertQuery);
            
            preparedStatement.setString(1, userName);
            preparedStatement.setInt(2, initialRate);
            preparedStatement.setInt(3, initialWinCount);
            preparedStatement.setInt(4, initialLoseCount);
            preparedStatement.setInt(5, initialDrawCount);

            preparedStatement.executeUpdate();
            
            //終了処理
            preparedStatement.close();
            connection.close();
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public List<Integer> executeQueryForGetResultInformation(String sql) {
        List<Integer> resultInformation = new ArrayList<>();
    	
    	try {
        	String target = url + ":" + sqlServerPort + "/" + sqlDatabaseName;
			System.out.println("target: " + target);
			
        	Connection connection = DriverManager.getConnection(target, sqlUserId, sqlPassword);
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
        	preparedStatement.setString(1, sql);

                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                    	resultInformation.add(resultSet.getInt("rate"));
                        resultInformation.add(resultSet.getInt("winCount"));
                        resultInformation.add(resultSet.getInt("loseCount"));
                        resultInformation.add(resultSet.getInt("drawCount"));
                    }
                }
                
                //終了処理
                preparedStatement.close();
                connection.close();

            } catch (SQLException e) {
                e.printStackTrace();
            }
    	
		return resultInformation;
    	
    }


    /*public void executeQueryForTimeOut{
    	
    }*/
}

