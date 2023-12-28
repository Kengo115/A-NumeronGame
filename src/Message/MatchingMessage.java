package Message;

public class MatchingMessage extends Message{

    public MatchingMessage(String demandType,String username){ //マッチングメッセージクラスの生成
        this.demandType = demandType;
        this.username = username;
    }
}
