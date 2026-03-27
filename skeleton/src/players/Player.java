package players;

import finance.Purchasable;
import finance.Wallet;

public abstract class Player {
    String sName;

    String name;
    Wallet wallet;

    /**
     * The constructor of the player class
     * 
     * @param sName  The name of the player in the skeleton, used for logging and
     *               testing purposes.
     * @param name   The name of the player.
     * @param wallet The wallet of the player.
     */
    protected Player(String sName, String name, Wallet wallet) {
        this.sName = sName;
        this.name = name;
        this.wallet = wallet;
    }

    /**
     * Method to receive money, the implementation depends on the player type.
     * 
     * @param amount The amount of money to receive.
     */
    public abstract void receiveMoney(int amount);

    /**
     * Method to purchase an item, the implementation depends on the player type.
     * 
     * @param item The item to purchase.
     */
    public abstract void purchaseItem(Purchasable item);

    /**
     * Method to get the name of the player in the skeleton.
     * 
     * @return The name of the player in the skeleton.
     */
    public String getSName() {
        return sName;
    }

    /**
     * Method to set the name of the player in the skeleton.
     * 
     * @param sName The new name of the player in the skeleton.
     * @return The new name of the player in the skeleton.
     */
    public String setSName(String sName) {
        this.sName = sName;
        return this.sName;
    }
}
