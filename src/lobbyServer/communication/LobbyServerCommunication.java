package lobbyServer.communication;

import org.glassfish.tyrus.server.Server;

import javax.websocket.DeploymentException;
import java.io.IOException;

public class LobbyServerCommunication {
	static String contextRoot = "lobby";//コンテキストルート
	static String protocol = "ws";//通信プロトコル
	static int port = 8080;//ポート番号

	public static void main(String[] args) {
		Server server = new Server(protocol, port, contextRoot, null, WebSocketEndpoint.class);

		try {
			server.start();
			System.in.read();
		} catch (DeploymentException e) {
			throw new RuntimeException(e);

		} catch (IOException e) {
			throw new RuntimeException(e);
		} finally {
			server.stop();
		}
	}

}
