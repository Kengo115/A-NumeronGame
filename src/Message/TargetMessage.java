package Message;

public class TargetMessage extends ItemMessage{
    public int targetNumber;//ターゲットの数字
    ItemMessage itemMessage;
    public TargetMessage(ItemMessage itemMessage, int targetNumber){ //ターゲットメッセージクラス(ItemMessageクラスの子クラス)の生成
        this.itemMessage = itemMessage;
        this.targetNumber = targetNumber;
    }
}
