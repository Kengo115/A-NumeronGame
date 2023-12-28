package applicationServer.entity;

public class Player {
    String userName;
    boolean isItem;
    String myNumber;
    String opponentNumber;
    int turnOrder; //0(先攻), 1(後攻)
    int EAT; /**直前のEAT数を保存する*/
    int BITE;

    public void setUserName(String userName){
        this.userName = userName;
    }
    public void setMyNumber(String myNumber){
        this.myNumber = myNumber;
    }
    public void setOpponentNumber(String opponentNumber){
        this.opponentNumber = opponentNumber;
    }
    public void setTurnOrder(int turnOrder){
        this.turnOrder = turnOrder;
    }
    public void setEATAndBITE(int EAT, int BITE){
        this.EAT = EAT;
        this.BITE = BITE;
    }

    public String getUserName(){
        return userName;
    }
    public String getMyNumber(){
        return myNumber;
    }
    public int getEAT(){
        return EAT;
    }

    public  int getTurnOrder() { return  turnOrder;}
}
