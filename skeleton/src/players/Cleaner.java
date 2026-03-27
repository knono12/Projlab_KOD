package players;

import static application.Skeleton.ret;

import java.util.List;

import application.Skeleton;
import finance.Purchasable;
import finance.Wallet;

public class Cleaner extends Player {
    
    List<Purchasable> inventory;

    public Cleaner(String sName, String name, Wallet wallet) {
        super(sName, name, wallet);
    }
    
    @Override
    public void receiveMoney(int amount) {
        Skeleton.call("Cleaner.receiveMoney(" + amount + ")");

        Boolean answer = Skeleton.ask("Did the snowplow clean a road?");

        if(answer) {
            wallet.addMoney(amount);
        }

        ret("");
    }

    @Override
    public void purchaseItem(Purchasable item) {
        Skeleton.call("Cleaner.purchaseItem(" + item.getSName() + ")");
        
        wallet.deductMoney(item.getPrice());
        item.boughtByCleaner(this);
        addToInventory(item);
        
        ret("");
    }
    
    public void addToInventory(Purchasable item) {
        Skeleton.call("Cleaner.addToInventory(" + item.getSName() + ")");
        
        ret("");
    }
}
