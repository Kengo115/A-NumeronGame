package Message;

public class MatchingCancelMessage extends Message{

    public MatchingCancelMessage(String demandType,String username){ //マッチングキャンセルメッセージクラスの生成
        this.demandType = demandType;
        this.username = username;
    }
}
