package players;
import java.util.ArrayList;
import java.util.List;

import skeleton.SkeletonManager;
import finance.Purchasable;
import finance.Wallet;
import vehicles.Snowplow;

public class Cleaner extends Player {
    
    List<Purchasable> inventory;
    List<Snowplow> snowplows;
    
    /**
     * The constructor of the Cleaner class
     * @param sName The name of the cleaner in the skeleton, used for logging and testing purposes.
     * @param name The name of the  cleaner.
     * @param wallet The wallet of the cleaner.
     */
    public Cleaner(String sName, String name, Wallet wallet) {
        super(sName, name, wallet);
        this.inventory = new ArrayList<>();
        this.snowplows = new ArrayList<>();
    }
    
    /**
     * Method to receive money, the cleaner receives money when a snowplow successfully cleans a road.
     * @param amount The amount of money to receive.
     */
    @Override
    public void receiveMoney(int amount) {
        SkeletonManager.call("Cleaner.receiveMoney(" + amount + ")");

        Boolean answer = SkeletonManager.ask("Did the snowplow clean a road?");

        if (answer) {
            wallet.addMoney(amount);
        }

        SkeletonManager.ret("");
    }
    
    /**
     * Method to purchase an item, the cleaner can purchase items from the market.
     * @param item The item to purchase.
     */
    @Override
    public void purchaseItem(Purchasable item) {
        SkeletonManager.call("Cleaner.purchaseItem(" + item.getSName() + ")");

        wallet.deductMoney(item.getPrice());
        item.boughtByCleaner(this);
        addToInventory(item);

        SkeletonManager.ret("");
    }
    
    /**
     * Method to add an item to the cleaner's inventory.
     * @param item The item to add to the inventory.
     */
    public void addToInventory(Purchasable item) {
        SkeletonManager.call("Cleaner.addToInventory(" + item.getSName() + ")");

        inventory.add(item);

        SkeletonManager.ret("");
    }
    
    /**
     * Method to add a snowplow to the cleaner's list of snowplows.
     * @param snowplow The snowplow to add to the list of snowplows.
     */
    public void addSnowplow(Snowplow snowplow) {
        SkeletonManager.call("Cleaner.addSnowplow(" + snowplow.getSName() + ")");
        
        snowplows.add(snowplow);
        
        SkeletonManager.ret("");
    }
}
