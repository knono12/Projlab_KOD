package model.finance;

import java.util.ArrayList;
import java.util.List;
import observers.WalletObserver;

/**
 * A pénztárca osztály, amely a játékosok pénzügyi tranzakcióit kezeli.
 */
public class Wallet {
    private int balance;
    private List<WalletObserver> observers = new ArrayList();
    
    /**
     * A Wallet osztály konstruktora.
     */
    public Wallet() {
        this.balance = 0;
    }
    
    /**
     * A Wallet osztály konstruktora, amely lehetővé teszi a kezdeti egyenleg beállítását.
     * @param balance A kezdeti egyenleg, amelyet a pénztárca tartalmaz. Nem lehet negatív érték.
     */
    public Wallet(int balance) {
        this.balance = balance;
    }
    
    /**
     * Egy bizonyos összeg levonása a pénztárcából.
     * @param amount A levonandó összeg.
     */
    public void deductMoney(int amount) {
        balance -= amount;
        notifyWalletChanged();
    }
    
    /**
     * Egy bizonyos összeg hozzáadása a pénztárcához.
     * @param amount A hozzáadandó összeg.
     */
    public void addMoney(int amount) {
        balance += amount;
        notifyWalletChanged();
    }


    /**
     * Visszaadja a pénztárca aktuális egyenlegét.
     * @return A pénztárca egyenlege.
     */
    public int getBalance() {
        return balance;
    }
    
    /**
     * Beállítja a pénztárca egyenlegét. Ha a megadott egyenleg negatív, kivételt dob.
     * @param balance Az új egyenleg.
     * @return A beállított egyenleg.
     */
    public int setBalance(int balance) {
        if(balance < 0) {
            throw new IllegalArgumentException("Balance cannot be negative");
        }
        this.balance = balance;
        return this.balance;
    }

    public void addObserver(WalletObserver o){
        if (!observers.contains(o)){
            observers.add(o);
            o.onWalletChanged(balance);
        }
    }

    public void notifyWalletChanged(){
        for(WalletObserver o : observers){
            o.onWalletChanged(balance);
        }
    }
}
