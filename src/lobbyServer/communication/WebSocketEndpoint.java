package lobbyServer.communication;

import com.google.gson.Gson;
import lobbyServer.controller.LoginController;
import lobbyServer.controller.RegistrationController;
import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;


@ServerEndpoint("/")
public class WebSocketEndpoint {
	private static Set<Session> establishedSessions = Collections.synchronizedSet(new HashSet<Session>());
	private int privateIncrementTest = 0;
	private static int staticIncrementTest = 0;
	static Gson gson = new Gson();//Gson
	LobbyServerCommunication serverCommunication;//サーバコミュニケーション
	LoginController loginController;
	RegistrationController registrationController;
	String username;
	String password;

	@OnOpen//開通時のメッセージ
	public void onOpen(Session session, EndpointConfig ec) {
		establishedSessions.add(session);
		System.out.println("[WebSocketServer] onOpen:" + session.getId());
	}


	@OnMessage//メッセージ受信
	public void onMessage(final String message, final Session session) throws IOException {

		// メッセージクラスに変換：String -> Message
		Message receivedMessage = gson.fromJson(message, Message.class);
		Certification certificationMessage = gson.fromJson(message, Certification.class);
		this.username = certificationMessage.username;
		this.password = certificationMessage.password;

		switch (receivedMessage.demandType) {//受け取った要求に応じて変化
			case "Signin"://会員登録
				certificationMessage.result = registrationController.registUser(username, password);
				String sendMessageJson1 = gson.toJson(certificationMessage);
				sendMessage(session, sendMessageJson1);
				break;
			case "Login"://ログイン
				certificationMessage.result = loginController.login(username, password);
				String sendMessageJson2 = gson.toJson(certificationMessage);
				sendMessage(session, sendMessageJson2);
				break;
			case "Logout"://ログアウト
				loginController.logout(username);
				break;
			default:
				System.out.println("無効な要求です");
		}
	}


	@OnClose//切断時の処理
	public void onClose(Session session) {
		System.out.println("[WebSocketServer] onClose:" + session.getId());
		establishedSessions.remove(session);
	}

	@OnError//エラー発生時の処理
	public void onError(Session session, Throwable error) {
		System.out.println("[WebSocketServer] onError:" + session.getId());
	}

	public void sendMessage(Session session, String message) {
		System.out.println("[WebSocketServer] sendMessage(): " + message);
		try {
			// 同期送信（sync）
			session.getBasicRemote().sendText(message);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void sendBroadcastMessage(String message) {
		System.out.println("[WebSocketServer] sendBroadcastMessage(): " + message);
		establishedSessions.forEach(session -> {
			// 非同期送信（async）
			session.getAsyncRemote().sendText(message);
		});
	}
}
