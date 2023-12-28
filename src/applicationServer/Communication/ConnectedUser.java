package applicationServer.Communication;

import javax.websocket.Session;

public class ConnectedUser {
    String username; // ユーザー名を格納するフィールド
    Session session;  // セッションを格納するフィールド

    // コンストラクタ
    public ConnectedUser(String username, Session session) {
        this.username = username;
        this.session = session;
    }
}

