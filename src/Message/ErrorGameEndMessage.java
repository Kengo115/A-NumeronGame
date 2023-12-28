package Message;

public class ErrorGameEndMessage extends Message{
    public String errorUser;//異常終了したユーザ名
    public String normalUser;//対戦相手のユーザ名

    public ErrorGameEndMessage(String demandType,String errorUser,String normalUser){
        this.demandType = demandType;
        this.errorUser = errorUser;
        this.normalUser = normalUser;
    }
}
