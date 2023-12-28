/*
package applicationServer.entity;

import java.util.Timer;
import java.util.TimerTask;

public class Timer { //タイマー
    private int time = 180000;//制限時間
    private Timer timer = new Timer();
    private boolean running = true;

    public void startTimer(){//タイマー開始
        if(!running){
            running = true;
            timer = new Timer();
            timer.schedule(this,0,1000);
        }
    }
    public void stopTimer(){//タイマー停止
        running = false;
        timer.cancel();
    }
    public void resetTimer(){//タイマーリセット
        stopTimer();
        time = 180000;
    }
}
*/