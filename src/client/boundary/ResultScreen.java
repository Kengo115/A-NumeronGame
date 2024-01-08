package client.boundary;

import client.controller.Controller;
import applicationServer.controller.GameController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class ResultScreen extends JPanel implements ActionListener{
    JButton returnButton;
    Controller controller;
    GameController gamecontroller;
    private Image backgroundImage;

    public String username1;
    public String username2;
    public String winUser;

    //勝者を表示するラベル
    private JLabel winLabel;


    //コンストラクタ
    public ResultScreen(Controller controller){
        super();
        this.controller=controller;
        this.gamecontroller= gamecontroller;
        try{
            this.setLayout(null);

            returnButton =new JButton("<-ロビーへ戻る");
            returnButton.addActionListener(this);//リスナーにこのクラスを登録
            Font font1 =new Font("SansSerif",Font.BOLD,20);
            returnButton.setFont(font1);
            returnButton.setOpaque(true);
            returnButton.setForeground(Color.CYAN);
            returnButton.setForeground(Color.WHITE);
            try{
                UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
            }catch(Exception e){
                e.printStackTrace();
            }
            //JButtonの座標とサイズを指定
            returnButton.setBounds(430,500,120,60);
            this.add(returnButton);

            //winLabelの設定
            winLabel =new JLabel("");
            winLabel.setFont(font1);
            winLabel.setBounds(430, 470, 120, 30);
            this.add(winLabel);

            //isWinnerが１であれば先行が勝ち、２で後攻が勝ち、３であれば引き分け
            //turnが0であれば先行、１であれば後攻
            if(gamecontroller.getTurn()==0){
                if (gamecontroller.getIsWinner() == 1) {
                    String imagePath = "Numeron-Reselt-Win.png";
                    backgroundImage = new ImageIcon(getClass().getResource(imagePath)).getImage();
                } else if (gamecontroller.getIsWinner() == 2) {
                    String imagePath = "Numeron-Reselt-Lose.png";
                    backgroundImage = new ImageIcon(getClass().getResource(imagePath)).getImage();
                } else {
                    String imagePath = "Numeron-Reselt-Draw.png";
                    backgroundImage = new ImageIcon(getClass().getResource(imagePath)).getImage();
                }
            }else {
                if (gamecontroller.getIsWinner() == 1) {
                    String imagePath = "Numeron-Reselt-Lose.png";
                    backgroundImage = new ImageIcon(getClass().getResource(imagePath)).getImage();
                } else if (gamecontroller.getIsWinner() == 2) {
                    String imagePath = "Numeron-Reselt-Win.png";
                    backgroundImage = new ImageIcon(getClass().getResource(imagePath)).getImage();
                } else {
                    String imagePath = "Numeron-Reselt-Draw.png";
                    backgroundImage = new ImageIcon(getClass().getResource(imagePath)).getImage();
                }
            }
        }catch(Exception ex){
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this,"ERROR : " + ex.toString());
        }
    }
    @Override
    protected void paintComponent(Graphics g){
        super.paintComponent(g);
        //背景画像を描画
        g.drawImage(backgroundImage,0,0,getWidth(),getHeight(),this);
    }

    public void pushReturnButton(){
        controller.screenTransition("lobby");
    }

    public void displayResult(String username1,String username2,String winUser){
        this.username1=username1;
        this.username2=username2;
        this.winUser=winUser;

        winLabel.setText("Win: " + winUser);
    }

    public void actionPerformed(ActionEvent ae){
        if(ae.getSource()==returnButton){
            pushReturnButton();
        }
    }

}