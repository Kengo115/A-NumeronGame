package lobbyServer.communication;

import java.io.IOException;
import java.util.ArrayList;

import javax.websocket.EndpointConfig;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import com.google.gson.Gson;

import Message.Certification;
import Message.Message;
import lobbyServer.controller.LoginController;
import lobbyServer.controller.RegistrationController;
import lobbyServer.controller.UserController;


@ServerEndpoint("/sample")
public class WebSocketEndpoint {
	private static ArrayList<ConnectedUser> connectedUserList = new ArrayList<>();
	static Gson gson = new Gson();//Gson
	LobbyServerCommunication serverCommunication;//サーバコミュニケーション
	LoginController loginController = new LoginController();
	RegistrationController registrationController = new RegistrationController();
	static UserController userController = new UserController();

	@OnOpen //開通時のメッセージ
	public void onOpen(Session session, EndpointConfig ec) {
		//デバック
    	System.out.println("ロビーサーバ開通");
		System.out.println("[WebSocketServer] onOpen:" + session.getId());
	}


	@OnMessage//メッセージ受信
	public void onMessage(final String message, final Session session) throws IOException {
		
		//デバック
    	System.out.println("ロビーサーバデータ受信");

		// メッセージクラスに変換：String -> Message
		Message receivedMessage = gson.fromJson(message, Message.class);
		
		//デバック
    	System.out.println("メッセージクラスへ変換");


		switch (receivedMessage.demandType) {//受け取った要求に応じて変化
			case "Signin"://会員登録
				
				//デバック
		    	System.out.println("会員登録要求を認識");
		    	
				Certification signinMessage = gson.fromJson(message, Certification.class);
				
				//デバック
		    	System.out.println("Certificationクラスへ変換");
		    	System.out.println(signinMessage.username);
		    	System.out.println(signinMessage.password);
				
				signinMessage.result = registrationController.registUser(signinMessage.username, signinMessage.password);
				
				//デバック
		    	System.out.println(signinMessage.result);

				//会員登録成功したら接続ユーザーに登録する
				if (signinMessage.result) {
					ConnectedUser connectedUser = new ConnectedUser(signinMessage.username, session);
					connectedUserList.add(connectedUser);
				}
				
				
				String sendMessageJson1 = gson.toJson(signinMessage);
				sendMessage(session, sendMessageJson1);
				break;
			case "Login"://ログイン
				Certification loginMessage = gson.fromJson(message, Certification.class);
				loginMessage.result = loginController.login(loginMessage.username, loginMessage.password);
				//デバック
				System.out.println("ログイン成功"+loginMessage.result);
				//ログイン成功したら接続ユーザーに登録する
				if (loginMessage.result) {

					ConnectedUser connectedUser = new ConnectedUser(loginMessage.username, session);
					connectedUserList.add(connectedUser);
					//デバック
					System.out.println("接続ユーザー追加");
				}
				String sendMessageJson2 = gson.toJson(loginMessage);
				sendMessage(session, sendMessageJson2);
				break;
			case "Logout"://ログアウト
				loginController.logout(receivedMessage.username);
				break;
			case "Matching"://マッチング
				userController.addUser(receivedMessage.username);
				break;
			case "MatchingCancel"://マッチングキャンセル
				userController.deleteUser(receivedMessage.username);
				break;
			default:
				System.out.println("無効な要求です");
		}
	}


	@OnClose//切断時の処理
	public void onClose(Session session) {
		System.out.println("[WebSocketServer] onClose:" + session.getId());
		//このセッションのユーザー名を調べる
	    for (ConnectedUser connectedUser : connectedUserList) {
	        if (connectedUser.session.equals(session)) {
				String closeUsername = connectedUser.username;
				connectedUserList.remove(connectedUser);

				//そのユーザーのマッチングキャンセルする
				userController.deleteUser(closeUsername);

				//ログアウトする
				loginController.logout(closeUsername);
				return;
	        }
	    }

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

	//ユーザーネームからSessionを獲得する
	public static Session getSession(String username) {
		for (ConnectedUser connectedUser : connectedUserList) {
			if (connectedUser.username.equals(username)) {
				return connectedUser.session;
			}
		}
		return null;
	}

	public void sendBroadcastMessage(String message) {
		System.out.println("[WebSocketServer] sendBroadcastMessage(): " + message);
		for(ConnectedUser connectedUser : connectedUserList) {
			connectedUser.session.getAsyncRemote().sendText(message);
		}
	}
}
