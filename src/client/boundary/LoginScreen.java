package client.boundary;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.UIManager;

import client.controller.Controller;

public class LoginScreen extends JPanel implements ActionListener{
    /** ログインボタン */
    JButton loginButton;
    /** 戻るボタン */
    JButton backButton;
    /** ユーザ名入力欄用ラベル */
    JLabel userNameLabel;
    /** パスワード入力欄用ラベル */
    JLabel passwordLabel;
    /** エラーメッセージラベル  */
    JLabel errorLabel = new JLabel();
    /** ユーザ名入力欄 */
    JTextField userNameField;
    /** パスワード入力欄 */
    JPasswordField passwordField;
    /** 背景画像 */
    private Image backgroundImage;
    /** コントローラ */
    Controller controller;
    /** コンポーネント */
    Component[] components;

    //コンストラクタ
    public LoginScreen(Controller controller) {
        super();
        this.controller = controller;
        try {
            this.setLayout(null);

            loginButton = new JButton("ログイン->");
            loginButton.addActionListener(this); //リスナーをこのクラスに登録(実際はバウンダリコントローラに登録?)
            Font font1 = new Font("SansSerif", Font.BOLD, 12); //ボタンの中の文字のフォント
            loginButton.setFont(font1);
            loginButton.setContentAreaFilled(false); // ボタンの透明度を有効にする
            try {
                UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
            } catch (Exception e) {
                e.printStackTrace();
            }
            loginButton.setForeground(Color.WHITE);
            //JButtonの座標とサイズを指定
            loginButton.setBounds(870,450,95,50);
            this.add(loginButton);

            backButton = new JButton("<-戻る");
            backButton.addActionListener(this);
            backButton.setFont(font1);
            backButton.setContentAreaFilled(false);
            backButton.setForeground(Color.WHITE);
            backButton.setBounds(700,450,80,50);
            this.add(backButton);

            // ユーザ名入力欄の作成
            userNameLabel = new JLabel("ユーザ名（ID）");
            userNameLabel.setForeground(Color.WHITE);
            userNameLabel.setBounds(700, 100, 250,40);
            userNameField = new JTextField(1); // 1行のテキストフィールド
            userNameField.setFont(new Font("SansSerif",Font.BOLD, 16));
            userNameField.setForeground(Color.WHITE);  // 白文字
            userNameField.setBackground(Color.WHITE);  // 白枠
            userNameField.setCaretColor(Color.WHITE);  // キャレット白
            userNameField.setOpaque(false);    //背景透明
            userNameField.setBounds(700, 140, 250, 40);    // 配置座標
            userNameField.setMargin(new Insets(0,5,0,0));  //左に余白
            this.add(userNameLabel);
            this.add(userNameField);

            // パスワード入力欄の作成
            passwordLabel = new JLabel("パスワード");
            passwordLabel.setForeground(Color.WHITE);
            passwordLabel.setBounds(700, 220, 250,40);
            passwordField = new JPasswordField(1);
            passwordField.setFont(new Font("SansSerif",Font.BOLD, 16));
            passwordField.setForeground(Color.WHITE);
            passwordField.setBackground(Color.WHITE);
            passwordField.setCaretColor(Color.WHITE);
            passwordField.setOpaque(false);
            passwordField.setBounds(700, 260, 250, 40);
            passwordField.setMargin(new Insets(0,5,0,0));
            this.add(passwordLabel);
            this.add(passwordField);

            String imagePath = "Numeron.png";
            backgroundImage = new ImageIcon(getClass().getResource(imagePath)).getImage();

            /** 画面サイズが変更されたときに呼ばれるイベントリスナー
             * ここ書く予定
             */

        }catch(Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "ERROR : " + ex.toString());
        }

    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        // 背景画像を描画
        g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
    }

    public void displayError(int type){

        switch (type){
            case 1:
                errorLabel.setText("全て入力してください");
                break;
            case 2:
                errorLabel.setText("ユーザ名またはパスワードが違います");
                break;
        }
        errorLabel.setForeground(Color.RED);
        errorLabel.setBounds(700,310,280,40);


        // エラーメッセージラベルが表示されているか確認
        this.components = this.getComponents();
        for(Component component : components){
            if(component != errorLabel){
                this.add(errorLabel);    // エラーメッセージを追加
            }
        }
        repaint();  // 画面を更新
        System.out.println("ログイン失敗");
    }

    public void pushLoginButton(){
        // ログインボタンが押された後の処理を書く
        String username = userNameField.getText();
        String password = new String(passwordField.getPassword());

        if(username.equals("") || password.equals("")){
            // 空欄がある場合エラーを表示
            this.displayError(1);
        }
        else{
        	//ログイン処理を行う
        	controller.login(username, password);
        }
    }

    public void pushBackButton(){
        // 戻るボタンが押された後の処理を書く

        // エラーメッセージラベルが表示されているか確認
        this.components = this.getComponents();
        for(Component component : components){
            if(component == errorLabel){
                this.remove(errorLabel);    // エラーメッセージを削除
            }
        }

        userNameField.setText(null);    // 入力をクリア
        passwordField.setText(null);
        controller.screenTransition("title");
    }

    public void actionPerformed(ActionEvent ae) {
        if(ae.getSource() == loginButton) {
            pushLoginButton();
            System.out.println("ログインボタンが押されました");
        }else if(ae.getSource() == backButton) {
            pushBackButton();
            System.out.println("戻るボタンが押されました");
        }
    }

}

