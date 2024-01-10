package applicationServer.entity;

public class Player {
    String userName = null;
    boolean isItem;
    String myNumber = null;
    String opponentNumber = null;
    int turnOrder = 0; //0(先攻), 1(後攻)
    int EAT = 0; /**直前のEAT数を保存する*/
    int BITE = 0;

    boolean isWin = false;

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
    public void setIsWin(boolean isWin){
        this.isWin = isWin;
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
    public boolean getIsWin() { return isWin;}
}
