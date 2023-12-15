package client.boundary;

import client.controller.Controller;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LobbyScreen extends JPanel implements ActionListener{
	/** ユーザ切り替え/ログアウトボタン */
	JButton logoutButton;
	/** ルール説明ボタン*/
	JButton ruleButton;
	/** オンラインマッチングボタン */
	JButton matchingButton;
	/** ユーザ情報ボタン */
	JButton rewardButton;
	private Image backgroundImage;
	Controller controller = new Controller();
	//コンストラクタ
	public LobbyScreen() {
		super();
		try {
			this.setLayout(null);

            logoutButton = new JButton("ユーザ切り替え/ログアウト");
            logoutButton.addActionListener(this); //リスナーをこのクラスに登録(実際はバウンダリコントローラに登録?)
            Font font1 = new Font("Arial", Font.BOLD, 12); //ボタンの中の文字のフォント
            logoutButton.setFont(font1);
            // 背景色を水色に設定
            logoutButton.setOpaque(true); // ボタンの透明度を有効にする
            logoutButton.setBackground(Color.RED); //ボタンの背景色を水色にする(余裕があればこだわる)
            try {
                UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
            } catch (Exception e) {
                e.printStackTrace();
            }
            logoutButton.setForeground(Color.WHITE);
            //JButtonの座標とサイズを指定
            logoutButton.setBounds(3,5,250,20);
			this.add(logoutButton);

            ruleButton = new JButton("ルール説明");
            ruleButton.addActionListener(this);
            Font font2 = new Font("Arial", Font.BOLD, 12);
            ruleButton.setFont(font2);
            Color yellowGreenColor = new Color(154, 205, 50);
            ruleButton.setBackground(yellowGreenColor);
            ruleButton.setBounds(780,5,200,20);
			this.add(ruleButton);
            
            matchingButton = new JButton("オンラインマッチング");
            matchingButton.addActionListener(this);
            Font font3 = new Font("Arial", Font.BOLD, 20);
            matchingButton.setFont(font3);
            matchingButton.setBackground(Color.CYAN);
            matchingButton.setBounds(700,100,250,70);
            this.add(matchingButton);
            
            rewardButton = new JButton("ユーザ情報");
            rewardButton.addActionListener(this);
            Font font4 = new Font("Arial", Font.BOLD, 25);
            rewardButton.setFont(font4);
            // ボタンの背景色を肌色に設定
            Color skinColor = new Color(255, 218, 185);
            rewardButton.setBackground(skinColor);
            rewardButton.setBounds(700,190,250,70);
            this.add(rewardButton);
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

	public void pushLogoutButton() {
		//ここにユーザ切り替え/ログアウトボタンが押された後の処理を書く
		controller.screenTransition("logout");
	}
	public void pushRuleButton() {
		//ここにルール説明ボタンが押された後の処理を書く
		controller.screenTransition("rule");
	}
	public void pushMatchingButton() {
		//ここにマッチングボタンが押された後の処理を書く
		controller.screenTransition("matchWait");
	}
	public void pushRewardButton() {
		//ここにユーザ情報ボタンが押された後の処理を書く
		controller.screenTransition("reward");
	}
	
	public void actionPerformed(ActionEvent ae) {
		if(ae.getSource() == logoutButton) {
			pushLogoutButton();
		}else if(ae.getSource() == ruleButton) {
			pushRuleButton();
		}else if(ae.getSource() == matchingButton) {
			pushMatchingButton();
		}else if(ae.getSource() == rewardButton) {
			pushRewardButton();
		}
	}
	
}
