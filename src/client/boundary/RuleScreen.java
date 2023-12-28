package client.boundary;

import client.controller.Controller;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RuleScreen extends JPanel implements ActionListener{
	/** 戻るボタン*/
	JButton returnButton;
	Controller controller;
	private Image backgroundImage;


	//コンストラクタ
	public RuleScreen(Controller controller) {
		super();
		this.controller = controller;
		try {
			this.setLayout(null);

            returnButton = new JButton("<戻る");
            returnButton.addActionListener(this); //リスナーをこのクラスに登録(実際はバウンダリコントローラに登録?)
            Font font1 = new Font("SansSerif", Font.BOLD, 20); //ボタンの中の文字のフォント
            returnButton.setFont(font1);
            // 背景色を水色に設定
            returnButton.setOpaque(true); // ボタンの透明度を有効にする
            returnButton.setBackground(Color.CYAN); //ボタンの背景色を水色にする(余裕があればこだわる)
            returnButton.setForeground(Color.WHITE);
            try {
                UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
            } catch (Exception e) {
                e.printStackTrace();
            }
            //JButtonの座標とサイズを指定
            returnButton.setBounds(430,500,100,50);
			this.add(returnButton);
			String imagePath = "Numeron-Rule.png";
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
	public void pushReturnButton() {
		/**ここに戻るボタンが押された後の処理を書く
		 * 本来は画面が遷移する
		 * バウンダリコントローラから指示?
		 */
		controller.screenTransition("lobby");
	}
	public void actionPerformed(ActionEvent ae) {
		if(ae.getSource() == returnButton) {
			pushReturnButton();
		}
	}
	
}
