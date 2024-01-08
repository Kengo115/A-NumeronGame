package Message;

public class SetNumberMessage extends Message{
    public String setNumber;//設定ナンバー

    public SetNumberMessage(String demandType,String username,String setNumber){//セットナンバーのメッセージクラスの生成
        this.demandType = demandType;
        this.username = username;
        this.setNumber = setNumber;
    }
}
