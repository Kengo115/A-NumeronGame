package applicationServer.controller;

import applicationServer.entity.*;
import applicationServer.entity.Target;
import databaseServer.controller.DataBaseController;
import Message.*;

import java.util.Random;

public class GameController {
    int isWinner = 0;
    int turn = 0;
    int rand;
    Player player1;
    Player player2;
    Player firstPlayer; //先攻
    Player secondPlayer; //後攻
    Item HighAndLow = new HighAndLow();
    Item Slash = new Slash();
    Item Target = new Target();
    String Item1 = "HighAndLow";
    String Item2 = "Slash";
    String Item3 = "TargetMessage";
    int EAT;
    int BITE;

    Message message;

    public GameController(Player player1, Player player2){
        this.player1 = player1;
        this.player2 = player2;
        determineTurnOrder();
    }

    public void setMessage(Message message){
        this.message = message;
    }
    public int getTurn(){
        return turn;
    }

    public int getIsWinner(){
        return isWinner;
    }

    /**先行か後攻か決めるメソッド*/
    public void determineTurnOrder(){
        // 0か1のランダムな数を生成し、それが0ならplayer1が先攻、1ならplayer2が先攻とする
        Random random = new Random();
        rand = random.nextInt(2);

        if(rand == 0){
            this.player1.setTurnOrder(rand);
            this.player2.setTurnOrder(rand+1);
            firstPlayer = player1;
            secondPlayer = player2;
        }else{
            this.player2.setTurnOrder(rand-1);
            this.player1.setTurnOrder(rand);
            firstPlayer = player2;
            secondPlayer = player1;
        }
    }

    /** CALL結果を渡すメソッド*/
    public CallMessage determineEATAndBITE(CallMessage message){
        if(message.username == player1.getUserName()){
            String[] predictArray = message.callNumber.split("");
            String[] enemyNumArray = this.player2.getMyNumber().split("");
            for(int i = 1; i < predictArray.length; ++i) {
                for(int j = 1; j < predictArray.length; ++j) {
                    if (predictArray[i].equals(enemyNumArray[j])) {
                        if (i == j) {
                            ++EAT;
                        } else {
                            ++BITE;
                        }
                    }
                }
            }
            player1.setEATAndBITE(EAT, BITE);
        }else if(message.username == player2.getUserName()){
            String[] predictArray = message.callNumber.split("");
            String[] enemyNumArray = this.player1.getMyNumber().split("");
            for(int i = 1; i < predictArray.length; ++i) {
                for(int j = 1; j < predictArray.length; ++j) {
                    if (predictArray[i].equals(enemyNumArray[j])) {
                        if (i == j) {
                            ++EAT;
                        } else {
                            ++BITE;
                        }
                    }
                }
            }
            player2.setEATAndBITE(EAT, BITE);
        }
        CallMessage callMessage = new CallMessage(message.demandType, message.username, message.callNumber, EAT, BITE);

        return callMessage;
    }

    /**ゲームが終了したかどうか判断するメソッド*/
    public boolean isFinish(){
        if (isWinner == 0) {
            // 先攻が3EATの場合
            if (turn % 2 == 0 && firstPlayer.getEAT() == 3) {
                isWinner += 1;
            }
            // 後攻が3EATの場合
            else if (turn % 2 != 0 && secondPlayer.getEAT() == 3) {
                isWinner += 2;
            }
        }

        // ターンが終了した場合
        if (turn % 2 != 0) {
            switch (isWinner) {
                case 1:
                    // 先攻が勝利した時の記述を書く
                    System.out.println("先攻が勝利しました。");
                    return true;
                case 2:
                    // 後攻が勝利した時の記述を書く
                    System.out.println("後攻が勝利しました。");
                    return true;
                case 3:
                    // 引き分けした時の記述を書く
                    System.out.println("引き分けです。");
                    return true;
                default:
                    // ゲームが続行中
                    break;
            }
        }
        return false;
    }
    /**ターンを更新する*/
    public void changeTurn(){
        turn++;
    }
    /**自分の設定ナンバーを決める*/
    public void setNumber(String player, String number){
        if(player == player1.getUserName()){
            this.player1.setMyNumber(number);
            this.player2.setOpponentNumber(number);
        }else if(player == player2.getUserName()){
            this.player2.setMyNumber(number);
            this.player1.setOpponentNumber(number);
        }else{
            System.out.println("ユーザ名が登録されていません");
        }
    }

    /**
     * タイムアウトしたユーザがいた時に呼ばれるメソッド
     * 正常に処理が成功した場合tureを返す
     * 型や返り値、SQL文の仕様が変わる可能性大
     */
    public boolean handleTimeout(String errorPlayer, String normalPlayer){
        DataBaseController dataBaseController = new DataBaseController();
        // 制限時間を超えたユーザの敗北数を1増やす
        String updateLoseQuery = "UPDATE UserList SET loseCount = loseCount + 1 WHERE UserName = '"+errorPlayer+"'";
        boolean TF1 = dataBaseController.executeUpdate(updateLoseQuery);

        // 相手ユーザの勝利数を1増やす
        String updateWinQuery = "UPDATE UserList SET winCount = winCount + 1 WHERE UserName = '"+normalPlayer+"'";
        boolean TF2 = dataBaseController.executeUpdate(updateWinQuery);

        // 制限時間を超えたユーザをログアウト状態にする
        String updateLogoutQuery = "UPDATE UserList SET isLoggedIn = false WHERE UserName = '"+errorPlayer+"'";
        boolean TF3 = dataBaseController.executeUpdate(updateLogoutQuery);

        if((TF1 && TF2 && TF3) == true) return true;
        else return false;
    }
    /**
     * 使用するアイテムに応じてアイテムクラスを呼び出すメソッド
     */
    public ItemMessage useItem(ItemMessage message){
        if(message.itemName == Item1){
            return HighAndLow.useItem(message);
        }else if(message.itemName == Item2){
            return Slash.useItem(message);
        }else if(message.itemName == Item3){
            return Target.useItem(message);
        }
        return null;
    }
    public TargetMessage useTarget(TargetMessage message){
        return Target.useTarget(message);
    }
    /**GameControllerにプレイヤーがいるかどうか判断するメソッド*/
    public boolean hasPlayer(Player playerName) {
        return player1.equals(playerName) || player2.equals(playerName);
    }
    /**GameControllerにそのユーザ名のPlayerがいるか判断するメソッド*/
    public boolean hasUser(String userName){

        //デバック
        System.out.println("プレイヤー1="+player1.getUserName());
        System.out.println("プレイヤー2="+player2.getUserName());

        return player1.getUserName().equals(userName) || player2.getUserName().equals(userName);
    }
}
