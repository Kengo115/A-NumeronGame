package Message;

public class RemainingTime extends Message{
    public int time;//残り時間

    public RemainingTime(String demandType,String username,int time){ //残り時間メッセージクラスの生成
        this.demandType = demandType;
        this.username = username;
        this.time = time;
    }
}
