package Message;

public class ResultMessage extends Message{
    public String username1;//ユーザー1
    public String username2;//ユーザー2
    public String winUser;//勝利したユーザー(引き分けなら"draw")

    public ResultMessage(String demandType,String winUser,String username1,String username2){//結果メッセージクラスの生成
        this.demandType = demandType;
        this.username1 = username1;
        this.username2 = username2;
        this.winUser = winUser;
    }
}
