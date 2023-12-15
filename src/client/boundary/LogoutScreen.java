package client.boundary;

import client.controller.Controller;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LogoutScreen extends JPanel implements ActionListener{
    /* okボタン*/
    JButton okButton;
    /* キャンセルボタン*/
    JButton cancelButton;

    private Image backgroundImage;
    Controller controller = new Controller();
    //コンストラクタ
    public LogoutScreen() {
        super();
        try {
            this.setLayout(null);

            okButton = new JButton("ok");
            okButton.addActionListener(this); //リスナーをこのクラスに登録(実際はバウンダリコントローラに登録?)
            Font font1 = new Font("Arial", Font.BOLD, 20); //ボタンの中の文字のフォント
            okButton.setFont(font1);
            // 背景色を青色に設定
            okButton.setOpaque(true); // ボタンの透明度を有効にする
            okButton.setBackground(Color.BLUE); //ボタンの背景色を青色にする
            try {
                UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
            } catch (Exception e) {
                e.printStackTrace();
            }
            okButton.setForeground(Color.WHITE);
            //JButtonの座標とサイズを指定
            okButton.setBounds(820,350,120,40);
            this.add(okButton);

            cancelButton = new JButton("キャンセル");
            cancelButton.addActionListener(this);
            Font font2 = new Font("SansSerif", Font.BOLD, 15);
            cancelButton.setFont(font2);
            cancelButton.setBackground(Color.RED);
            cancelButton.setForeground(Color.WHITE);
            cancelButton.setBounds(660,350,120,40);
            this.add(cancelButton);

            String imagePath = "Numeron-Logout.png";
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
    public void pushOkButton() {
        //ここにokボタンが押された後の処理を書く
        controller.logout();
    }
    public void pushCancelButton() {
        //ここにキャンセルが押された後の処理を書く
        controller.screenTransition("lobby");
    }

    public void actionPerformed(ActionEvent ae) {
        if(ae.getSource() == okButton) {
            pushOkButton();
        }else if(ae.getSource() == cancelButton) {
            pushCancelButton();
        }
    }

}
