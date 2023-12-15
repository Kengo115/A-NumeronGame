package client.communication;
import javax.websocket.ClientEndpoint;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;

import com.google.gson.Gson;

import client.controller.Controller;


@ClientEndpoint
public class WebSocketEndpoint {
	static Gson gson = new Gson(); //Gson
	ClientCommunication clientCommunication; //クライアントコミュニケーション
	
	Controller controller; //コントローラー

	@OnOpen //開通時のメッセージ
	public void onOpen(Session session) {
		System.out.println("[client] onOpen" + session.getId());
	}

	@OnMessage //メッセージ受信
	public void onMessage(String message) {

		// メッセージクラスに変換
        Message receivedMessage = gson.fromJson(message, Message.class);
        
        switch (receivedMessage.demandType) { //受け取ったメッセージの挙動に応じで変化
        	case "Signin"://会員登録
        	case "Login"://ログイン
        		Certification certificationMessage = gson.fromJson(message, Certification.class);
        		if(certificationMessage.result) {
        			controller.screenTransition("Lobby");
        		}
        		{
        			controller.displayError("ログインに失敗しました。");
        		}
        		break;
        	case "Logout":
        		controller.screenTransition("Title");
        		clientCommunication.LSdisconnect();
        		
        		break;
        	default:
        		System.out.println("無効な要求です。");
        }	
	}

	@OnError //エラー発生時の処理
	public void onError(Throwable t) {
		System.out.println("[client] onError");
	}

	@OnClose //切断時の処理
	public void onClose(Session session) {
		System.out.println("[client] onClose: " + session.getId());
	}

}