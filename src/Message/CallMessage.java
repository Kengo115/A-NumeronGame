package Message;

public class CallMessage extends Message{
    public int EAT;//EAT
    public int BITE;//BITE
    public String callNumber; //コールしたナンバー

    public CallMessage(String demandType, String username, String callNumber, int EAT, int BITE){ //コールメッセージクラスの生成
        this.demandType = demandType;
        this.username = username;
        this.callNumber = callNumber;
        this.EAT = EAT;
        this.BITE = BITE;
    }
}
