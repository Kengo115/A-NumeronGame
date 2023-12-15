package Message;

public class LogoutMessage extends Message {

	public LogoutMessage(String demandType,String username) { //ログアウトメッセージクラスの生成
		this.demandType =demandType;
		this.username = username;
	}
}