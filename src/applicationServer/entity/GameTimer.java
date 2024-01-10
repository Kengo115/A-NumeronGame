package applicationServer.entity;

import applicationServer.Communication.ApplicationServerCommunication;
import applicationServer.controller.ApplicationController;
import applicationServer.controller.GameController;

import java.util.Timer;
import java.util.TimerTask;

public class GameTimer {
    private Timer timer;
    private int seconds;
    private static final int TIMEOUT_SECONDS = 180; // 180秒に固定

    //private static final int TIMEOUT_SECONDS = 10;//デバック用

    private final GameController gameController;

    public GameTimer(GameController gameController) {
        this.gameController = gameController;
        resetTimer();
    }

    public void startTimer() {
        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                seconds--;
                if (seconds <= 0) {

                    //デバック用
                    System.out.println("時間超過しました");

                    String errorPlayer; //時間超過したプレイヤー
                    String normalPlayer; //相手プレイヤー
                    if (gameController.getTurn() % 2 == 0) { //先攻が時間超過
                        //デバック用
                        System.out.println("先攻が時間超過しました");

                        errorPlayer = gameController.getFirstPlayer().getUserName();
                        normalPlayer = gameController.getSecondPlayer().getUserName();
                    }
                    else { //後攻が時間超過
                        //デバック用
                        System.out.println("後攻が時間超過しました");
                        normalPlayer = gameController.getFirstPlayer().getUserName();
                        errorPlayer = gameController.getSecondPlayer().getUserName();
                    }


                    ApplicationServerCommunication.timeout(errorPlayer);

                    stopTimer();
                }
            }
        }, 1000, 1000);
    }

    public void stopTimer() {
        if (timer != null) {
            timer.cancel();
        }
    }

    public void resetTimer() {
        stopTimer();
        seconds = TIMEOUT_SECONDS;
    }
}

