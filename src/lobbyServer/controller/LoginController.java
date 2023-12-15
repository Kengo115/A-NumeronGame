package lobbyServer.controller;

import databaseServer.controller.DataBaseController;

public class LoginController {
	
    public boolean login(String userName, String password) {
    	
    	DataBaseController dataBaseController = new DataBaseController();
    	String loginQuery = "SELECT * FROM users WHERE UserName = '" + userName + "' AND password = '" + password + "'";
    	return dataBaseController.executeQueryForLogin(loginQuery);
  
    }

    public boolean logout(String userName) {
        DataBaseController dataBaseController = new DataBaseController();
        return dataBaseController.executeQueryForLogout("UPDATE users SET isLogin = false WHERE UserName = 'ユーザ名'", userName);
        
    }
    
}