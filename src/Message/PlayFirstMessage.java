package Message;

public class PlayFirstMessage extends Message  {
    // 追加されるフィールド
    public boolean playFirst;

    // CheckPlayFirstクラスのコンストラクタ
    public PlayFirstMessage(String demandType, String username) {
        this.demandType = demandType;
        this.username = username;
    }
}
