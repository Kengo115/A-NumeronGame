package applicationServer.entity;

import Message.*;
import applicationServer.controller.ApplicationController;

public class Target extends Item {
    String result;
    Player player;
    int digit = 4;
    @Override
    public ItemMessage useItem(ItemMessage message){
        player = ApplicationController.getPlayer(message.username);
        String[] opponentNumberArray = player.opponentNumber.split("");
        for(int i=0; i<opponentNumberArray.length; i++){
            int number = Integer.parseInt(opponentNumberArray[i]);
            if(number == message.targetNumber) digit = number;
        }

        if(digit == 0) result = "ターゲットナンバーは3桁目にあります.";
        else if(digit == 1) result = "ターゲットナンバーは2桁目にあります.";
        else if(digit == 2) result = "ターゲットナンバーは1桁目にあります.";
        else result = "ターゲットナンバーは使われていません.";

        ItemMessage itemMessage = new ItemMessage(message.demandType, message.username, message.itemName, result, message.targetNumber);
        return itemMessage;
    }

}
