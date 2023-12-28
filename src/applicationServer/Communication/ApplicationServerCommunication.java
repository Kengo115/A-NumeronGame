package applicationServer.Communication;

import java.net.URI;

import Message.ResultMessage;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.tyrus.server.Server;

public class ApplicationServerCommunication {
	static String contextRoot = "/app";
	static String protocol = "ws";
	static int port = 8081;
    public static final String restUri = "http://localhost:8082";


    public static void main(String[] args) throws Exception {
        Server server = new Server(protocol, port, contextRoot, null, WebSocketEndpoint.class);
        final ResourceConfig rc = new ResourceConfig().packages("applicationServer.Communication");
        final HttpServer restServer = GrizzlyHttpServerFactory.createHttpServer(URI.create(restUri), rc);

        
        try {
            server.start();
            System.in.read();
        } finally {
            server.stop();
            restServer.shutdownNow();
        }
    }
	
	ApplicationServerCommunication() {
	}

    //結果を送信する
    public static void sendResult(ResultMessage resultMessage) {
        WebSocketEndpoint webSocketEndpoint = new WebSocketEndpoint();
        webSocketEndpoint.sendResult(resultMessage);
    }

    //時間超過を送信する
    public static void timeout(String timeoutUsername) {
        WebSocketEndpoint webSocketEndpoint = new WebSocketEndpoint();
        webSocketEndpoint.timeout(timeoutUsername);
    }

}
