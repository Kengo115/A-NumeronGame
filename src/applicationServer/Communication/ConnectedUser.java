package applicationServer.Communication;

import javax.websocket.Session;

public class ConnectedUser {
    String username; // ユーザー名を格納するフィールド
    Session session;  // セッションを格納するフィールド

    boolean close=false; //アプリケーションサーバ側から切断を予定しているかどうか

    // コンストラクタ
    public ConnectedUser(String username, Session session) {
        this.username = username;
        this.session = session;
    }

}

