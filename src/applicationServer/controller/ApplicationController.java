
package applicationServer.controller;

import applicationServer.entity.Player;
import Message.*;
import java.util.ArrayList;
import java.util.Iterator;

public class ApplicationController {
    /**GameControllerクラスを複数持つリスト**/

    static ArrayList<GameController> gameControllers = new ArrayList<>();
    Player player1 = new Player();/**Player型のプレイヤー1人目*/
    Player player2 = new Player();/**Player型のプレイヤー2人目*/

    /** ゲームコントローラの生成をする*/
    public void makeRoom(String player1, String player2){
        this.player1.setUserName(player1);
        this.player2.setUserName(player2);
        GameController newController = new GameController(this.player1, this.player2);
        // ゲームコントローラをリストに追加
        gameControllers.add(newController);
    }

    /**ゲームコントローラを削除する(ルームを削除する)*/
    public void deleteRoom(Player playerName) {
        Iterator<GameController> iterator = gameControllers.iterator();

        while (iterator.hasNext()) {
            GameController controller = iterator.next();
            if (controller.hasPlayer(playerName)) {
                iterator.remove();
                return; // 見つかったらループを抜ける
            }
        }
    }
    public GameController searchRoom(String userName) {
        Iterator<GameController> iterator = gameControllers.iterator();
        while (iterator.hasNext()) {
            GameController controller = iterator.next();
            if (controller.hasUser(userName)) {
                return controller;
            }
        }
        return null;
    }

    public CallMessage determineEATAndBITE(CallMessage message){
        GameController gameController = searchRoom(message.username);
        return gameController.determineEATAndBITE(message); //CallMessageクラスが返ってくる
    }

    public boolean isFinish(String userName){
        GameController gameController = searchRoom(userName);
        return gameController.isFinish();
    }

    public void changeTurn(String userName){
        GameController gameController = searchRoom(userName);
        gameController.changeTurn();
    }

    public void setNumber(SetNumberMessage message){
        GameController gameController = searchRoom(message.username);
        gameController.setNumber(message.username, message.setNumber);
    }

    public void handleTimeout(ErrorGameEndMessage message){
        GameController gameController = searchRoom(message.username);
        gameController.handleTimeout(message.errorUser, message.normalUser);
    }

    public ItemMessage useItem(ItemMessage message){
        GameController gameController = searchRoom(message.username);
        return gameController.useItem(message);
    }
    public TargetMessage useTarget(TargetMessage message){
        GameController gameController = searchRoom(message.username);
        return gameController.useTarget(message);
    }

    //ユーザ名からプレイヤークラスを返すメソッド
    public Player getPlayer(String username){

        //デバック
        System.out.println("getPlayer到達");

        GameController gameController = searchRoom(username);

        //デバック
        System.out.println(gameController.player1.getUserName());
        //デバック
        System.out.println(gameController.player2.getUserName());

        if(gameController.player1.getUserName().equals(username)){
            return player1;
        }else if(gameController.player2.getUserName().equals(username)){
            return player2;
        }
        return null;
    }

    public String getOpponentUsername(String username){
        GameController gameController = searchRoom(username);
        if(gameController.player1.getUserName().equals(username)){
            return player2.getUserName();
        }else if(gameController.player2.getUserName().equals(username)){
            return player1.getUserName();
        }
        return null;
    }
}
