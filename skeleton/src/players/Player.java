package players;

import finance.Purchasable;
import finance.Wallet;

public abstract class Player {
    String sName;
    
    String name;
    Wallet wallet;
    
    protected Player(String sName, String name, Wallet wallet) {
        this.sName = sName;
        this.name = name;
        this.wallet = wallet;
    }
    
    public abstract void receiveMoney(int amount);

    public abstract void purchaseItem(Purchasable item);
}
