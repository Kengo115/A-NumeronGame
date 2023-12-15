package client.controller;

import java.awt.CardLayout;
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import client.boundary.LobbyScreen;
import client.boundary.LoginScreen;
import client.boundary.LogoutScreen;
import client.boundary.MatchingWaitScreen;
import client.boundary.RuleScreen;
import client.boundary.SigninScreen;
import client.boundary.TitleScreen;
import client.communication.ClientCommunication;

public class Controller extends JFrame {

    private JFrame frame;
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

    public static void main(String[] args) {
        Controller controller = new Controller();
        controller.setTitle("Numeron");
        controller.setLocationRelativeTo(null);
        controller.setVisible(true);
    }

    public Controller() {
        frame = new JFrame("Numeron");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setPreferredSize(new Dimension(1000, 600));

        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);

        titleScreen = new TitleScreen();
        lobbyScreen = new LobbyScreen();
        matchingWaitScreen = new MatchingWaitScreen();
        ruleScreen = new RuleScreen();
        loginScreen = new LoginScreen();
        signinScreen = new SigninScreen();
        logoutScreen = new LogoutScreen();

        cardPanel.add(titleScreen, "title");
        cardPanel.add(lobbyScreen, "lobby");
        cardPanel.add(matchingWaitScreen, "matchWait");
        cardPanel.add(ruleScreen, "rule");
        cardPanel.add(loginScreen, "login");
        cardPanel.add(signinScreen, "signin");
        cardPanel.add(logoutScreen, "logout");
        frame.add(cardPanel);

        // 初期画面をTitleScreenに設定
        screenTransition("title");

        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public void screenTransition(String panelName) {
        cardLayout.show(cardPanel, panelName);
    }

    public void displayError(String errorMessage) {
        JOptionPane.showMessageDialog(frame, errorMessage, "Error", JOptionPane.ERROR_MESSAGE);
    }

    public boolean login(String username, String password) {
        clientCommunication.login(username, password);
        return true;    // 仮
    }

    public boolean signin(String username, String password) {
        clientCommunication.signin(username, password);
        return true;    // 仮
    }

    public void logout() {
        clientCommunication.logout();
        screenTransition("title");
    }
}
