package lobbyServer.controller;

import java.util.List;

import databaseServer.controller.DataBaseController;

public class BattleRecordController {
	
	public List<Integer> getBattleRecord(String userName) {
        DataBaseController dataBaseController = new DataBaseController();
        
        dataBaseController.insertInitialValues(userName, 1000, 0, 0, 0);
        
        String getResultInformationQuery = "SELECT rate, winCount, loseCount, drawCount FROM UserList WHERE UserName = ?";
        
		return dataBaseController.executeQueryForGetResultInformation(getResultInformationQuery);

	}
	
}
