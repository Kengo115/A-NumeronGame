package client.communication;

import java.io.IOException;
import java.net.URI;

import javax.websocket.ContainerProvider;
import javax.websocket.Session;
import javax.websocket.WebSocketContainer;

public class WebSocketManager {

	Session session; //セッション
	WebSocketContainer container; //webSocketのもの
	URI uri; //接続先のサーバIP

	WebSocketManager(String uriString) { 
		container = ContainerProvider.getWebSocketContainer();
	    uri = URI.create(uriString);
	}

	public boolean isConnected() { //接続確認
		return session.isOpen();
	}

	public void sendMessage(String text) { //メッセージを送る
		try {
			session.getBasicRemote().sendText(text);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public boolean connect() { //接続処理
		try {
			session = container.connectToServer(new WebSocketEndpoint(), uri);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public void disconnect() throws IOException { //切断処理
		if(!session.isOpen()) {
			session.close();
		}
	}
}