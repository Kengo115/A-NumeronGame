package client.communication;

import java.io.IOException;

import Message.*;
import com.google.gson.Gson;

public class ClientCommunication {
	static WebSocketManager LSwsManager; //ロビーサーバ用
	static WebSocketManager APwsManager; //アプリケーションサーバ用

	
	static String LSserverEndpoint = "ws://localhost:8080/lobby/sample"; //ロビーサーバIP
	static String APserverEndpoint = "ws://localhost:8081/app/sample"; //アプリケーションサーバIP
	
	static String username; //ユーザーネーム
	
    static Gson gson = new Gson(); //Gson
    
    
    //コンストラクタ
    public ClientCommunication() {
    	WebSocketEndpoint webSocketEndpoint = new WebSocketEndpoint();
    	webSocketEndpoint.synchroClientCommunication(this);
    }

	public static String getUsername() {
		return username;
	}

	public void LServerConnect() { //ロビーサーバへ接続する
    	LSwsManager = new WebSocketManager(LSserverEndpoint);
    	LSwsManager.connect();
    }

	public void APServerConnect() { //アプリケーションサーバへ接続する
		APwsManager = new WebSocketManager(APserverEndpoint);
		APwsManager.connect();
	}

    public void login(String username,String password) { //ログインのメッセージを送る
    	
    	//デバック
    	System.out.println("ClientCommunication.login到達");

    	LServerConnect();

    	if(LSwsManager.isConnected()) {
    		ClientCommunication.username = username;
    		Certification sendMessage = new Certification("Login",username, password);
    		String sendMessageJson = gson.toJson(sendMessage);
    		LSwsManager.sendMessage(sendMessageJson);
    	}
    }
    public void signin(String username,String password) { //ログインのメッセージを送る
    	
    	//デバック
    	System.out.println("ClientCommunication.signin到達");

    	LServerConnect();

    	if(LSwsManager.isConnected()) {
    		ClientCommunication.username = username;
    		Certification sendMessage = new Certification("Signin",username, password);
    		String sendMessageJson = gson.toJson(sendMessage);
    		LSwsManager.sendMessage(sendMessageJson);
    	}
    }

    public void logout() {//ログアウトのメッセージ送る
    	
		if(LSwsManager.isConnected()) {
			LogoutMessage sendMessage = new LogoutMessage("Logout",ClientCommunication.username);
			String sendMessageJson = gson.toJson(sendMessage);
			LSwsManager.sendMessage(sendMessageJson);
		}
    }

	public void showRecord() {//戦績表示のメッセージを送る

		System.out.println("ClientCommunication.showRecord到達");
		if(LSwsManager.isConnected()) {
			RecordMessage sendMessage = new RecordMessage("Record",username);
			String sendMessageJson = gson.toJson(sendMessage);
			LSwsManager.sendMessage(sendMessageJson);
		}
	}



	public void addUser() {//待機ユーザー追加処理を送る

		System.out.println("ClientCommunication.addUser到達");
		if(LSwsManager.isConnected()) {
			MatchingMessage sendMessage = new MatchingMessage("Matching",username);
			String sendMessageJson = gson.toJson(sendMessage);
			LSwsManager.sendMessage(sendMessageJson);
		}

	}

	public void cancel() {//キャンセルの処理(クラス図を参照)

		System.out.println("ClientCommunication.cancel到達");
		if(LSwsManager.isConnected()) {
			MatchingCancelMessage sendMessage = new MatchingCancelMessage("MatchingCancel",username);
			String sendMessageJson = gson.toJson(sendMessage);
			LSwsManager.sendMessage(sendMessageJson);
		}
	}

	public void playFirst() {//先攻後攻通知

		System.out.println("ClientCommunication.playFirst到達");
		if(APwsManager.isConnected()) {
			PlayFirstMessage sendMessage = new PlayFirstMessage("PlayFirst",username);
			String sendMessageJson = gson.toJson(sendMessage);
			APwsManager.sendMessage(sendMessageJson);
		}
	}

	public void checkNumber(String number) {//設定ナンバーを送る

		System.out.println("ClientCommunication.checkNumber到達");
		if(APwsManager.isConnected()) {
			SetNumberMessage sendMessage = new SetNumberMessage("SetNumber",username,number);
			String sendMessageJson = gson.toJson(sendMessage);
			APwsManager.sendMessage(sendMessageJson);
		}
	}

	public void selectItem(String item,int number){//アイテム使用を送る

		String result="NULL";//空文字を初期値として送る
		System.out.println("ClientCommunication.selectItem到達");
		if(APwsManager.isConnected()){
			//分岐処理
			if(item.equals("Target")) {
				ItemMessage itemMessage = new ItemMessage("Item",username,item,result,number);
				String sendMessageJson = gson.toJson(itemMessage);
				APwsManager.sendMessage(sendMessageJson);
			}
			else {
				ItemMessage sendMessage = new ItemMessage("Item",username,item,result);
				String sendMessageJson = gson.toJson(sendMessage);
				APwsManager.sendMessage(sendMessageJson);
			}
		}
	}

	public void callCheck(String number){//コールした番号とEAT,BITEの判別要求を送る

		int EAT=0;
		int BITE=0;//0を初期値として送る
		System.out.println("ClientCommunication.callCheck到達");
		if (APwsManager.isConnected()) {
			CallMessage sendMessage = new CallMessage("Call",username,number,EAT,BITE);
			String sendMessageJson = gson.toJson(sendMessage);
			APwsManager.sendMessage(sendMessageJson);
		}

	}

	public void LSDisconnect() { //ロビーサーバとの通信を切断する・
    	try {
			LSwsManager.disconnect();
		} catch (IOException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
    }

	public void APDisconnect() { //アプリケーションサーバとの通信を切断する・
		try {
			APwsManager.disconnect();
		} catch (IOException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
	}

    
    
}