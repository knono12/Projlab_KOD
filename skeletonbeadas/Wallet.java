package finance;

import skeleton.SkeletonManager;

public class Wallet {
    String sName;
    
    int balance;
    
    /**
     * The constructor of the Wallet class
     * @param sName The name of the wallet in the skeleton.
     * @param balance The initial balance of the wallet.
     */
    public Wallet(String sName, int balance) {
        this.sName = sName;
        this.balance = balance;
    }
    
    /**
     * Method to get the name of the wallet in the skeleton.
     * @param amount The amount of money to deduct from the wallet.
     */
    public void deductMoney(int amount) {
        SkeletonManager.call("Wallet.deductMoney(" + amount + ")");

        balance -= amount;
        
        SkeletonManager.ret("");
    }
    
    /**
     * Method to add money to the wallet.
     * @param amount The amount of money to add to the wallet.
     */
    public void addMoney(int amount) {
        SkeletonManager.call("Wallet.addMoney(" + amount + ")");
        
        balance += amount;

        SkeletonManager.ret("");
    }

}
