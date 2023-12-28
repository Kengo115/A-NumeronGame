package Message;

public class SetNumberMessage extends Message{
    public String setNumber;//設定ナンバー
    public boolean result;//先行か後攻かどうか

    public SetNumberMessage(String demandType,String username,String setNumber,boolean result){//セットナンバーのメッセージクラスの生成
        this.demandType = demandType;
        this.username = username;
        this.setNumber = setNumber;
        this.result = result;
    }
}
