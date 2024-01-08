package applicationServer.entity;

import Message.ItemMessage;
import applicationServer.controller.ApplicationController;

public class HighAndLow extends Item {
    String High = "HIGH";
    String Low = "LOW";
    String result;
    Player player;
    ApplicationController applicationController;
    @Override
    public ItemMessage useItem(ItemMessage message){
        //デバック
        System.out.println("HighAndLow.useItem到達");

        player = ApplicationController.getPlayer(message.username);
        String[] opponentNumberArray = player.opponentNumber.split("");

        //デバック
        System.out.println("相手のナンバー分割成功");

        for(int i = 0; i < opponentNumberArray.length; i++) {
            int digit = Integer.parseInt(opponentNumberArray[i]);

            // 数字が0〜4の範囲なら"Low"を追加
            if (digit >= 0 && digit <= 4) {
                result += Low;
            }
            // 数字が5〜9の範囲なら"High"を追加
            else if (digit >= 5 && digit <= 9) {
                result += High;
            }
        }
        ItemMessage itemMessage = new ItemMessage(message.demandType, message.username, message.itemName, result);

        //デバック
        System.out.println("アイテムメッセージ到達"+result);

        return itemMessage;
    }
}
