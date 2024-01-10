package applicationServer.controller;

import java.util.Random;

import Message.CallMessage;
import Message.ItemMessage;
import Message.ResultMessage;
import applicationServer.entity.GameTimer;
import applicationServer.entity.HighAndLow;
import applicationServer.entity.Item;
import applicationServer.entity.Player;
import applicationServer.entity.Slash;
import applicationServer.entity.Target;
import databaseServer.controller.DataBaseController;

public class GameController {
    int isWinner = 0;
    int turn = 0;
    int rand;
    Player player1 = null;
    Player player2 = null;
    Player firstPlayer = null; //先攻
    Player secondPlayer = null; //後攻
    Item HighAndLow = new HighAndLow();
    Item Slash = new Slash();
    Item Target = new Target();
    String Item1 = "HighAndLow";
    String Item2 = "Slash";
    String Item3 = "Target";

    GameTimer gameTimer = new GameTimer(this);



    public GameController(Player player1, Player player2){
        this.player1 = player1;
        this.player2 = player2;
        System.out.println("初期値確認");
        System.out.println("["+player1.getUserName()+","+player1.getMyNumber()+","+player1.getTurnOrder()+","+player1.getEAT()+"]");
        System.out.println("初期値確認");
        System.out.println("["+player2.getUserName()+","+player2.getMyNumber()+","+player2.getTurnOrder()+","+player2.getEAT()+"]");
        determineTurnOrder();
    }

    public int getTurn(){
        return turn;
    }

    public int getIsWinner(){
        return isWinner;
    }

    public Player getFirstPlayer() {
        return firstPlayer;
    }

    public Player getSecondPlayer() {
        return  secondPlayer;
    }

    /**先行か後攻か決めるメソッド*/
    public void determineTurnOrder(){
        // 0か1のランダムな数を生成し、それが0ならplayer1が先攻、1ならplayer2が先攻とする
        Random random = new Random();
        rand = random.nextInt(2);

        if(rand == 0){
            this.player1.setTurnOrder(0);
            this.player2.setTurnOrder(1);
            firstPlayer = player1;
            secondPlayer = player2;
        }else{
            this.player2.setTurnOrder(0);
            this.player1.setTurnOrder(1);
            firstPlayer = player2;
            secondPlayer = player1;
        }
        gameTimer.startTimer();
    }

    /** CALL結果を渡すメソッド*/
    public CallMessage determineEATAndBITE(CallMessage message){

        //タイマーをリセットする
        gameTimer.resetTimer();

        int EAT = 0;
        int BITE = 0;
        if(message.username.equals(player1.getUserName())){
            String[] predictArray = message.callNumber.split("");
            String[] enemyNumArray = this.player2.getMyNumber().split("");
            for(int i = 0; i < predictArray.length; ++i) {
                for(int j = 0; j < predictArray.length; ++j) {
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
        }else if(message.username.equals(player2.getUserName())){
            String[] predictArray = message.callNumber.split("");
            String[] enemyNumArray = this.player1.getMyNumber().split("");
            for(int i = 0; i < predictArray.length; ++i) {
                for(int j = 0; j < predictArray.length; ++j) {
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
        // 先攻が3EATの場合
        isWinner =0;
        if (firstPlayer.getEAT() == 3) {
            isWinner += 1;
        }
        // 後攻が3EATの場合
        if (secondPlayer.getEAT() == 3) {
            isWinner += 2;
        }

        //デバック
        System.out.println("turn ="+turn);
        System.out.println("firstPlayerEat"+firstPlayer.getEAT());
        System.out.println("secondPlayerEat"+secondPlayer.getEAT());

        DataBaseController dataBaseController = new DataBaseController();

        // ターンが終了した場合
        if (turn % 2 != 0) {
            switch (isWinner) {
                case 1:
                    // 先攻が勝利した時の記述を書く
                    System.out.println("先攻が勝利しました。");
                    String insertQuery1 = "UPDATE UserList SET rate = rate + 100 WHERE UserName = '" + firstPlayer.getUserName() + "'";
                    String insertQuery2 = "UPDATE UserList SET rate = rate - 100 WHERE UserName = '" + secondPlayer.getUserName() + "'";
                    String insertQuery3 = "UPDATE UserList SET  winCount = winCount + 1 WHERE UserName = '" + firstPlayer.getUserName() + "'";
                    String insertQuery4 = "UPDATE UserList SET loseCount = loseCount + 1 WHERE UserName = '" + secondPlayer.getUserName() + "'";
                    dataBaseController.executeUpdate(insertQuery1);
                    dataBaseController.executeUpdate(insertQuery2);
                    dataBaseController.executeUpdate(insertQuery3);
                    dataBaseController.executeUpdate(insertQuery4);
                    return true;
                case 2:
                    // 後攻が勝利した時の記述を書く
                    System.out.println("後攻が勝利しました。");
                    String insertQuery5 = "UPDATE UserList SET rate = rate + 100 WHERE UserName = '" + secondPlayer.getUserName() + "'";
                    String insertQuery6 = "UPDATE UserList SET rate = rate - 100 WHERE UserName = '" + firstPlayer.getUserName() + "'";
                    String insertQuery7 = "UPDATE UserList SET  winCount = winCount + 1 WHERE UserName = '" + secondPlayer.getUserName() + "'";
                    String insertQuery8 = "UPDATE UserList SET loseCount = loseCount + 1 WHERE UserName = '" + firstPlayer.getUserName() + "'";
                    dataBaseController.executeUpdate(insertQuery5);
                    dataBaseController.executeUpdate(insertQuery6);
                    dataBaseController.executeUpdate(insertQuery7);
                    dataBaseController.executeUpdate(insertQuery8);
                    return true;
                case 3:
                    // 引き分けした時の記述を書く
                    System.out.println("引き分けです。");
                    String insertQuery9 = "UPDATE UserList SET drawCount = drawCount + 1 WHERE UserName = '" + firstPlayer.getUserName() + "'";
                    String insertQuery10 = "UPDATE UserList SET drawCount = drawCount + 1 WHERE UserName = '" +  secondPlayer.getUserName() + "'";
                    dataBaseController.executeUpdate(insertQuery9);
                    dataBaseController.executeUpdate(insertQuery10);
                    return true;
                default:
                    // ゲームが続行中
                    System.out.println("ゲーム進行中");

                    break;
            }
        }
        return false;
    }
    /**ターンを更新する*/
    public void changeTurn(){

        turn++;

        //タイマーを開始する
        gameTimer.startTimer();
    }
    /**自分の設定ナンバーを決める*/
    public void setNumber(String player, String number){
        //タイマーをリセットする
        gameTimer.resetTimer();

        if(player.equals(player1.getUserName())){
            this.player1.setMyNumber(number);
            this.player2.setOpponentNumber(number);
        }else if(player.equals(player2.getUserName())){
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
    public void handleTimeout(String errorPlayer,String normalPlayer){

        //デバック
        System.out.println("handleTimeout到達");

        DataBaseController dataBaseController = new DataBaseController();
        // 制限時間を超えたユーザの敗北数を1増やしレートを下げる
        String updateLoseQuery = "UPDATE UserList SET loseCount = loseCount + 1 WHERE UserName = '"+errorPlayer+"'";

        String rateDownQuery = "UPDATE UserList SET rate = rate - 100 WHERE UserName = '" + errorPlayer + "'";
        boolean TF1 = dataBaseController.executeUpdate(updateLoseQuery);
        dataBaseController.executeUpdate(rateDownQuery);

        // 相手ユーザの勝利数を1増やしレートを上げる
        String updateWinQuery = "UPDATE UserList SET winCount = winCount + 1 WHERE UserName = '"+normalPlayer+"'";
        String rateUpQuery = "UPDATE UserList SET rate = rate + 100 WHERE UserName = '" + normalPlayer + "'";
        boolean TF2 = dataBaseController.executeUpdate(updateWinQuery);
        dataBaseController.executeUpdate(rateUpQuery);

        // 制限時間を超えたユーザをログアウト状態にする
        String updateLogoutQuery = "UPDATE UserList SET isLoggedIn = false WHERE UserName = '"+errorPlayer+"'";
        boolean TF3 = dataBaseController.executeUpdate(updateLogoutQuery);

        //デバック
        System.out.println("SQL処理終了");

    }
    /**
     * 使用するアイテムに応じてアイテムクラスを呼び出すメソッド
     */
    public ItemMessage useItem(ItemMessage message){
        //デバック
        System.out.println("ゲームコントローラー.useItem到達");

        if(message.itemName.equals(Item1)){
            return HighAndLow.useItem(message);
        }else if(message.itemName.equals(Item2)){
            return Slash.useItem(message);
        }else if(message.itemName.equals(Item3)){
            return Target.useItem(message);
        }
        return null;
    }
    public ResultMessage sendResult(){
        if(isWinner == 1){
            ResultMessage resultMessage = new ResultMessage("Result", firstPlayer.getUserName(),player1.getUserName(), player2.getUserName());
            return resultMessage;
        }else if(isWinner == 2){
            ResultMessage resultMessage = new ResultMessage("Result", secondPlayer.getUserName(),player1.getUserName(), player2.getUserName());
            return resultMessage;
        }else if(isWinner == 3){
            ResultMessage resultMessage = new ResultMessage("Result", "draw",player1.getUserName(), player2.getUserName());
            return resultMessage;
        }
        return null;
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
