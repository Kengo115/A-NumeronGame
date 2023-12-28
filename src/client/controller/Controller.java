package client.controller;

import java.awt.CardLayout;
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JPanel;

import client.boundary.LobbyScreen;
import client.boundary.LoginScreen;
import client.boundary.LogoutScreen;
import client.boundary.MatchingWaitScreen;
import client.boundary.RuleScreen;
import client.boundary.SigninScreen;
import client.boundary.TitleScreen;
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
   // private RecordScreen recordScreen;

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
        //recordScreen = new RecordScreen(this);

        
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
       // cardPanel.add(recordScreen,"record");
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
        /*recordScreen.updataRecord(rate,win,lose,draw);
        screenTransition("record");*/
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

}

