package Message;

public class Certification extends Message {
	public String password;//パスワード
	public boolean result;//結果

	public Certification(String demandType, String username,String password ) { //認証メッセージクラスの生成
		this.demandType = demandType;
		this.username = username;
		this.password = password;
	}

}