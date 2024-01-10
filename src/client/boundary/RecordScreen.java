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
    private JLabel winLabel;
    private JLabel loseLabel;
    private JLabel drawLabel;
    private JLabel rateLabel;



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

            winLabel = new JLabel("");
            Font font2=new Font("SansSerif",Font.BOLD,20);
            winLabel.setFont(font2);
            winLabel.setForeground(Color.WHITE);
            winLabel.setBounds(700, 60, 250, 70);
            this.add(winLabel);

            loseLabel = new JLabel("");
            loseLabel.setFont(font2);
            loseLabel.setForeground(Color.WHITE);
            loseLabel.setBounds(700, 130, 250, 70);
            this.add(loseLabel);

            drawLabel = new JLabel("");
            drawLabel.setFont(font2);
            drawLabel.setForeground(Color.WHITE);
            drawLabel.setBounds(700, 200, 250, 70);
            this.add(drawLabel);

            rateLabel = new JLabel("");
            rateLabel.setFont(font2);
            rateLabel.setForeground(Color.WHITE);
            rateLabel.setBounds(700, 270, 250, 70);
            this.add(rateLabel);


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

        winLabel.setText("Wins: " + winCount);
        loseLabel.setText("Loses:" + loseCount);
        drawLabel.setText("Draws:" + drawCount);
        rateLabel.setText("Rate:" + rate);
    }


    public void actionPerformed(ActionEvent ae){
        if(ae.getSource()==returnButton){
            pushReturnButton();
        }
    }

}
