package applicationServer.entity;

import Message.ItemMessage;
import applicationServer.controller.ApplicationController;

public class Slash extends Item {
    String result = "";
    Player player = null;
    @Override
    public ItemMessage useItem(ItemMessage message){
        player = ApplicationController.getPlayer(message.username);
        String[] opponentNumberArray = player.opponentNumber.split("");
        int max = Integer.parseInt(opponentNumberArray[0]);
        int min = Integer.parseInt(opponentNumberArray[0]);
        for(int i = 1; i < opponentNumberArray.length; i++) {
            int digit = Integer.parseInt(opponentNumberArray[i]);
            if(digit > max) max = digit;
            else if(digit < min) min = digit;
        }
        int value = max-min;
        result = String.valueOf(value);
        ItemMessage itemMessage = new ItemMessage(message.demandType, message.username, message.itemName, result);
        return itemMessage;
    }

}
