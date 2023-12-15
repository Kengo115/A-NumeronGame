package client.boundary;

import client.controller.Controller;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
    /** ユーザ名入力欄 */
    JTextField userNameField;
    /** パスワード入力欄 */
    JPasswordField passwordField1;
    /** パスワード（確認）入力欄 */
    JPasswordField passwordField2;
    /** 背景画像 */
    private Image backgroundImage;
    /** コントローラ */
    Controller controller = new Controller();

    //コンストラクタ
    public SigninScreen() {
        super();
        try {
            this.setLayout(null);

            signinButton = new JButton("作成->");
            signinButton.addActionListener(this); //リスナーをこのクラスに登録(実際はバウンダリコントローラに登録?)
            Font font1 = new Font("Arial", Font.BOLD, 12); //ボタンの中の文字のフォント
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
            userNameLabel.setBounds(700, 100, 250,40);
            userNameField = new JTextField(1); // 1行のテキストフィールド
            userNameField.setFont(new Font("Arial",Font.BOLD, 16));
            userNameField.setForeground(Color.WHITE);  // 白文字
            userNameField.setBackground(Color.WHITE);  // 白枠
            userNameField.setCaretColor(Color.WHITE);  // キャレット白
            userNameField.setOpaque(false);    //背景透明
            userNameField.setBounds(700, 140, 250, 40);    // 配置座標
            userNameField.setMargin(new Insets(0,5,0,0));  //左に余白
            this.add(userNameField);

            // パスワード入力欄の作成
            passwordLabel1 = new JLabel("パスワード");
            passwordLabel1.setForeground(Color.WHITE);
            passwordLabel1.setBounds(700, 220, 250,40);
            passwordField1 = new JPasswordField(1);
            passwordField1.setFont(new Font("Arial",Font.BOLD, 16));
            passwordField1.setForeground(Color.WHITE);
            passwordField1.setBackground(Color.WHITE);
            passwordField1.setCaretColor(Color.WHITE);
            passwordField1.setOpaque(false);
            passwordField1.setBounds(700, 260, 250, 40);
            passwordField1.setMargin(new Insets(0,5,0,0));
            this.add(passwordField1);

            // パスワード（確認）入力欄の作成
            passwordLabel2 = new JLabel("パスワード（確認）");
            passwordLabel2.setForeground(Color.WHITE);
            passwordLabel2.setBounds(700,310,280,40);
            passwordField2 = new JPasswordField(1);
            passwordField2.setFont(new Font("Arial",Font.BOLD, 16));
            passwordField2.setForeground(Color.WHITE);
            passwordField2.setBackground(Color.WHITE);
            passwordField2.setCaretColor(Color.WHITE);
            passwordField2.setOpaque(false);
            passwordField2.setBounds(700, 350, 250, 40);
            passwordField2.setMargin(new Insets(0,5,0,0));
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

    public void displayError(){
        System.out.println("新規登録失敗");
    }

    public void pushSigninButton(){
        // 作成ボタンが押された後の処理を書く
        String username = userNameField.getText();
        String password = passwordField1.getText();

        // IDとパスワードの認証処理を書く
        if(controller.signin(username,password)){
            // サインインに成功したのでロビー画面に遷移
            controller.screenTransition("lobby");
        }else{
            // サインインに失敗したのでエラー画面を表示
            this.displayError();
        }
    }

    public void pushBackButton(){
        // 戻るボタンが押された後の処理を書く
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

