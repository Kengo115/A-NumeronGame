package lobbyServer.controller;

import lobbyServer.communication.LobbyServerCommunication;
import lobbyServer.communication.WebSocketEndpoint;

import java.util.ArrayList;

public class UserController {
    private ArrayList<String> waitUser;
    LobbyServerCommunication lobbyServerCommunication = new LobbyServerCommunication();

    public UserController() {

        this.waitUser = new ArrayList<>();
    }

    // マッチング要求があったときに呼び出されるメソッド
    public void addUser(String username) {

        waitUser.add(username);
        System.out.println("UserController.addUser:"+username+"を待ちユーザリストに追加");

        // ユーザーリストが2人になったらルーム作成の通知をアプリケーションサーバに送る
        if (waitUser.size() == 2) {
            sendUsername();
            waitUser.clear();
        }
    }

    // キャンセルボタンが押されたときの処理
    public void deleteUser(String deleteUsername) {
        for (String username : waitUser) {
            if(username.equals(deleteUsername)) {
                waitUser.remove(username);
                System.out.println("UserController.deleteUser:"+username+"を待ちユーザリストから削除しました");
                return;
            }
        }

    }

    // アプリケーションサーバにルーム作成の通知を送る
    private void sendUsername() {

        String username1 = waitUser.get(0);
        String username2 = waitUser.get(1);

        System.out.println("ルーム作成要求を送信:"+username1+"と"+username2+"がマッチングしました");


        //現時点でマッチングした二人のユーザ名を渡すメソッドにしておきました
        lobbyServerCommunication.makeRoom(username1,username2);


    }

}
