package client.controller;

import java.awt.CardLayout;
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JPanel;

import client.boundary.*;
import client.communication.ClientCommunication;
import client.communication.WebSocketEndpoint;

public class Controller extends JFrame {

    private CardLayout cardLayout;
    private JPanel cardPanel;
    private TitleScreen titleScreen;
    private LobbyScreen lobbyScreen;
    private MatchingWaitScreen matchingWaitScreen;
    private RuleScreen ruleScreen;
    private LoginScreen loginScreen;
    private SigninScreen signinScreen;
    private LogoutScreen logoutScreen;
    private ClientCommunication clientCommunication;
    private GameScreen gameScreen;
    private RecordScreen recordScreen;
    private ResultScreen resultScreen;

    public static void main(String[] args) {
        Controller controller = new Controller();
        controller.setTitle("Numeron");
        controller.setLocationRelativeTo(null);
        controller.setVisible(true);
    }

    public Controller() {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setPreferredSize(new Dimension(1000, 600));

        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);

        clientCommunication = new ClientCommunication();
        titleScreen = new TitleScreen(this);
        lobbyScreen = new LobbyScreen(this);
        matchingWaitScreen = new MatchingWaitScreen(this);
        ruleScreen = new RuleScreen(this);
        loginScreen = new LoginScreen(this);
        signinScreen = new SigninScreen(this);
        logoutScreen = new LogoutScreen(this);
        gameScreen = new GameScreen(this);
        recordScreen = new RecordScreen(this);
        resultScreen =new ResultScreen(this);


        //WebSocketEndpointクラスにコントローラーを同期させる
        WebSocketEndpoint webSoketEndpoint = new WebSocketEndpoint();
        webSoketEndpoint.synchroController(this);

        cardPanel.add(titleScreen, "title");
        cardPanel.add(lobbyScreen, "lobby");
        cardPanel.add(matchingWaitScreen, "matchWait");
        cardPanel.add(ruleScreen, "rule");
        cardPanel.add(loginScreen, "login");
        cardPanel.add(signinScreen, "signin");
        cardPanel.add(logoutScreen, "logout");
        cardPanel.add(gameScreen,"game");
        cardPanel.add(resultScreen,"result");
        cardPanel.add(recordScreen,"record");
        this.add(cardPanel);

        // 初期画面をTitleScreenに設定
        screenTransition("title");
        pack();
    }

    public void screenTransition(String panelName) {
        //デバック
        System.out.println("画面遷移します");
        System.out.print("遷移する画面: ");
        System.out.println(panelName);

        cardLayout.show(cardPanel, panelName);
    }

    public void displayError(String errorMessage) {
        switch (errorMessage) {

            case "会員登録に失敗しました。":
                signinScreen.displayError(3);
                break;
            case "ログインに失敗しました。":
                loginScreen.displayError(2);
            case "対戦相手がいなくなりました。":
                gameScreen.communicationCutoff();
            case "制限時間が経過しました。":
                gameScreen.timeOut(); //処理は後で追加
                break;
            default:


        }
    }

    public void login(String username, String password) {

        //デバック
        System.out.println("コントローラー到達:login");

        clientCommunication.login(username, password);

    }

    public void signin(String username, String password) {
        //デバック
        System.out.println("コントローラー到達:signin");

        clientCommunication.signin(username, password);
    }

    public void logout() {
        clientCommunication.logout();
        screenTransition("title");
    }

    public void getRecord() {
        System.out.println("コントローラー到達:getRecord");
        /*clientCommunicationに戦績を取得する要求を渡す*/
        clientCommunication.showRecord();
    }

    public void displayRecord(int rate, int winCount, int loseCount, int drawCount) {
        System.out.println("コントローラー到達:displayRecord");
        //RecordScreenのrate,win,lose,drawを更新
        recordScreen.UpdateRecord(rate,winCount,loseCount,drawCount);
        screenTransition("record");
    }

    public void displayResult(String username1,String username2,String winUser,int result){
        System.out.println("コントローラ到達:displayResult");
        //ResultScreenにユーザ情報を渡す
        resultScreen.displayResult(username1,username2,winUser,result);
        screenTransition("result");
    }

    public void match() {

        //デバック
        System.out.println("コントローラー到達:match");

        screenTransition("matchWait");

        /*clientCommunicationにマッチング要求を渡す処理を追加*/
        clientCommunication.addUser();
    }

    public void cancelMatching() {

        //デバック
        System.out.println("コントローラー到達:cancelMatching");

        /*clientCommunicationにマッチングキャンセル要求を渡す処理を追加*/
        clientCommunication.cancel();

        screenTransition("lobby");

    }

    public void set(String setNumber){
        clientCommunication.checkNumber(setNumber);
    }

    public void call(String callNumber){
        clientCommunication.callCheck(callNumber);
    }


    //HIGHANDLOW,SLASH使用時はnumberをどうするか？
    //GameScreenのpushYesButtonメソッド内で使われているUseItemメソッドの引数が
    // ItemだけになっているがTarget使用時の指定したナンバーはどうするか?
    //アイテム名を頭文字だけ大文字か全部大文字か統一するべきですか？
    //GameScreenは全部大文字、clientCommunicationは頭文字だけ大文字になっています。
    public void useItem(String item,int number){
        if(item.equals("HighAndLow")){
            clientCommunication.selectItem(item,10);
        }
        else if(item.equals("Slash")){
            clientCommunication.selectItem(item,10);
        }
        else if(item.equals("Target")){
            clientCommunication.selectItem(item,number);
        }
    }



    public void displayCallResult(String callNumber,int eat, int bite,boolean mine){
        gameScreen.displayCallResult(callNumber, Integer.toString(eat), Integer.toString(bite),mine);
    }

    public void displayItemResult(String item, String result,boolean mine){
        gameScreen.displayItemResult(item,result,mine);
    }

    // 先攻後攻を取得するメソッドを追加
    public void getOrder(boolean attackFirst){
        gameScreen.initialization();
        if(attackFirst){
            // 先攻
            gameScreen.attackFirst();
        }
        else{
            // 後攻
            gameScreen.attackLast();
        }
        screenTransition("game");
    }

    // 設定ナンバーの入力時
    public void opponentSetComplete(){
        gameScreen.opponentSetComplete();
    }

    // 相手のターンから自分のターンに切り替えるためのメソッドを追加
    public void changeTurn(){
        gameScreen.changeTurn();
    }


}

