package Message;

public class ItemMessage extends Message{
    public String itemName;//アイテム名
    public String result;//結果

    public ItemMessage(String demandType,String username,String itemName,String result){ //アイテムメッセージクラスの生成
        this.demandType = demandType;
        this.username = username;
        this.itemName = itemName;
    }

    public ItemMessage() {

    }
}
