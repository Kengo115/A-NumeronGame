package client.boundary;

import java.awt.Dimension;
import javax.swing.JFrame;

/** 画面がしっかり表示しているか確認するクラス 
 * 実際は不要
 * それぞれの画面クラスでmain文を実行しても良い
 * */
public class FrameExample extends JFrame{
	
	/** 必要に応じてこのフィールドを作成した画面クラスに変更する */
	private TitleScreen titlePanel = null;
	//private SigninScreen signinPanel = null;
	//private LoginScreen loginPanel = null;
	//private LobbyScreen lobbyPanel = null;
	//private RuleScreen rulePanel = null;
	//private MatchingWaitScreen waitPanel = null;
	public static void main(String[] args) {
		// TODO 自動生成されたメソッド・スタブ
		new FrameExample(); 
	}
	
	public FrameExample() {
		super();
		// パネルサイズを決める
        super.setPreferredSize(new Dimension(1000, 600));
		// ×ボタンが押されたら、終了する
        super.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        /* 必要に応じて作成した画面クラスに変更 */
        titlePanel = new TitleScreen();
		//signinPanel = new SigninScreen();
		//loginPanel = new LoginScreen();
        //lobbyPanel = new LobbyScreen();
        //rulePanel = new RuleScreen();
        //waitPanel = new MatchingWaitScreen();
        // フレームのコンテントペインを置き換える
        super.setContentPane(titlePanel);
		//super.setContentPane(signinPanel);
		//super.setContentPane(loginPanel);
        //super.setContentPane(lobbyPanel);
        //super.setContentPane(rulePanel);
		//super.setContentPane(waitPanel);
        // フレームを表示
        super.setVisible(true);
        // サイズを最適化する
        super.pack();
    }

}
