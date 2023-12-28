package client.boundary;

import client.controller.Controller;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
//import java.awt.event.ComponentAdapter; 今度
//import java.awt.event.ComponentEvent;　使う
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.UIManager;

public class TitleScreen extends JPanel implements ActionListener{
	/** ログインボタン*/
	JButton loginButton;
	/** 新規作成の方はこちらボタン*/
	JButton signinButton;
	private Image backgroundImage;

	Controller controller;
	//コンストラクタ
	public TitleScreen(Controller controller) {
		super();
		this.controller = controller;
		try {
			this.setLayout(null);

			loginButton = new JButton("ログイン");
			loginButton.addActionListener(this); //リスナーをこのクラスに登録(実際はバウンダリコントローラに登録?)
			Font font1 = new Font("SansSerif", Font.BOLD, 35); //ボタンの中の文字のフォント
			loginButton.setFont(font1);
			// 背景色を水色に設定
			loginButton.setOpaque(true); // ボタンの透明度を有効にする
			loginButton.setBackground(Color.CYAN); //ボタンの背景色を水色にする(余裕があればこだわる)
			try {
				UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
			} catch (Exception e) {
				e.printStackTrace();
			}
			//JButtonの座標とサイズを指定
			loginButton.setBounds(700,60,250,100);
			this.add(loginButton); //背景画像パネルに追加

			signinButton = new JButton("新規作成の方はこちら");
			signinButton.addActionListener(this);
			// ボタンの背景色を透明に設定
			signinButton.setBackground(new Color(0, 0, 0, 0));
			// ボーダーの設定を透明にする
			signinButton.setBorderPainted(false);
			Font font2 = new Font("SansSerif", Font.BOLD, 15);
			signinButton.setFont(font2);
			signinButton.setForeground(Color.ORANGE);
			signinButton.setBounds(650,180,250,30);
			this.add(signinButton);
			String imagePath = "Numeron.png";
			backgroundImage = new ImageIcon(getClass().getResource(imagePath)).getImage();
		}
		catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(this, "ERROR : " + e.toString());
		}

	}
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		// 背景画像を描画
		g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
	}
	public void pushLoginButton() {
		/**ここにログインボタンが押された後の処理を書く
		 * 本来は画面が遷移する
		 * バウンダリコントローラから指示?
		 */
		controller.screenTransition("login");
	}

	public void pushSigninButton() {
		//ここに新規登録ボタンが押された後の処理を書く
		controller.screenTransition("signin");
	}

	public void actionPerformed(ActionEvent ae) {
		if(ae.getSource() == loginButton) {
			pushLoginButton();
		}else if(ae.getSource() == signinButton) {
			pushSigninButton();
		}
	}

}
