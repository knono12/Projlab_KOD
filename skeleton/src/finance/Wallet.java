package finance;

import static application.Skeleton.call;
import static application.Skeleton.ret;

public class Wallet {
    String sName;
    
    int balance;

    public Wallet(String sName, int balance) {
        this.sName = sName;
        this.balance = balance;
    }
    
    public void deductMoney(int amount) {
        call("Wallet.deductMoney(" + amount + ")");

        ret("");
    }
    
    public void addMoney(int amount) {
        call("Wallet.addMoney(" + amount + ")");

        ret("");
    }

}
