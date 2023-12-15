package lobbyServer.controller;

import databaseServer.controller.DataBaseController;

public class RegistrationController {

    // 新しいユーザーを登録するメソッド
    public boolean registUser(String userName, String password) {

        // DataBaseController のインスタンスを作成
        DataBaseController dataBaseController = new DataBaseController();

        return dataBaseController.executeQueryForSignin("INSERT INTO users (username, password) VALUES ('" + userName + "', '" + password + "')");
    }
}
