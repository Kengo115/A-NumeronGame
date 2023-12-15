package client.communication;

import java.io.IOException;

import com.google.gson.Gson;

public class ClientCommunication {
	static WebSocketManager LSwsManager; //ロビーサーバ用
	static WebSocketManager APwsManager; //アプリケーションサーバ用

	
	static String LSserverEndpoint = "ws://localhost:8080/lobby/"; //ロビーサーバIP
	static String APserverEndpoint = "ws://localhost:8080/app/"; //アプリケーションサーバIP
	
    static Gson gson = new Gson(); //Gson
    
    public void LSserverConnect() { //ロビーサーバへ接続する
    	LSwsManager = new WebSocketManager(LSserverEndpoint);
    	LSwsManager.connect();
    }

    public void login(String username,String password) { //ログインのメッセージを送る

    	LSserverConnect();

    	if(LSwsManager.isConnected()) {
    		Certification sendMessage = new Certification("Login",username, password);
    		String sendMessageJson = gson.toJson(sendMessage);
    		LSwsManager.sendMessage(sendMessageJson);
    	}
    }
    public void signin(String username,String password) { //ログインのメッセージを送る

    	LSserverConnect();

    	if(LSwsManager.isConnected()) {
    		Certification sendMessage = new Certification("Signin",username, password);
    		String sendMessageJson = gson.toJson(sendMessage);
    		LSwsManager.sendMessage(sendMessageJson);
    	}
    }

    public void logout() {//ログアウトのメッセージ送る
    	
		if(LSwsManager.isConnected()) {
			LogoutMessage sendMessage = new LogoutMessage("Logout");
			String sendMessageJson = gson.toJson(sendMessage);
			LSwsManager.sendMessage(sendMessageJson);
		}
    }
    
    public void LSdisconnect() { //通信を切断する・
    	try {
			LSwsManager.disconnect();
		} catch (IOException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
    }

    
    
}