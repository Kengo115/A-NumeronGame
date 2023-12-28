package Message;

public class MakeRoomMessage extends Message{
    public String username1;//ユーザ名1
    public String username2;//ユーザ名2

    public MakeRoomMessage(String demandType,String username1,String username2){ //部屋作成メッセージクラスの生成
        this.demandType = demandType;
        this.username1=username1;
        this.username2=username2;
    }
}
