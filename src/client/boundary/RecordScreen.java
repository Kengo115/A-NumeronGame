package client.boundary;


import client.controller.Controller;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RecordScreen extends JPanel implements ActionListener {
    JButton returnButton;
    private Image backgroundImage;
    Controller controller;
    public int rate;
    public int winCount;
    public int loseCount;
    public int drawCount;
    //勝敗表示するラベル
    private JLabel recordLabel;



    public RecordScreen(Controller controller){
        super();
        this.controller=controller;
        try{
            this.setLayout(null);

            returnButton=new JButton("<-戻る");
            returnButton.addActionListener(this);
            Font font1=new Font("SansSerif",Font.BOLD,12);
            returnButton.setFont(font1);
            returnButton.setContentAreaFilled(false);
            try{
                UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
            }catch(Exception e){
                e.printStackTrace();
            }
            returnButton.setForeground(Color.WHITE);
            returnButton.setBounds(800,500,80,50);
            this.add(returnButton);

            recordLabel = new JLabel("");
            recordLabel.setFont(font1);
            recordLabel.setBounds(700, 60, 250, 50);
            this.add(recordLabel);


            String imagePath ="Numeron-Record.png";
            backgroundImage = new ImageIcon(getClass().getResource(imagePath)).getImage();
        }catch(Exception ex){
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

    public void pushReturnButton(){
        controller.screenTransition("lobby");
    }

    public void UpdateRecord(int rate,int winCount,int loseCount,int drawCount){
         this.rate=rate;
         this.winCount=winCount;
         this.loseCount=loseCount;
         this.drawCount=drawCount;

         recordLabel.setText("Wins: " + winCount + "  Losses: " + loseCount + "  Draws: " + drawCount + "  Rate: " + rate);
    }


    public void actionPerformed(ActionEvent ae){
        if(ae.getSource()==returnButton){
            pushReturnButton();
        }
    }
}
