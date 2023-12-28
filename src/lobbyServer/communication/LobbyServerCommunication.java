package lobbyServer.communication;

import java.io.IOException;

import javax.websocket.ClientEndpoint;
import javax.websocket.Session;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;

import Message.MakeRoomMessage;
import Message.MatchingMessage;
import org.glassfish.tyrus.server.Server;

import com.google.gson.Gson;

@ClientEndpoint
public class LobbyServerCommunication {
	static String contextRoot = "/lobby";
	static String protocol = "ws";
	static int port = 8080;
	private Client client = ClientBuilder.newClient();
	
	private final String appServerUri = "http://localhost:8082";
	private final String makeRoomPath = "/makeRoom";
	static Gson gson = new Gson();


    public static void main(String[] args) throws Exception {
        Server server = new Server(protocol, port, contextRoot, null, WebSocketEndpoint.class);
        
        try {
            server.start();
            System.in.read();
        } finally {
            server.stop();
        }
    }
	
	public LobbyServerCommunication() {
	}


	// 部屋作成通信を作る
	public void makeRoom(String username1,String username2) {

		//デバック文
		System.out.println("makeroom到達");

		WebTarget target = client.target(appServerUri).path(makeRoomPath);

		MakeRoomMessage makeRoomMessage = new MakeRoomMessage("MakeRoom",username1,username2);

		target.request(MediaType.APPLICATION_JSON_TYPE)
			.post(Entity.entity(gson.toJson(makeRoomMessage), MediaType.APPLICATION_JSON), String.class);

		//デバック文
		System.out.println("APサーバ伝送完了");

		//ユーザ１に返信
		//返信先のセッションを獲得
		Session user1Session = WebSocketEndpoint.getSession(username1);
		if(user1Session == null) {
			System.out.println("user1無効なセッション");
			return;
		}
		MatchingMessage matchingMessage = new MatchingMessage("Matching",username1);
		sendMessage(user1Session,gson.toJson(matchingMessage));
		//ユーザ２に返信
		//返信先のセッションを獲得
		Session user2Session = WebSocketEndpoint.getSession(username2);
		if(user2Session == null) {
			System.out.println("user2無効なセッション");
			return;
		}
		matchingMessage = new MatchingMessage("Matching",username2);
		sendMessage(user2Session,gson.toJson(matchingMessage));

		//デバック文
		System.out.println("クライアント伝送完了");
	}
	
	//返信する
	public void sendMessage(Session session, String message) {
		System.out.println("[WebSocketServer] sendMessage(): " + message);
		try {
			// 同期送信（sync）
			session.getBasicRemote().sendText(message);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
