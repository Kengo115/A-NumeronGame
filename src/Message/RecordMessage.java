package Message;

public class RecordMessage extends Message{
    public int rate;//レート
    public int winCount;//勝利数
    public int loseCount;//敗北数
    public int drawCount;//引き分け数

    public RecordMessage(String demandType,String username){//戦績表示メッセージクラスの生成
        this.demandType =demandType;
        this.username = username;
    }
}
