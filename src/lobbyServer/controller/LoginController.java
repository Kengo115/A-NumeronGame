package lobbyServer.controller;

import databaseServer.controller.DataBaseController;

public class LoginController {
	
	public boolean login(String userName, String password) {

	    //デバック
	    System.out.println("ログインコントローラー到達");

	    DataBaseController dataBaseController = new DataBaseController();
	    String loginQuery = "SELECT * FROM UserList WHERE UserName = '" + userName + "' AND password = '" + password + "'";
	    boolean loginSuccessful = dataBaseController.executeQueryForLogin(loginQuery);

	    if (loginSuccessful) {
	        // isLoggedInをTRUEに設定する更新クエリ
	        String updateQuery = "UPDATE UserList SET isLoggedIn = true WHERE UserName = '" + userName + "'";
	        dataBaseController.executeUpdate(updateQuery);
	    }

	    return loginSuccessful;
	}

    public boolean logout(String userName) {
    	//デバック
    	System.out.println("ログインコントローラー到達");
    	
        DataBaseController dataBaseController = new DataBaseController();
        return dataBaseController.executeQueryForLogout("UPDATE UserList SET isLoggedIn = false WHERE UserName = '"+userName+ "'");
        
    }
    
}