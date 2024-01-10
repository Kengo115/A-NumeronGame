package lobbyServer.controller;

import java.util.List;

import databaseServer.controller.DataBaseController;

public class BattleRecordController {
	
	public List<Integer> getBattleRecord(String userName) {
        DataBaseController dataBaseController = new DataBaseController();
        
        String getResultInformationQuery = "SELECT rate, winCount, loseCount, drawCount FROM UserList WHERE UserName = "+"'"+userName+"'";
        
		return dataBaseController.executeQueryForGetResultInformation(getResultInformationQuery);

	}
	
}
