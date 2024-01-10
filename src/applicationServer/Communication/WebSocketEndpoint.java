package applicationServer.Communication;
import java.io.IOException;
import java.util.ArrayList;
import javax.websocket.EndpointConfig;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import applicationServer.controller.ApplicationController;
import applicationServer.entity.Player;
import com.google.gson.Gson;

import Message.*;


// エンドポイントは適宜変更する
@ServerEndpoint("/sample")
public class WebSocketEndpoint {
    private static final ArrayList<ConnectedUser> connectedUserList = new ArrayList<>(); //接続ユーザ

    private static final ApplicationController applicationController = new ApplicationController(); //アプリケーションコントローラ

    static Gson gson = new Gson();

    @OnOpen //開通時のメッセージ
    public void onOpen(Session session, EndpointConfig ec) {
        //デバック
        System.out.println("アプリケーションサーバ開通");

        ConnectedUser connectedUser = new ConnectedUser(null,session);
        connectedUserList.add(connectedUser);
        System.out.println("[WebSocketServer] onOpen:" + session.getId());
    }


    @OnMessage
    public void onMessage(final String message, final Session session) throws IOException {
        String opponentUsername; //対戦相手の名前
        String messageToJson; //メッセージをJsonでString型にしたもの

        //デバック
        System.out.println("アプリケーションサーバデータ受信");



        // メッセージクラスに変換：String -> Message
        Message receivedMessage = gson.fromJson(message, Message.class);

        //デバック
        System.out.println("メッセージクラスへ変換");

        //もしまだConnectedUserのユーザーネームの登録がされていない場合はする。
        for(ConnectedUser connectedUser : connectedUserList) {
            if(connectedUser.session == session) {
                if(connectedUser.username == null) {
                    connectedUser.username = receivedMessage.username;
                }
            }
        }

        //デバック
        System.out.println("ユーザー登録完了");


        switch (receivedMessage.demandType) {//受け取った要求に応じて変化
            case "PlayFirst"://先攻後攻
                Player player = applicationController.getPlayer(receivedMessage.username);

                //デバック
                System.out.println("プレイヤー取得");
                System.out.println("プレイヤー名="+player.getUserName());

                PlayFirstMessage playFirstMessage = gson.fromJson(message, PlayFirstMessage.class);

                //デバック
                System.out.println("プレイファーストメッセージ変換");

                if(player.getTurnOrder() == 0) {
                    // メッセージクラスに変換：String -> PlayFirstMessage
                    //デバック
                    System.out.println(player.getUserName()+"は先攻です");
                    playFirstMessage.playFirst = true;
                }
                else {
                    //デバック
                    System.out.println(player.getUserName()+"は後攻です");
                    playFirstMessage.playFirst = false;
                }
                sendMessage(session,gson.toJson(playFirstMessage));
                break;
            case "SetNumber":
                // SetNumberの場合の処理
                SetNumberMessage setNumberMessage  = gson.fromJson(message, SetNumberMessage.class);
                applicationController.setNumber(setNumberMessage);

                //デバック
                System.out.println("設定ナンバーの登録成功");

                applicationController.changeTurn(setNumberMessage.username);


                //対戦相手に設定ナンバーを送ったことを送信
                sendMessageToOpponent(setNumberMessage.username,message);
                break;
            case "Call":
                // Callの場合の処理
                CallMessage callMessage  = gson.fromJson(message, CallMessage.class);
                callMessage = applicationController.determineEATAndBITE(callMessage);
                if(callMessage==null) { //ゲーム終了時
                    return;
                }
                applicationController.changeTurn(callMessage.username);
                messageToJson  = gson.toJson(callMessage);
                sendMessage(session,messageToJson);
                //対戦相手にも結果を送る
                sendMessageToOpponent(callMessage.username,messageToJson);
                break;
            case "Item":
                // Itemの場合の処理
                ItemMessage itemMessage  = gson.fromJson(message, ItemMessage.class);

                //デバック
                System.out.println("アイテムメッセージ変換");

                itemMessage = applicationController.useItem(itemMessage);
                messageToJson = gson.toJson(itemMessage);
                sendMessage(session,messageToJson);
                //対戦相手にも結果を送る
                sendMessageToOpponent(itemMessage.username,messageToJson);
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
                //この切断が予定されているものかどうか判断する
                if(connectedUser.close) {
                    //予定されているものなら何もしない
                    return;
                }

                String closeUsername = connectedUser.username;
                //対戦相手を探す
                String opponentUsername = applicationController.getOpponentUsername(closeUsername);
                if(opponentUsername == null) {
                    //対戦相手いなかったら終わりにする
                    return;
                }
                ErrorGameEndMessage errorGameEndMessage = new ErrorGameEndMessage("ErrorGameEnd",closeUsername,opponentUsername);
                System.out.println("エラーゲームエンドメッセージ作成");

                applicationController.handleTimeout(errorGameEndMessage);

                System.out.println("handleTimeout完了");

                //対戦相手にエラーメッセージを送る
                sendMessageToOpponent(closeUsername,gson.toJson(errorGameEndMessage));
                System.out.println("対戦相手にエラーメッセージを送る");

                //対象のユーザーを接続ユーザーのリストから外す
                connectedUserList.remove(connectedUser);

                //対戦相手の切断処理を行う
                disconnectConnection(opponentUsername);

                //部屋を解散する
                applicationController.deleteRoom(applicationController.getPlayer(closeUsername));
                System.out.println("部屋を解散する");
                return;
            }
        }

    }

    @OnError
    public void onError(Session session, Throwable error) {
        System.out.println("[WebSocketServerSample] onError:" + session.getId());
    }

	public void sendMessage(Session session, String message) {
		System.out.println("[WebSocketServerSample] sendMessage(): " + message);
		try {
			// 同期送信（sync）
			session.getBasicRemote().sendText(message);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

    public void sendBroadcastMessage(String message) {
        System.out.println("[WebSocketServer] sendBroadcastMessage(): " + message);
        for(ConnectedUser connectedUser : connectedUserList) {
            connectedUser.session.getAsyncRemote().sendText(message);
        }
    }

    //対戦相手にStringに変換したメッセージを送る
    public void sendMessageToOpponent(String username , String messageToJson) {
        //対戦相手のセッションを調べる
        Session opponentSession = getSession(applicationController.getOpponentUsername(username));
        sendMessage(opponentSession,messageToJson);
    }

    //ユーザネームからセッションを手に入れる
    public Session getSession(String username) {
        for (ConnectedUser connectedUser : connectedUserList) {
            if (connectedUser.username.equals(username)) {
                return connectedUser.session;
            }
        }
        System.out.println("対応するユーザがいません");
        return null;
    }

    //結果を送信する
    public void sendResult(ResultMessage resultMessage) {
        String messageToJson = gson.toJson(resultMessage);
        //プレイヤー1のセッションを調べて結果を送信する
        Session player1Session = getSession(resultMessage.username1);
        sendMessage(player1Session,messageToJson);
        //プレイヤー2のセッションを調べて結果を送信する
        Session player2Session = getSession(resultMessage.username2);
        sendMessage(player2Session,messageToJson);

        //部屋閉じる
        applicationController.deleteRoom(applicationController.getPlayer(resultMessage.username1));

        //両者の切断処理を行う
        disconnectConnection(resultMessage.username1);
        disconnectConnection(resultMessage.username2);
    }

    //時間超過を送信する
    public void timeout(String timeoutUsername) {

        //対戦相手を探す
        String opponentUsername = applicationController.getOpponentUsername(timeoutUsername);
        if(opponentUsername == null) {
            //対戦相手いなかったら終わりにする
            return;
        }
        ErrorGameEndMessage errorGameEndMessage = new ErrorGameEndMessage("ErrorGameEnd",timeoutUsername,opponentUsername);
        applicationController.handleTimeout(errorGameEndMessage);

        //切断者と対戦相手にエラーメッセージを送る
        sendMessageToOpponent(timeoutUsername,gson.toJson(errorGameEndMessage));
        sendMessage(getSession(timeoutUsername),gson.toJson(errorGameEndMessage));

        //部屋閉じる
        applicationController.deleteRoom(applicationController.getPlayer(timeoutUsername));

        //両者の切断処理を行う
        disconnectConnection(timeoutUsername);
        disconnectConnection(opponentUsername);
    }

    //サーバ側から対象のユーザ名のユーザを切断する
    public void disconnectConnection(String username) {
        try {
            for(ConnectedUser connectedUser : connectedUserList) {
                if(connectedUser.username.equals(username)) {
                    connectedUser.close = true;
                }
            }
            getSession(username).close();
            connectedUserList.removeIf(connectedUser -> connectedUser.username.equals(username));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}

