package applicationServer.entity;

import Message.*;

public abstract class Item {
    public abstract ItemMessage useItem(ItemMessage message);

}
