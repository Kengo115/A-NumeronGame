package client.communication;

public class LogoutMessage extends Message {

	public LogoutMessage(String demandType) { //ログアウトメッセージクラスの生成
		this.demandType =demandType;
	}
}