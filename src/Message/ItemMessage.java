package Message;

public class ItemMessage extends Message{
    public String itemName;//アイテム名
    public String result;//結果
    public int targetNumber; //ターゲットナンバー

    public ItemMessage(String demandType,String username,String itemName,String result){ //アイテムメッセージクラスの生成
        this.demandType = demandType;
        this.username =username;
        this.itemName = itemName;
        this.result = result;
    }
    //ターゲット用コンストラクタ
    public ItemMessage(String demandType,String username,String itemName,String result, int targetNumber){
        this.demandType = demandType;
        this.username =username;
        this.itemName = itemName;
        this.result = result;
        this.targetNumber = targetNumber;
    }

    public ItemMessage() {

    }
}
