package client.boundary;

import client.controller.Controller;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameScreen extends JPanel implements ActionListener {

    /** 画面の状態 */
    private int status = 1;
    /** ターンの管理 */
    private int turn = 1;
    /** 設定ナンバー */
    private String setNumber;
    /** CALLナンバー */
    private String callNumber;
    /** アイテムの使用可不可 */
    private boolean item = true;
    /** コントローラ */
    Controller controller;
    /** 確認ボタン */
    JButton checkButton = new JButton("確認");
    /** ポップアップラベル */
    JLabel popUpLabel = new JLabel("対戦開始！あなたは先攻です");
    /** サブポップアップラベル */
    JLabel subPopUpLabel = new JLabel("");
    /** 進行ラベル */
    JLabel progressionLabel = new JLabel("設定ナンバーを入力してください");
    /** ボタン背景 */
    JLabel backGroundLabel1 = new JLabel("");
    /** 入力数字背景 */
    JLabel backGroundLabel2 = new JLabel("");
    /** 入力数字表示フィールド */
    JTextField inputNumberField = new JTextField();
    /** 数字ボタン */
    JButton button0 = new JButton("0");
    JButton button1 = new JButton("1");
    JButton button2 = new JButton("2");
    JButton button3 = new JButton("3");
    JButton button4 = new JButton("4");
    JButton button5 = new JButton("5");
    JButton button6 = new JButton("6");
    JButton button7 = new JButton("7");
    JButton button8 = new JButton("8");
    JButton button9 = new JButton("9");
    /** クリアボタン */
    JButton clearButton = new JButton("クリア");
    /** 設定ボタン */
    JButton setButton = new JButton("設定");
    /** CALLボタン */
    JButton callButton = new JButton("CALL");
    /** CALLナンバーラベル */
    JLabel callNumberLabel = new JLabel("CALLナンバー");    // CALLナンバーの水色背景
    /** アイテムボタン */
    JButton highAndLowButton = new JButton("H");
    JButton slashButton = new JButton("S");
    JButton targetButton = new JButton("T");
    JLabel itemBackgroundLabel = new JLabel("");    //アイテムボタンの水色背景
    /** はいボタン */
    JButton yesButton = new JButton("はい");
    /** 戻るボタン */
    JButton backButton = new JButton("戻る");
    /** 表のコラム */
    private String[] columnNames = {"TURN", "NUMBER", "EAT", "BITE"};
    /** 表のデータ */
    private String[][] myTableData = {
            {"列1のデータ", "列2のデータ", "列3のデータ"},   /* 1行目 */
            {"列1のデータ", "列2のデータ", "列3のデータ"},   /* 2行目 */
            {"列1のデータ", "列2のデータ", "列3のデータ"},   /* 3行目 */
            {"列1のデータ", "列2のデータ", "列3のデータ"}    /* 4行目 */
    };

    DefaultTableModel myTableModel = new DefaultTableModel(myTableData,columnNames);
    /** 自分のCALL履歴表 */
    JTable myCallTable = new JTable(myTableModel);

    //JScrollPane myScrollPane = new JScrollPane(myCallTable);





    public GameScreen(Controller controller){
        super();
        this.controller = controller;

        try{
            this.setLayout(null);

            // フォント設定（サイズごと）
            Font font30 = new Font("Arial", Font.BOLD, 30);
            Font font20 = new Font("Arial", Font.BOLD, 20);
            Font font16 = new Font("Arial", Font.BOLD, 16);
            Font font10 = new Font("Arial", Font.BOLD, 10);

            // ボーダー設定
            LineBorder border1 = new LineBorder(Color.black,1,true);

            /** マッチング完了直後 */
            checkButton.addActionListener(this);
            checkButton.setFont(font20);
            checkButton.setBackground(new Color(245, 91, 96));    // 背景色設定
            //いるのかわかんないけどとりあえず
            try {
                UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
            } catch (Exception e) {
                e.printStackTrace();
            }
            checkButton.setBounds(450, 300, 100, 60);
            checkButton.setOpaque(true);

            popUpLabel.setFont(font20);
            popUpLabel.setBounds(300,150,400,220);
            popUpLabel.setHorizontalAlignment(JLabel.CENTER);
            popUpLabel.setOpaque(true);
            popUpLabel.setBackground(Color.WHITE);
            popUpLabel.setBorder(border1);

            subPopUpLabel.setFont(font16);
            subPopUpLabel.setBounds(350,200,200,100);
            subPopUpLabel.setHorizontalAlignment(JLabel.CENTER);

            /** 設定ナンバー入力 */
            progressionLabel.setFont(font16);
            progressionLabel.setOpaque(true);      // 不透明有効化
            progressionLabel.setForeground(Color.WHITE);
            progressionLabel.setBackground(new Color(31, 92, 220));
            progressionLabel.setBounds(0, 0, 1000, 40);
            progressionLabel.setHorizontalAlignment(JLabel.CENTER);

            button0.addActionListener(this);
            button0.setFont(font20);
            button0.setBackground(Color.WHITE);
            button0.setBounds(415, 450,50,50);

            button1.addActionListener(this);
            button1.setFont(font20);
            button1.setBackground(Color.WHITE);
            button1.setBounds(415, 390, 50,50);

            button2.addActionListener(this);
            button2.setFont(font20);
            button2.setBackground(Color.WHITE);
            button2.setBounds(475, 390, 50,50);

            button3.addActionListener(this);
            button3.setFont(font20);
            button3.setBackground(Color.WHITE);
            button3.setBounds(535, 390, 50,50);

            button4.addActionListener(this);
            button4.setFont(font20);
            button4.setBackground(Color.WHITE);
            button4.setBounds(415, 330, 50,50);

            button5.addActionListener(this);
            button5.setFont(font20);
            button5.setBackground(Color.WHITE);
            button5.setBounds(475, 330, 50,50);

            button6.addActionListener(this);
            button6.setFont(font20);
            button6.setBackground(Color.WHITE);
            button6.setBounds(535, 330, 50,50);

            button7.addActionListener(this);
            button7.setFont(font20);
            button7.setBackground(Color.WHITE);
            button7.setBounds(415, 270, 50,50);

            button8.addActionListener(this);
            button8.setFont(font20);
            button8.setBackground(Color.WHITE);
            button8.setBounds(475, 270, 50,50);

            button9.addActionListener(this);
            button9.setFont(font20);
            button9.setBackground(Color.WHITE);
            button9.setBounds(535, 270, 50,50);

            setButton.addActionListener(this);
            setButton.setFont(font20);
            setButton.setBackground(new Color(250, 244, 88));
            setButton.setBounds(475,450,110,50);

            callButton.addActionListener(this);
            callButton.setFont(font20);
            callButton.setBackground(new Color(250, 244, 88));
            callButton.setBounds(475,450,110,50);

            clearButton.addActionListener(this);
            clearButton.setFont(font10);
            clearButton.setBackground(new Color(183, 183, 180));
            clearButton.setBounds(570,110,70,30);

            // 数字ボタンの水色背景
            backGroundLabel1.setOpaque(true);
            backGroundLabel1.setBackground(new Color(101, 143, 232));
            backGroundLabel1.setBounds(405,260,190,250);
            backGroundLabel1.setBorder(border1);

            // 入力した数字の水色背景
            backGroundLabel2.setOpaque(true);
            backGroundLabel2.setBackground(new Color(101, 143, 232));
            backGroundLabel2.setBounds(350,90,300,60);
            backGroundLabel2.setBorder(border1);

            // 入力した数字のフィールド
            inputNumberField.setEditable(false);
            inputNumberField.setOpaque(true);
            inputNumberField.setBackground(Color.WHITE);
            inputNumberField.setBounds(370,100,180,40);
            inputNumberField.setFont(font20);
            inputNumberField.setHorizontalAlignment(JLabel.CENTER);

            /** 自分のターン */
            callNumberLabel.setFont(font16);
            callNumberLabel.setOpaque(true);
            callNumberLabel.setForeground(Color.WHITE);
            callNumberLabel.setBackground(new Color(31, 92, 220));
            callNumberLabel.setBounds(350,60,300,30);
            callNumberLabel.setHorizontalAlignment(JLabel.CENTER);
            callNumberLabel.setBorder(border1);

            highAndLowButton.addActionListener(this);
            highAndLowButton.setFont(font20);
            highAndLowButton.setBackground(Color.WHITE);
            highAndLowButton.setBounds(415,190,50,50);

            slashButton.addActionListener(this);
            slashButton.setFont(font20);
            slashButton.setBackground(Color.WHITE);
            slashButton.setBounds(475, 190, 50, 50);

            targetButton.addActionListener(this);
            targetButton.setFont(font20);
            targetButton.setBackground(Color.WHITE);
            targetButton.setBounds(535,190,50,50);

            // アイテムボタンの水色背景
            itemBackgroundLabel.setOpaque(true);
            itemBackgroundLabel.setBackground(new Color(101, 143, 232));
            itemBackgroundLabel.setBounds(405,180, 190, 70);
            itemBackgroundLabel.setBorder(border1);

            // テーブルのレイアウト
            myCallTable.setOpaque(true);
            myCallTable.setBackground(Color.BLACK);
            myCallTable.setFont(font16);
            myCallTable.setBounds(50,60,200,200);

            /** アイテム使用 */
            yesButton.addActionListener(this);
            yesButton.setFont(font20);
            yesButton.setBackground(new Color(101, 143, 232));
            yesButton.setBounds(550, 300, 100, 60);

            backButton.addActionListener(this);
            backButton.setFont(font20);
            backButton.setBackground(new Color(245, 91, 96));
            backButton.setBounds(350, 300, 100, 60);


            changeStatus(status);

            /** 画面サイズが変更されたときに呼ばれるイベントリスナー
             * ここ書く予定
             */


        }catch (Exception ex){
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this,"ERROR : " + ex.toString());
        }

    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        if(ae.getSource() == checkButton){
            pushCheckButton();
            System.out.println("確認ボタンが押されました");
        }
        else if(ae.getSource() == setButton){
            pushSetButton();
            System.out.println("設定ボタンが押されました");
        }
        else if(ae.getSource() == callButton){
            pushCallButton();
            System.out.println("CALLボタンが押されました");
        }
        else if(ae.getSource() == highAndLowButton){
            pushHighAndLowButton();
            System.out.println("HIGH & LOWボタンが押されました");
        }
        else if(ae.getSource() == targetButton){
            pushTargetButton();
            System.out.println("TARGETボタンが押されました");
        }
        else if(ae.getSource() == slashButton){
            pushSlashButton();
            System.out.println("SLASHボタンが押されました");
        }
        else if(ae.getSource() == backButton){
            pushBackButton();
            System.out.println("戻るボタンが押されました");
        }
        else if(ae.getSource() == yesButton){
            pushYesButton();
            System.out.println("はいボタンが押されました");
        }
        else if(ae.getSource() == clearButton){
            inputNumberField.setText("");
            button0.setEnabled(true);
            button1.setEnabled(true);
            button2.setEnabled(true);
            button3.setEnabled(true);
            button4.setEnabled(true);
            button5.setEnabled(true);
            button6.setEnabled(true);
            button7.setEnabled(true);
            button8.setEnabled(true);
            button9.setEnabled(true);
            setButton.setEnabled(false);
            callButton.setEnabled(false);
        }
        else if(ae.getSource() == button0){
            inputNumberField.setText(inputNumberField.getText() + 0);
            button0.setEnabled(false);
            setNumber = this.inputNumberField.getText();
            if(checkNumber(setNumber)){
                setButton.setEnabled(true);
                button0.setEnabled(false);
                button1.setEnabled(false);
                button2.setEnabled(false);
                button3.setEnabled(false);
                button4.setEnabled(false);
                button5.setEnabled(false);
                button6.setEnabled(false);
                button7.setEnabled(false);
                button8.setEnabled(false);
                button9.setEnabled(false);
            }
            else{
                setButton.setEnabled(false);
            }
        }
        else if(ae.getSource() == button1){
            inputNumberField.setText(inputNumberField.getText() + 1);
            button1.setEnabled(false);
            setNumber = this.inputNumberField.getText();
            if(checkNumber(setNumber)){
                setButton.setEnabled(true);
                button0.setEnabled(false);
                button1.setEnabled(false);
                button2.setEnabled(false);
                button3.setEnabled(false);
                button4.setEnabled(false);
                button5.setEnabled(false);
                button6.setEnabled(false);
                button7.setEnabled(false);
                button8.setEnabled(false);
                button9.setEnabled(false);
            }
            else{
                setButton.setEnabled(false);
            }
        }
        else if(ae.getSource() == button2){
            inputNumberField.setText(inputNumberField.getText() + 2);
            button2.setEnabled(false);
            setNumber = this.inputNumberField.getText();
            if(checkNumber(setNumber)){
                setButton.setEnabled(true);
                button0.setEnabled(false);
                button1.setEnabled(false);
                button2.setEnabled(false);
                button3.setEnabled(false);
                button4.setEnabled(false);
                button5.setEnabled(false);
                button6.setEnabled(false);
                button7.setEnabled(false);
                button8.setEnabled(false);
                button9.setEnabled(false);
            }
            else{
                setButton.setEnabled(false);
            }
        }
        else if(ae.getSource() == button3){
            inputNumberField.setText(inputNumberField.getText() + 3);
            button3.setEnabled(false);
            setNumber = this.inputNumberField.getText();
            if(checkNumber(setNumber)){
                setButton.setEnabled(true);
                button0.setEnabled(false);
                button1.setEnabled(false);
                button2.setEnabled(false);
                button3.setEnabled(false);
                button4.setEnabled(false);
                button5.setEnabled(false);
                button6.setEnabled(false);
                button7.setEnabled(false);
                button8.setEnabled(false);
                button9.setEnabled(false);
            }
            else{
                setButton.setEnabled(false);
            }
        }
        else if(ae.getSource() == button4){
            inputNumberField.setText(inputNumberField.getText() + 4);
            button4.setEnabled(false);
            setNumber = this.inputNumberField.getText();
            if(checkNumber(setNumber)){
                setButton.setEnabled(true);
                button0.setEnabled(false);
                button1.setEnabled(false);
                button2.setEnabled(false);
                button3.setEnabled(false);
                button4.setEnabled(false);
                button5.setEnabled(false);
                button6.setEnabled(false);
                button7.setEnabled(false);
                button8.setEnabled(false);
                button9.setEnabled(false);
            }
            else{
                setButton.setEnabled(false);
            }
        }
        else if(ae.getSource() == button5){
            inputNumberField.setText(inputNumberField.getText() + 5);
            button5.setEnabled(false);
            setNumber = this.inputNumberField.getText();
            if(checkNumber(setNumber)){
                setButton.setEnabled(true);
                button0.setEnabled(false);
                button1.setEnabled(false);
                button2.setEnabled(false);
                button3.setEnabled(false);
                button4.setEnabled(false);
                button5.setEnabled(false);
                button6.setEnabled(false);
                button7.setEnabled(false);
                button8.setEnabled(false);
                button9.setEnabled(false);
            }
            else{
                setButton.setEnabled(false);
            }
        }
        else if(ae.getSource() == button6){
            inputNumberField.setText(inputNumberField.getText() + 6);
            button6.setEnabled(false);
            setNumber = this.inputNumberField.getText();
            if(checkNumber(setNumber)){
                setButton.setEnabled(true);
                button0.setEnabled(false);
                button1.setEnabled(false);
                button2.setEnabled(false);
                button3.setEnabled(false);
                button4.setEnabled(false);
                button5.setEnabled(false);
                button6.setEnabled(false);
                button7.setEnabled(false);
                button8.setEnabled(false);
                button9.setEnabled(false);
            }
            else{
                setButton.setEnabled(false);
            }
        }
        else if(ae.getSource() == button7){
            inputNumberField.setText(inputNumberField.getText() + 7);
            button7.setEnabled(false);
            setNumber = this.inputNumberField.getText();
            if(checkNumber(setNumber)){
                setButton.setEnabled(true);
                button0.setEnabled(false);
                button1.setEnabled(false);
                button2.setEnabled(false);
                button3.setEnabled(false);
                button4.setEnabled(false);
                button5.setEnabled(false);
                button6.setEnabled(false);
                button7.setEnabled(false);
                button8.setEnabled(false);
                button9.setEnabled(false);
            }
            else{
                setButton.setEnabled(false);
            }
        }
        else if(ae.getSource() == button8){
            inputNumberField.setText(inputNumberField.getText() + 8);
            button8.setEnabled(false);
            setNumber = this.inputNumberField.getText();
            if(checkNumber(setNumber)){
                setButton.setEnabled(true);
                button0.setEnabled(false);
                button1.setEnabled(false);
                button2.setEnabled(false);
                button3.setEnabled(false);
                button4.setEnabled(false);
                button5.setEnabled(false);
                button6.setEnabled(false);
                button7.setEnabled(false);
                button8.setEnabled(false);
                button9.setEnabled(false);
            }
            else{
                setButton.setEnabled(false);
            }
        }
        else if(ae.getSource() == button9){
            inputNumberField.setText(inputNumberField.getText() + 9);
            button9.setEnabled(false);
            setNumber = this.inputNumberField.getText();
            if(checkNumber(setNumber)){
                setButton.setEnabled(true);
                button0.setEnabled(false);
                button1.setEnabled(false);
                button2.setEnabled(false);
                button3.setEnabled(false);
                button4.setEnabled(false);
                button5.setEnabled(false);
                button6.setEnabled(false);
                button7.setEnabled(false);
                button8.setEnabled(false);
                button9.setEnabled(false);
            }
            else{
                setButton.setEnabled(false);
            }
        }

    }

    public void changeStatus(int status){

        switch (status){
            /** マッチング完了直後画面 */
            case 1:
                //this.add(popUpLabel);
                this.add(checkButton);
                repaint();
                break;
            /** 設定ナンバー入力画面 */
            case 2:
                //this.remove(popUpLabel);
                this.remove(checkButton);
                this.add(progressionLabel);
                this.add(button0);
                this.add(button1);
                this.add(button2);
                this.add(button3);
                this.add(button4);
                this.add(button5);
                this.add(button6);
                this.add(button7);
                this.add(button8);
                this.add(button9);
                this.add(setButton);
                this.add(clearButton);
                this.add(backGroundLabel1);
                this.add(backGroundLabel2);
                this.add(inputNumberField);
                setButton.setEnabled(false);
                repaint();
                break;
            /** 相手の設定ナンバー入力待ち画面（先攻プレイヤー）*/
            case 3:
                progressionLabel.setText("相手が設定ナンバーを入力中です");
                //ポップアップラベルの記述

                this.add(popUpLabel);
                repaint();
                break;
            /** 自分のターン（アイテム未使用）*/
            case 4:
                progressionLabel.setText("あなたのターンです");
                if(this.status != 5 && this.status != 6 && this.status != 7){
                    // 初期の自分のターン画面
                    button0.setEnabled(true);
                    button1.setEnabled(true);
                    button2.setEnabled(true);
                    button3.setEnabled(true);
                    button4.setEnabled(true);
                    button5.setEnabled(true);
                    button6.setEnabled(true);
                    button7.setEnabled(true);
                    button8.setEnabled(true);
                    button9.setEnabled(true);
                    this.remove(setButton);
                    this.add(callButton);
                    this.add(callNumberLabel);
                    this.add(highAndLowButton);
                    this.add(slashButton);
                    this.add(targetButton);
                    this.add(itemBackgroundLabel);
                    //this.add(myCallTable);
                    //this.add(myScrollPane);
                    //this.add(new JScrollPane(myCallTable), BorderLayout.CENTER);
                    inputNumberField.setText("");
                }
                else{
                    // アイテムを使用した後の自分のターン画面
                    this.remove(checkButton);
                    this.remove(popUpLabel);
                    button0.setEnabled(true);
                    button1.setEnabled(true);
                    button2.setEnabled(true);
                    button3.setEnabled(true);
                    button4.setEnabled(true);
                    button5.setEnabled(true);
                    button6.setEnabled(true);
                    button7.setEnabled(true);
                    button8.setEnabled(true);
                    button9.setEnabled(true);
                    callButton.setEnabled(true);
                    inputNumberField.setText("");
                }

                repaint();
                break;
            /** HIGH&LOW使用画面 */
            case 5:
                button0.setEnabled(false);
                button1.setEnabled(false);
                button2.setEnabled(false);
                button3.setEnabled(false);
                button4.setEnabled(false);
                button5.setEnabled(false);
                button6.setEnabled(false);
                button7.setEnabled(false);
                button8.setEnabled(false);
                button9.setEnabled(false);
                callButton.setEnabled(false);
                highAndLowButton.setEnabled(false);
                slashButton.setEnabled(false);
                targetButton.setEnabled(false);
                popUpLabel.setText("HIGH & LOWを使用しますか？");
                subPopUpLabel.setText("※アイテムの使用は一度きりです");
                subPopUpLabel.setForeground(Color.RED);
                this.add(popUpLabel);
                this.add(yesButton);
                this.add(backButton);
                repaint();
                break;
            /** SLASH使用画面 */
            case 6:
                popUpLabel.setText("SLASHを使用しますか？");
                this.add(popUpLabel);
                this.add(yesButton);
                this.add(backButton);
                button0.setEnabled(false);
                button1.setEnabled(false);
                button2.setEnabled(false);
                button3.setEnabled(false);
                button4.setEnabled(false);
                button5.setEnabled(false);
                button6.setEnabled(false);
                button7.setEnabled(false);
                button8.setEnabled(false);
                button9.setEnabled(false);
                callButton.setEnabled(false);
                highAndLowButton.setEnabled(false);
                slashButton.setEnabled(false);
                targetButton.setEnabled(false);
                repaint();
                break;
            /** TARGET使用画面 */
            case 7:
                popUpLabel.setText("TARGETするナンバーを選択してください");
                this.add(popUpLabel);
                this.add(backButton);
                callButton.setEnabled(false);
                highAndLowButton.setEnabled(false);
                slashButton.setEnabled(false);
                targetButton.setEnabled(false);
                inputNumberField.setText("");
                repaint();
                break;

            /** アイテム結果表示画面 */
            case 8:
                this.remove(backButton);
                this.remove(yesButton);
                if(this.status == 5){
                    popUpLabel.setText("HIGH & LOW 結果");
                }
                else if(this.status == 6){
                    popUpLabel.setText("SLASH 結果");
                }
                else if(this.status == 7){
                    popUpLabel.setText("TARGET 結果");
                }
                checkButton.setText("確認");
                this.add(checkButton);
                repaint();
                break;
            /** CALL結果表示画面 */
            case 9:
                inputNumberField.setText("");
                popUpLabel.setText("CALL結果");
                this.add(popUpLabel);
                this.add(backButton);
                repaint();
                break;
            /** マッチング中断画面 */
            case 10:
                popUpLabel.setText("対戦相手との通信が切断されました");
                this.add(popUpLabel);
                this.add(backButton);
                button0.setEnabled(false);
                button1.setEnabled(false);
                button2.setEnabled(false);
                button3.setEnabled(false);
                button4.setEnabled(false);
                button5.setEnabled(false);
                button6.setEnabled(false);
                button7.setEnabled(false);
                button8.setEnabled(false);
                button9.setEnabled(false);
                callButton.setEnabled(false);
                repaint();
                break;
            /** 相手のターン画面 */
            case 11:
                progressionLabel.setText("相手のターンです");
                button0.setEnabled(false);
                button1.setEnabled(false);
                button2.setEnabled(false);
                button3.setEnabled(false);
                button4.setEnabled(false);
                button5.setEnabled(false);
                button6.setEnabled(false);
                button7.setEnabled(false);
                button8.setEnabled(false);
                button9.setEnabled(false);
                callButton.setEnabled(false);
                this.remove(backButton);
                repaint();
                break;
            /** アイテム使用画面から戻るボタンが押されて自分のターン画面に戻る */
            case 12:
                button0.setEnabled(true);
                button1.setEnabled(true);
                button2.setEnabled(true);
                button3.setEnabled(true);
                button4.setEnabled(true);
                button5.setEnabled(true);
                button6.setEnabled(true);
                button7.setEnabled(true);
                button8.setEnabled(true);
                button9.setEnabled(true);
                callButton.setEnabled(true);
                // アイテム未使用ならボタン使用可にする
                if(item){
                    highAndLowButton.setEnabled(true);
                    slashButton.setEnabled(true);
                    targetButton.setEnabled(true);
                }
                inputNumberField.setText("");
                this.remove(popUpLabel);
                this.remove(yesButton);
                this.remove(backButton);
                repaint();
                break;
        }
    }

    public void displayError(){

    }

    public boolean checkNumber(String number){
        if(number.length() == 3){
            return true;
        }
        return false;
    }

    // このメソッドいる？
    public void displayItemResult(String result){
        item = false;
        changeStatus(8);
    }

    // このメソッドいる？
    public void displayCallResult(String result){
        changeStatus(9);
    }

    public void pushCheckButton(){
        if(this.status == 5 || this.status == 6 || this.status == 7){
            // アイテム使用後の確認ボタンの場合は自分のターン画面に遷移
            changeStatus(4);
        }
        else{
            // 先攻後攻の確認ボタンの場合は設定ナンバー画面に遷移
            changeStatus(2);
        }
    }

    public void pushSetButton(){
        this.status = 4;
        changeStatus(4);
    }

    public void pushCallButton(){
        callNumber = this.inputNumberField.getText();
        if(checkNumber(callNumber)){
            changeStatus(9);
        }
        else{
            inputNumberField.setText("");
            System.out.println("正しい数字を入力してください");
        }

    }

    public void pushHighAndLowButton(){
        this.status = 5;
        changeStatus(5);
    }

    public void pushSlashButton(){
        this.status = 6;
        changeStatus(6);
    }

    public void pushTargetButton(){
        this.status = 7;
        changeStatus(7);
    }

    public void pushBackButton(){
        changeStatus(12);
    }

    public void pushYesButton(){
        changeStatus(8);
    }
}
