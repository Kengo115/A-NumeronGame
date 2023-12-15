package client.boundary;

import client.controller.Controller;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MatchingWaitScreen extends JPanel implements ActionListener{
	/** 戻るボタン*/
	JButton cancelButton;
	private Image backgroundImage;
	Controller controller = new Controller();
	//コンストラクタ
	public MatchingWaitScreen() {
		super();
		try {
			this.setLayout(null);
            cancelButton = new JButton("キャンセル");
            cancelButton.addActionListener(this); //リスナーをこのクラスに登録(実際はバウンダリコントローラに登録?)
            Font font1 = new Font("Arial", Font.BOLD, 20); //ボタンの中の文字のフォント
            cancelButton.setFont(font1);
            // 背景色を水色に設定
            cancelButton.setOpaque(true); // ボタンの透明度を有効にする
            cancelButton.setBackground(Color.RED); //ボタンの背景色を水色にする(余裕があればこだわる)
            cancelButton.setForeground(Color.WHITE);
            try {
                UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
            } catch (Exception e) {
                e.printStackTrace();
            }
            //JButtonの座標とサイズを指定
            cancelButton.setBounds(660,490,140,30);
			this.add(cancelButton);
            
            /** 画面サイズが変更されたときに呼ばれるイベントリスナー
             * ここ書く予定
             */
			String imagePath = "Matching.png";
			backgroundImage = new ImageIcon(getClass().getResource(imagePath)).getImage();
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
	public void pushCancelButton() {
		/**ここにキャンセルボタンが押された後の処理を書く
		 * 本来は画面が遷移する
		 * バウンダリコントローラから指示?
		 */
		controller.screenTransition("lobby");
	}
	public void actionPerformed(ActionEvent ae) {
		if(ae.getSource() == cancelButton) {
			pushCancelButton();
		}
	}

	
}
