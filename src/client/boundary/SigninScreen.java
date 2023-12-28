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

public class SigninScreen extends JPanel implements ActionListener{
    /** 作成ボタン */
    JButton signinButton;
    /** 戻るボタン */
    JButton backButton;
    /** ユーザ名入力欄用ラベル */
    JLabel userNameLabel;
    /** パスワード入力欄用ラベル */
    JLabel passwordLabel1;
    /** パスワード（確認）入力欄用ラベル */
    JLabel passwordLabel2;
    /** エラーメッセージラベル */
    JLabel errorLabel = new JLabel();
    /** ユーザ名入力欄 */
    JTextField userNameField;
    /** パスワード入力欄 */
    JPasswordField passwordField1;
    /** パスワード（確認）入力欄 */
    JPasswordField passwordField2;
    /** 背景画像 */
    private Image backgroundImage;
    /** コントローラ */
    Controller controller;
    /** コンポーネント */
    Component[] components;

    //コンストラクタ
    public SigninScreen(Controller controller) {
        super();
        this.controller = controller;
        try {
            this.setLayout(null);

            signinButton = new JButton("作成->");
            signinButton.addActionListener(this); //リスナーをこのクラスに登録(実際はバウンダリコントローラに登録?)
            Font font1 = new Font("SansSerif", Font.BOLD, 12); //ボタンの中の文字のフォント
            signinButton.setFont(font1);
            signinButton.setContentAreaFilled(false); // ボタンの透明度を有効にする
            try {
                UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
            } catch (Exception e) {
                e.printStackTrace();
            }
            signinButton.setForeground(Color.WHITE);
            //JButtonの座標とサイズを指定
            signinButton.setBounds(870,450,80,50);
            this.add(signinButton);

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
            userNameLabel.setBounds(700, 70, 250,40);
            userNameField = new JTextField(1); // 1行のテキストフィールド
            userNameField.setFont(new Font("SansSerif",Font.BOLD, 16));
            userNameField.setForeground(Color.WHITE);  // 白文字
            userNameField.setBackground(Color.WHITE);  // 白枠
            userNameField.setCaretColor(Color.WHITE);  // キャレット白
            userNameField.setOpaque(false);    //背景透明
            userNameField.setBounds(700, 110, 250, 40);    // 配置座標
            userNameField.setMargin(new Insets(0,5,0,0));  //左に余白
            this.add(userNameLabel);
            this.add(userNameField);

            // パスワード入力欄の作成
            passwordLabel1 = new JLabel("パスワード");
            passwordLabel1.setForeground(Color.WHITE);
            passwordLabel1.setBounds(700, 170, 250,40);
            passwordField1 = new JPasswordField(1);
            passwordField1.setFont(new Font("SansSerif",Font.BOLD, 16));
            passwordField1.setForeground(Color.WHITE);
            passwordField1.setBackground(Color.WHITE);
            passwordField1.setCaretColor(Color.WHITE);
            passwordField1.setOpaque(false);
            passwordField1.setBounds(700, 210, 250, 40);
            passwordField1.setMargin(new Insets(0,5,0,0));
            this.add(passwordLabel1);
            this.add(passwordField1);

            // パスワード（確認）入力欄の作成
            passwordLabel2 = new JLabel("パスワード（確認）");
            passwordLabel2.setForeground(Color.WHITE);
            passwordLabel2.setBounds(700,290,280,40);
            passwordField2 = new JPasswordField(1);
            passwordField2.setFont(new Font("SansSerif",Font.BOLD, 16));
            passwordField2.setForeground(Color.WHITE);
            passwordField2.setBackground(Color.WHITE);
            passwordField2.setCaretColor(Color.WHITE);
            passwordField2.setOpaque(false);
            passwordField2.setBounds(700, 330, 250, 40);
            passwordField2.setMargin(new Insets(0,5,0,0));
            this.add(passwordLabel2);
            this.add(passwordField2);

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
                errorLabel.setText("パスワードと確認用パスワードが一致しません");
                break;
            case 3:
                errorLabel.setText("そのユーザ名は既に使用されています");
                break;
        }
        errorLabel.setForeground(Color.RED);
        errorLabel.setBounds(700,380,280,40);

        this.components = this.getComponents();
        // エラーメッセージラベルが表示されているか確認
        for(Component component : components){
            if(component != errorLabel){
                this.add(errorLabel);    // エラーメッセージを追加
            }
        }
        repaint();  // 画面を更新
        System.out.println("新規登録失敗");
    }

    public void pushSigninButton(){
        // 作成ボタンが押された後の処理を書く
        String username = userNameField.getText();
        String password1 = new String(passwordField1.getPassword());
        String password2 = new String(passwordField2.getPassword());

        if(username.equals("") || password1.equals("") || password2.equals("")){
            // 空欄がある場合エラーを表示
            this.displayError(1);
        }
        else if(!password1.equals(password2)){
            // パスワードと確認用パスワードが一致しない場合エラーを表示
            this.displayError(2);
        }
        else {
        	controller.signin(username,password1);
        }

    }

    public void pushBackButton(){
        // 戻るボタンが押された後の処理を書く
        this.components = this.getComponents();
        // エラーメッセージラベルが表示されているか確認
        for(Component component : components){
            if(component == errorLabel){
                this.remove(errorLabel);    // エラーメッセージを削除
            }
        }

        userNameField.setText(null);    // 入力をクリア
        passwordField1.setText(null);
        passwordField2.setText(null);
        controller.screenTransition("title");
    }

    public void actionPerformed(ActionEvent ae) {
        if(ae.getSource() == signinButton) {
            pushSigninButton();
            System.out.println("作成ボタンが押されました");
        }else if(ae.getSource() == backButton) {
            pushBackButton();
            System.out.println("戻るボタンが押されました");
        }
    }
}

