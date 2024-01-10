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
    private int status = 4;
    //アイテム未使用 4
    //HighAndLow使用 5
    //Slash使用 6
    //Target使用 7
    /** ターンの管理 */
    private boolean myTurn = true;
    /** ターン数の管理 */
    private int myTotalTurn = 1;
    private int opponentTotalTurn = 1;
    /** 設定ナンバー */
    private String setNumber;
    /** CALLナンバー */
    private String callNumber;
    /** 入力中の数字一時保存 */
    private String inputNumber;
    /** TARGETで指定する数字を保存 */
    private String targetNumber = "10";
    /** アイテムの使用可不可 */
    private boolean item = true;
    /** 使用するアイテム名 */
    private String itemName;
    /** コントローラ */
    Controller controller;
    /** 確認ボタン */
    JButton checkButton = new JButton("確認");
    /** ポップアップラベル */
    JLabel popUpLabel = new JLabel("");
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
    /** 設定ナンバー表示 */
    JLabel setNumberLabel = new JLabel("");
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
    JButton setCallButton = new JButton("設定");
    /** CALLナンバーラベル */
    JLabel callNumberLabel = new JLabel("CALLナンバー");    // CALLナンバーの水色背景
    /** 設定ナンバー */
    JLabel setNumberTopLabel = new JLabel("設定ナンバー");
    /** アイテムボタン */
    JButton highAndLowButton = new JButton("H");
    JButton slashButton = new JButton("S");
    JButton targetButton = new JButton("T");
    JLabel itemBackgroundLabel = new JLabel("");    //アイテムボタンの水色背景
    /** はいボタン */
    JButton yesButton = new JButton("はい");
    /** 戻るボタン */
    JButton backButton = new JButton("戻る");
    /** 相手のターンに移行するボタン */
    JButton changeOpponentTurnButton = new JButton("戻る");
    /** 表のコラム */
    private String[] columnNames = {"TURN", "NUMBER", "EAT", "BITE"};
    /** 自分の表 */
    private String[][] myTableData = {
            {"TURN", "NUMBER", "EAT", "BITE"}   /* 1行目 */
    };
    /** 表に追加するデータ */
    private String[] addData;
    /** 自分のテーブルモデル */
    DefaultTableModel myTableModel = new DefaultTableModel(myTableData,columnNames);
    /** 自分のCALL履歴表 */
    JTable myCallTable = new JTable(myTableModel);
    /** 相手の表 */
    private String[][] opponentTableData = {
            {"TURN", "NUMBER", "EAT", "BITE"}
    };
    /** 相手のテーブルモデル */
    DefaultTableModel opponentTableModel = new DefaultTableModel(opponentTableData,columnNames);
    /** 相手のCALL履歴表 */
    JTable opponentCallTable = new JTable(opponentTableModel);
    /** アイテム結果表示ラベル */
    JLabel myItemResult = new JLabel("アイテム未使用");
    JLabel opponentItemResult = new JLabel("アイテム未使用");

    public GameScreen(Controller controller){
        super();
        this.controller = controller;

        try{
            this.setLayout(null);

            // フォント設定（サイズごと）
            Font font20 = new Font("SansSerif", Font.BOLD, 20);
            Font font16 = new Font("SansSerif", Font.BOLD, 16);
            Font font10 = new Font("SansSerif", Font.BOLD, 10);

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

            subPopUpLabel.setFont(font20);
            subPopUpLabel.setBounds(0,230,1000,100);
            subPopUpLabel.setHorizontalAlignment(JLabel.CENTER);
            subPopUpLabel.setForeground(new Color(220, 31, 75));

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

            setCallButton.addActionListener(this);
            setCallButton.setFont(font20);
            setCallButton.setBackground(new Color(250, 244, 88));
            setCallButton.setBounds(475,450,110,50);

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

            // 設定ナンバー表示ラベル
            setNumberLabel.setFont(font20);
            setNumberLabel.setBounds(50,90,200,50);
            setNumberLabel.setHorizontalAlignment(JLabel.CENTER);
            setNumberLabel.setOpaque(true);
            setNumberLabel.setBackground(Color.WHITE);
            setNumberLabel.setBorder(border1);

            setNumberTopLabel.setFont(font16);
            setNumberTopLabel.setOpaque(true);
            setNumberTopLabel.setForeground(Color.WHITE);
            setNumberTopLabel.setBackground(new Color(31,92,220));
            setNumberTopLabel.setBounds(50,60,200,30);
            setNumberTopLabel.setHorizontalAlignment(JLabel.CENTER);
            setNumberTopLabel.setBorder(border1);

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
            myCallTable.setOpaque(false);
            myCallTable.setBackground(Color.WHITE);
            myCallTable.setFont(font16);
            myCallTable.setBorder(border1);
            myCallTable.setBounds(50,210,300,300);

            opponentCallTable.setOpaque(false);
            opponentCallTable.setBackground(Color.WHITE);
            opponentCallTable.setFont(font16);
            opponentCallTable.setBorder(border1);
            opponentCallTable.setBounds(650,210,300,300);

            // アイテム結果表示ラベル
            myItemResult.setFont(font16);
            myItemResult.setBounds(50,175,300,25);
            myItemResult.setHorizontalAlignment(JLabel.CENTER);
            myItemResult.setOpaque(true);
            myItemResult.setBackground(Color.WHITE);
            myItemResult.setBorder(border1);

            opponentItemResult.setFont(font16);
            opponentItemResult.setBounds(650,175,300,25);
            opponentItemResult.setHorizontalAlignment(JLabel.CENTER);
            opponentItemResult.setOpaque(true);
            opponentItemResult.setBackground(Color.WHITE);
            opponentItemResult.setBorder(border1);

            /** アイテム使用 */
            yesButton.addActionListener(this);
            yesButton.setFont(font20);
            yesButton.setBackground(new Color(101, 143, 232));
            yesButton.setBounds(550, 300, 100, 60);

            backButton.addActionListener(this);
            backButton.setFont(font20);
            backButton.setBackground(new Color(245, 91, 96));
            backButton.setBounds(350, 300, 100, 60);

            changeOpponentTurnButton.addActionListener(this);
            changeOpponentTurnButton.setFont(font20);
            changeOpponentTurnButton.setBackground(new Color(245, 91, 96));    // 背景色設定
            changeOpponentTurnButton.setBounds(450, 300, 100, 60);
            changeOpponentTurnButton.setOpaque(true);

            changeStatus(status);

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
        else if(ae.getSource() == setCallButton){
            pushSetCallButton();
            System.out.println("設定/CALLボタンが押されました");
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
        else if(ae.getSource() == changeOpponentTurnButton){
            pushChangeOpponentTurnButton();
            System.out.println("相手のターンに移行します");
        }
        else if(ae.getSource() == clearButton){
            System.out.println("クリアボタンが押されました");
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
            setCallButton.setEnabled(false);
            //callButton.setEnabled(false);
        }
        else if(ae.getSource() == button0){
            if(this.status != 7) {
                // CALLや設定のためのボタン操作の場合
                inputNumberField.setText(inputNumberField.getText() + 0);
                button0.setEnabled(false);
                inputNumber = inputNumberField.getText();
                if (checkNumber(inputNumber)) {
                    setCallButton.setEnabled(true);
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
            }
            else{
                // TARGETのためのボタン操作の場合
                targetNumber = "0";
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
                pushYesButton();
            }
        }
        else if(ae.getSource() == button1){
            if(this.status != 7) {
                inputNumberField.setText(inputNumberField.getText() + 1);
                button1.setEnabled(false);
                inputNumber = inputNumberField.getText();
                if (checkNumber(inputNumber)) {
                    setCallButton.setEnabled(true);
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
            }
            else{
                targetNumber = "1";
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
                pushYesButton();
            }
        }
        else if(ae.getSource() == button2){
            if(this.status != 7) {
                inputNumberField.setText(inputNumberField.getText() + 2);
                button2.setEnabled(false);
                inputNumber = inputNumberField.getText();
                if (checkNumber(inputNumber)) {
                    setCallButton.setEnabled(true);
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
            }
            else{
                targetNumber = "2";
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
                pushYesButton();
            }
        }
        else if(ae.getSource() == button3){
            if(this.status != 7) {
                inputNumberField.setText(inputNumberField.getText() + 3);
                button3.setEnabled(false);
                inputNumber = inputNumberField.getText();
                if (checkNumber(inputNumber)) {
                    setCallButton.setEnabled(true);
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
            }
            else {
                targetNumber = "3";
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
                pushYesButton();
            }
        }
        else if(ae.getSource() == button4){
            if(this.status != 7) {
                inputNumberField.setText(inputNumberField.getText() + 4);
                button4.setEnabled(false);
                inputNumber = inputNumberField.getText();
                if (checkNumber(inputNumber)) {
                    setCallButton.setEnabled(true);
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
            }
            else {
                targetNumber = "4";
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
                pushYesButton();
            }
        }
        else if(ae.getSource() == button5){
            if(this.status != 7) {
                inputNumberField.setText(inputNumberField.getText() + 5);
                button5.setEnabled(false);
                inputNumber = inputNumberField.getText();
                if (checkNumber(inputNumber)) {
                    setCallButton.setEnabled(true);
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
            }
            else {
                targetNumber = "5";
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
                pushYesButton();
            }
        }
        else if(ae.getSource() == button6){
            if(this.status != 7) {
                inputNumberField.setText(inputNumberField.getText() + 6);
                button6.setEnabled(false);
                inputNumber = inputNumberField.getText();
                if (checkNumber(inputNumber)) {
                    setCallButton.setEnabled(true);
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
            }
            else{
                targetNumber = "6";
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
                pushYesButton();
            }
        }
        else if(ae.getSource() == button7){
            if(this.status != 7) {
                inputNumberField.setText(inputNumberField.getText() + 7);
                button7.setEnabled(false);
                inputNumber = inputNumberField.getText();
                if (checkNumber(inputNumber)) {
                    setCallButton.setEnabled(true);
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
            }
            else{
                targetNumber = "7";
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
                pushYesButton();
            }
        }
        else if(ae.getSource() == button8){
            if(this.status != 7) {
                inputNumberField.setText(inputNumberField.getText() + 8);
                button8.setEnabled(false);
                inputNumber = inputNumberField.getText();
                if (checkNumber(inputNumber)) {
                    setCallButton.setEnabled(true);
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
            }
            else{
                targetNumber = "8";
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
                pushYesButton();
            }
        }
        else if(ae.getSource() == button9){
            if(this.status != 7) {
                inputNumberField.setText(inputNumberField.getText() + 9);
                button9.setEnabled(false);
                inputNumber = inputNumberField.getText();
                if (checkNumber(inputNumber)) {
                    setCallButton.setEnabled(true);
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
            }
            else{
                targetNumber = "9";
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
                pushYesButton();
            }
        }

    }

    public void changeStatus(int status){
        //ステータスの数値を合わせる
        this.status = status;

        //とりあえずすべてのコンポーネントをいったんremoveする
        this.remove(checkButton);
        this.remove(popUpLabel);
        this.remove(subPopUpLabel);
        this.remove(progressionLabel);
        this.remove(backGroundLabel1);
        this.remove(backGroundLabel2);
        this.remove(inputNumberField);
        this.remove(setNumberLabel);
        this.remove(button0);
        this.remove(button1);
        this.remove(button2);
        this.remove(button3);
        this.remove(button4);
        this.remove(button5);
        this.remove(button6);
        this.remove(button7);
        this.remove(button8);
        this.remove(button9);
        this.remove(clearButton);
        this.remove(setCallButton);
        this.remove(callNumberLabel);
        this.remove(setNumberTopLabel);
        this.remove(highAndLowButton);
        this.remove(slashButton);
        this.remove(targetButton);
        this.remove(itemBackgroundLabel);
        this.remove(yesButton);
        this.remove(backButton);
        this.remove(changeOpponentTurnButton);
        this.remove(myCallTable);
        this.remove(opponentCallTable);
        this.remove(myItemResult);
        this.remove(opponentItemResult);

        switch (status){

            /** マッチング完了直後画面 */
            case 1:
                this.add(checkButton);
                this.add(popUpLabel);
                repaint();
                break;
            /** 設定ナンバー入力画面 */
            case 2:
                setCallButton.setText("設定");
                progressionLabel.setText("設定ナンバーを入力してください");
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
                this.add(setCallButton);
                this.add(clearButton);
                this.add(inputNumberField);
                this.add(backGroundLabel1);
                this.add(backGroundLabel2);
                this.add(progressionLabel);
                setCallButton.setEnabled(false);
                repaint();
                break;
            /** 相手の設定ナンバー入力待ち画面 */
            case 3:
                progressionLabel.setText("相手が設定ナンバーを入力中です");
                popUpLabel.setText("相手が設定ナンバー選択しています");
                this.add(popUpLabel);
                this.add(progressionLabel);
                repaint();
                break;
            /** 自分のターン（アイテム未使用）*/
            case 4:
                progressionLabel.setText("あなたのターンです");
                if(item){
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
                    clearButton.setEnabled(true);
                    highAndLowButton.setEnabled(true);
                    slashButton.setEnabled(true);
                    targetButton.setEnabled(true);
                    setCallButton.setText("CALL");
                    setCallButton.setEnabled(false);
                    setNumberLabel.setText(setNumber);
                    inputNumberField.setText("");
                    this.add(progressionLabel);
                    this.add(callNumberLabel);
                    this.add(highAndLowButton);
                    this.add(slashButton);
                    this.add(targetButton);
                    this.add(setCallButton);
                    this.add(setNumberLabel);
                    this.add(setNumberTopLabel);
                    this.add(clearButton);
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
                    this.add(inputNumberField);
                    this.add(backGroundLabel1);
                    this.add(backGroundLabel2);
                    this.add(itemBackgroundLabel);
                    this.add(myCallTable);
                    this.add(opponentCallTable);
                    this.add(myItemResult);
                    this.add(opponentItemResult);
                }
                else{
                    // アイテムを使用した後の自分のターン画面
                    setCallButton.setText("CALL");
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
                    clearButton.setEnabled(true);
                    setCallButton.setEnabled(false);
                    highAndLowButton.setEnabled(false);
                    slashButton.setEnabled(false);
                    targetButton.setEnabled(false);
                    inputNumberField.setText("");
                    this.add(progressionLabel);
                    this.add(callNumberLabel);
                    this.add(highAndLowButton);
                    this.add(slashButton);
                    this.add(targetButton);
                    this.add(setCallButton);
                    this.add(setNumberLabel);
                    this.add(setNumberTopLabel);
                    this.add(clearButton);
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
                    this.add(inputNumberField);
                    this.add(backGroundLabel1);
                    this.add(backGroundLabel2);
                    this.add(itemBackgroundLabel);
                    this.add(myCallTable);
                    this.add(opponentCallTable);
                    this.add(myItemResult);
                    this.add(opponentItemResult);
                }

                repaint();
                break;
            /** HIGH&LOW使用画面 */
            case 5:
                setCallButton.setText("CALL");
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
                clearButton.setEnabled(false);
                setCallButton.setEnabled(false);
                highAndLowButton.setEnabled(false);
                slashButton.setEnabled(false);
                targetButton.setEnabled(false);
                popUpLabel.setText("HIGH & LOWを使用しますか？");
                this.add(yesButton);
                this.add(backButton);
                this.add(popUpLabel);
                this.add(progressionLabel);
                this.add(callNumberLabel);
                this.add(highAndLowButton);
                this.add(slashButton);
                this.add(targetButton);
                this.add(setCallButton);
                this.add(setNumberLabel);
                this.add(setNumberTopLabel);
                this.add(clearButton);
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
                this.add(inputNumberField);
                this.add(backGroundLabel1);
                this.add(backGroundLabel2);
                this.add(itemBackgroundLabel);
                this.add(myCallTable);
                this.add(opponentCallTable);
                this.add(myItemResult);
                this.add(opponentItemResult);
                repaint();
                break;
            /** SLASH使用画面 */
            case 6:
                setCallButton.setText("CALL");
                popUpLabel.setText("SLASHを使用しますか？");
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
                clearButton.setEnabled(false);
                setCallButton.setEnabled(false);
                highAndLowButton.setEnabled(false);
                slashButton.setEnabled(false);
                targetButton.setEnabled(false);
                this.add(yesButton);
                this.add(backButton);
                this.add(popUpLabel);
                this.add(progressionLabel);
                this.add(callNumberLabel);
                this.add(highAndLowButton);
                this.add(slashButton);
                this.add(targetButton);
                this.add(setCallButton);
                this.add(setNumberLabel);
                this.add(setNumberTopLabel);
                this.add(clearButton);
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
                this.add(inputNumberField);
                this.add(backGroundLabel1);
                this.add(backGroundLabel2);
                this.add(itemBackgroundLabel);
                this.add(myCallTable);
                this.add(opponentCallTable);
                this.add(myItemResult);
                this.add(opponentItemResult);
                repaint();
                break;
            /** TARGET使用画面 */
            case 7:
                setCallButton.setText("CALL");
                popUpLabel.setText("TARGETするナンバーを選択してください");
                setCallButton.setEnabled(false);
                highAndLowButton.setEnabled(false);
                slashButton.setEnabled(false);
                targetButton.setEnabled(false);
                clearButton.setEnabled(false);
                inputNumberField.setText("");
                this.add(backButton);
                this.add(popUpLabel);
                this.add(progressionLabel);
                this.add(callNumberLabel);
                this.add(highAndLowButton);
                this.add(slashButton);
                this.add(targetButton);
                this.add(setCallButton);
                this.add(setNumberLabel);
                this.add(setNumberTopLabel);
                this.add(clearButton);
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
                this.add(inputNumberField);
                this.add(backGroundLabel1);
                this.add(backGroundLabel2);
                this.add(itemBackgroundLabel);
                this.add(myCallTable);
                this.add(opponentCallTable);
                this.add(myItemResult);
                this.add(opponentItemResult);
                repaint();
                break;

            /** アイテム結果表示画面 */
            case 8:
                setCallButton.setText("CALL");
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
                setCallButton.setEnabled(false);
                highAndLowButton.setEnabled(false);
                slashButton.setEnabled(false);
                targetButton.setEnabled(false);
                clearButton.setEnabled(false);
                checkButton.setText("確認");
                this.add(checkButton);
                this.add(subPopUpLabel);
                this.add(popUpLabel);
                this.add(progressionLabel);
                this.add(callNumberLabel);
                this.add(highAndLowButton);
                this.add(slashButton);
                this.add(targetButton);
                this.add(setCallButton);
                this.add(clearButton);
                this.add(setNumberLabel);
                this.add(setNumberTopLabel);
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
                this.add(inputNumberField);
                this.add(backGroundLabel1);
                this.add(backGroundLabel2);
                this.add(itemBackgroundLabel);
                this.add(myCallTable);
                this.add(opponentCallTable);
                this.add(myItemResult);
                this.add(opponentItemResult);
                repaint();
                break;
            /** CALL結果表示画面 */
            case 9:
                setCallButton.setText("CALL");
                inputNumberField.setText("");
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
                setCallButton.setEnabled(false);
                highAndLowButton.setEnabled(false);
                slashButton.setEnabled(false);
                targetButton.setEnabled(false);
                clearButton.setEnabled(false);
                this.add(changeOpponentTurnButton);
                this.add(subPopUpLabel);
                this.add(popUpLabel);
                this.add(progressionLabel);
                this.add(callNumberLabel);
                this.add(highAndLowButton);
                this.add(slashButton);
                this.add(targetButton);
                this.add(setCallButton);
                this.add(clearButton);
                this.add(setNumberLabel);
                this.add(setNumberTopLabel);
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
                this.add(inputNumberField);
                this.add(backGroundLabel1);
                this.add(backGroundLabel2);
                this.add(itemBackgroundLabel);
                this.add(myCallTable);
                this.add(opponentCallTable);
                this.add(myItemResult);
                this.add(opponentItemResult);
                repaint();
                break;
            /** 通信切断画面 */
            case 10:
                setCallButton.setText("CALL");
                popUpLabel.setText("対戦相手との通信が切断されました");
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
                clearButton.setEnabled(false);
                setCallButton.setEnabled(false);
                highAndLowButton.setEnabled(false);
                slashButton.setEnabled(false);
                targetButton.setEnabled(false);
                this.add(backButton);
                this.add(popUpLabel);
                this.add(progressionLabel);
                this.add(callNumberLabel);
                this.add(highAndLowButton);
                this.add(slashButton);
                this.add(targetButton);
                this.add(setCallButton);
                this.add(clearButton);
                this.add(setNumberLabel);
                this.add(setNumberTopLabel);
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
                this.add(inputNumberField);
                this.add(backGroundLabel1);
                this.add(backGroundLabel2);
                this.add(itemBackgroundLabel);
                this.add(myCallTable);
                this.add(opponentCallTable);
                this.add(myItemResult);
                this.add(opponentItemResult);
                repaint();
                break;
            /** 相手のターン画面 */
            case 11:
                setCallButton.setText("CALL");
                inputNumberField.setText("");
                progressionLabel.setText("相手のターンです");
                setNumberLabel.setText(this.setNumber);
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
                clearButton.setEnabled(false);
                setCallButton.setEnabled(false);
                clearButton.setEnabled(false);
                this.add(progressionLabel);
                this.add(callNumberLabel);
                this.add(setCallButton);
                this.add(clearButton);
                this.add(setNumberLabel);
                this.add(setNumberTopLabel);
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
                this.add(inputNumberField);
                this.add(backGroundLabel1);
                this.add(backGroundLabel2);
                this.add(myCallTable);
                this.add(opponentCallTable);
                this.add(myItemResult);
                this.add(opponentItemResult);
                repaint();
                break;
            /** アイテム使用画面から戻るボタンが押されて自分のターン画面に戻る */
            case 12:
                setCallButton.setText("CALL");
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
                clearButton.setEnabled(true);
                setCallButton.setEnabled(false);
                highAndLowButton.setEnabled(true);
                slashButton.setEnabled(true);
                targetButton.setEnabled(true);
                inputNumberField.setText("");
                this.add(progressionLabel);
                this.add(callNumberLabel);
                this.add(highAndLowButton);
                this.add(slashButton);
                this.add(targetButton);
                this.add(setCallButton);
                this.add(setNumberLabel);
                this.add(setNumberTopLabel);
                this.add(clearButton);
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
                this.add(inputNumberField);
                this.add(backGroundLabel1);
                this.add(backGroundLabel2);
                this.add(itemBackgroundLabel);
                this.add(myCallTable);
                this.add(opponentCallTable);
                this.add(myItemResult);
                this.add(opponentItemResult);
                repaint();
                break;
            case 13:
                setCallButton.setText("CALL");
                popUpLabel.setText("制限時間を経過しました．");
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
                clearButton.setEnabled(false);
                setCallButton.setEnabled(false);
                highAndLowButton.setEnabled(false);
                slashButton.setEnabled(false);
                targetButton.setEnabled(false);
                this.add(backButton);
                this.add(popUpLabel);
                this.add(progressionLabel);
                this.add(callNumberLabel);
                this.add(highAndLowButton);
                this.add(slashButton);
                this.add(targetButton);
                this.add(setCallButton);
                this.add(clearButton);
                this.add(setNumberLabel);
                this.add(setNumberTopLabel);
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
                this.add(inputNumberField);
                this.add(backGroundLabel1);
                this.add(backGroundLabel2);
                this.add(itemBackgroundLabel);
                this.add(myCallTable);
                this.add(opponentCallTable);
                this.add(myItemResult);
                this.add(opponentItemResult);
                repaint();
                break;
        }
    }

    // 相手の通信が切断された場合に呼び出されるメソッド
    public void communicationCutoff(){
        changeStatus(10);
    }

    // 自分の制限時間経過で呼び出されるメソッド
    public void timeOut(){
        changeStatus(13);
    }

    public void initialization(){
        myTurn = true;
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
        clearButton.setEnabled(true);
        highAndLowButton.setEnabled(true);
        slashButton.setEnabled(true);
        targetButton.setEnabled(true);
        myTotalTurn = 1;
        opponentTotalTurn = 1;
        status = 1;
        inputNumberField.setText("");
        item = true;
        progressionLabel.setText("設定ナンバーを入力してください");
        setNumberLabel.setText("");
        setCallButton.setText("設定");
        for (int i = myTableModel.getRowCount()-1; i > 0; ){
            myTableModel.removeRow(i);
            i = myTableModel.getRowCount()-1;
        }
        for(int i = opponentTableModel.getRowCount()-1; i > 0; ){
            opponentTableModel.removeRow(i);
            i = opponentTableModel.getRowCount()-1;
        }
        myItemResult.setText("アイテム未使用");
        opponentItemResult.setText("アイテム未使用");
    }

    // 先攻プレイヤーのみ呼び出されるメソッド
    public void attackFirst(){
        popUpLabel.setText("対戦開始！あなたは先攻です");
        changeStatus(1);
    }

    // 後攻プレイヤーのみ呼び出されるメソッド
    public void attackLast(){
        popUpLabel.setText("対戦開始！あなたは後攻です");
        myTurn = false;
        changeStatus(1);
    }

    // 相手の設定ナンバーが入力されたことを確認して画面を遷移するメソッド
    public void opponentSetComplete(){
        if(myTurn){ //先攻の場合は自ターン開始
            changeStatus(4);
            myTurn = true;
        }
        else{
            changeStatus(2);
        }
    }

    public void changeTurn(){
        changeStatus(4);
    }

    public boolean checkNumber(String number){
        return number.length() == 3;
    }

    public void displayItemResult(String item,String result,boolean mine){
        String itemName;
        if(item.equals("HighAndLow")){
            itemName="HIGH & LOW";
        }
        else if(item.equals("Slash")){
            itemName="SLASH";
        }
        else if(item.equals("Target")){
            itemName="TARGET";
        }
        else {
            System.out.println("不正な遷移です");
            return;
        }

        if(mine) {
            popUpLabel.setText(itemName + "結果:使用者 自分");
            this.item = false;
            myItemResult.setText(result);
        }
        else {
            popUpLabel.setText(itemName + "結果:使用者 相手");
            opponentItemResult.setText(result);
        }
        subPopUpLabel.setText(result);
        changeStatus(8);
    }

    public void displayCallResult(String callNumber, String eat, String bite, boolean mine){
        if(mine) {
            popUpLabel.setText("CALL結果:自分のターン");
            this.addData = new String[]{Integer.toString(this.myTotalTurn), callNumber, eat, bite};
            myTableModel.addRow(addData);
            myTotalTurn++;
        }
        else {
            popUpLabel.setText("CALL結果:相手のターン");
            this.addData = new String[]{Integer.toString(this.opponentTotalTurn), callNumber, eat, bite};
            opponentTableModel.addRow(addData);
            opponentTotalTurn++;
        }
        subPopUpLabel.setText(callNumber+" → "+"EAT : " + eat + ", BITE : " + bite);
        changeStatus(9);
    }

    public void pushCheckButton(){
        if(this.status == 8){
            // アイテム使用後の確認ボタンの場合
            // 自分のターンの場合は自分のターン画面に遷移
            if(myTurn){
                changeStatus(4);
            }
            else{
                changeStatus(11);
            }
        }
        else{
            // 先攻後攻の確認ボタンの場合
            // 先攻の場合は設定ナンバー画面に遷移
            if(myTurn) changeStatus(2);
                //後攻の場合は相手の入力待ち
            else changeStatus(3);
        }
    }

    public void pushSetCallButton(){
        String buttonName = setCallButton.getText();
        if("設定".equals(buttonName)){
            this.setNumber = this.inputNumber;
            controller.set(this.setNumber);
            if(myTurn){
                changeStatus(3);
            }
            else{
                changeStatus(11);
                myTurn = false;
            }
        }
        else if("CALL".equals(buttonName)){
            this.callNumber = this.inputNumber;
            //this.displayCallResult(this.callNumber,"2","0",true);   // 単体用
            controller.call(this.callNumber); // 全体用
        }
    }

    public void pushHighAndLowButton(){
        this.itemName = "HighAndLow";
        changeStatus(5);
    }

    public void pushSlashButton(){
        this.itemName = "Slash";
        changeStatus(6);
    }

    public void pushTargetButton(){
        this.itemName = "Target";
        popUpLabel.setBounds(300,40,400,220);
        backButton.setBounds(350, 190, 100, 60);
        changeStatus(7);
    }

    public void pushBackButton(){
        if(this.status == 7){
            // ポップアップラベルをずらしたのを元に戻す
            popUpLabel.setBounds(300,150,400,220);
            backButton.setBounds(350,300,100,60);
            changeStatus(12);
        }
        else if(this.status == 10 || this.status == 13){
            // 相手の通信が切断された場合
            controller.screenTransition("lobby");
        }
        else{
            changeStatus(12);
        }
    }

    public void pushChangeOpponentTurnButton(){
        if(myTurn) { //自分のターンだった場合
            myTurn = false;
            changeStatus(11);
        }
        else { //相手のターンだった場合
            myTurn = true;
            changeStatus(4);
        }
    }

    public void pushYesButton(){
        controller.useItem(this.itemName, Integer.parseInt(this.targetNumber));   // 全体用
        //this.displayItemResult(this.itemName,"HIGH HIGH LOW",true); // 単体用
        // ポップアップラベルをずらしたのを元に戻す
        if(this.status == 7){
            popUpLabel.setBounds(300,150,400,220);
            backButton.setBounds(350,300,100,60);
        }
    }

}
