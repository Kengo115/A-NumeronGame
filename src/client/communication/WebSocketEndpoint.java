package client.communication;
import javax.websocket.ClientEndpoint;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;

import com.google.gson.Gson;

import Message.Certification;
import Message.Message;
import Message.PlayFirstMessage;
import client.controller.Controller;


@ClientEndpoint
public class WebSocketEndpoint {
	static Gson gson = new Gson(); //Gson
	static ClientCommunication clientCommunication; //クライアントコミュニケーション
	
	static Controller controller;
	
	//コントローラー同期
	public void synchroController(Controller controller) {
		WebSocketEndpoint.controller=controller;
	}
	
	//クライアントコミュニケーション同期
	public void synchroClientCommunication(ClientCommunication clientCommunication) {
		WebSocketEndpoint.clientCommunication = clientCommunication;
	}
	
	@OnOpen //開通時のメッセージ
	public void onOpen(Session session) {
		System.out.println("[client] onOpen" + session.getId());
	}

	@OnMessage //メッセージ受信
	public void onMessage(String message) {
		
		//デバック
    	System.out.println("クライアントデータ受信");

		// メッセージクラスに変換
        Message receivedMessage = gson.fromJson(message, Message.class);
        
		//デバック
    	System.out.println("メッセージクラスへ変換完了");
        
        switch (receivedMessage.demandType) { //受け取ったメッセージの挙動に応じで変化
        	case "Signin"://会員登録
        		Certification signinMessage = gson.fromJson(message, Certification.class);
        		
        		//デバック
            	System.out.println("認証メッセージクラスへ変換完了");
        		
        		if(signinMessage.result) {
        			//デバック
                	System.out.println("会員登録成功を認識");
        			
                	 controller.screenTransition("lobby");
        			
        			
        		}
        		else {
        			controller.displayError("会員登録に失敗しました。");
        		}
        		break;
        	case "Login"://ログイン
        		Certification loginMessage = gson.fromJson(message, Certification.class);
        		if(loginMessage.result) {
        			 controller.screenTransition("lobby");
        		}
        		else {
        			controller.displayError("ログインに失敗しました。");
        		}
        		break;
        	case "Logout"://ログアウト
        		 controller.screenTransition("title");
        		clientCommunication.LSDisconnect();
        		break;
			case "Matching"://マッチング開始
				//アプリケーションサーバに接続する
				clientCommunication.APServerConnect();
				//先攻後攻を聞きに行く
				clientCommunication.playFirst();
				break;
			case "PlayFirst": //先攻後攻通知
				PlayFirstMessage playFirstMessage = gson.fromJson(message, PlayFirstMessage.class);
				if(playFirstMessage.playFirst) { //先攻の時
					System.out.println("あなたは先攻です");
				}
				else { //後攻の時
					System.out.println("あなたは後攻です");
				}
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