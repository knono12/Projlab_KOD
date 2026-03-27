package vehicles;

import java.util.List;
import skeleton.SkeletonManager;
import players.BusDriver;
import finance.Purchasable;
import players.Cleaner;

public class Bus extends Vehicle implements Purchasable {
    int price;

    public void boughtByBusDriver(BusDriver b) {
        SkeletonManager.call(sName + ".boughtByBusDriver(" + b.getSName() + ")");
        
        b.addBus(this);
        
        SkeletonManager.ret("");
    }

    public void boughtByCleaner(Cleaner c) {
        SkeletonManager.call(sName + ".boughtByCleaner(" + c.getSName() + ")");

        SkeletonManager.ret("");
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
    
}
