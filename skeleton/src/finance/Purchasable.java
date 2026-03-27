package finance;

import players.BusDriver;
import players.Cleaner;

public interface Purchasable {
    // no instance variable

    void boughtByCleaner(Cleaner cleaner);

    void boughtByBusDriver(BusDriver busDriver);

    // forcing the implementer class to have these methods, so that it implements
    // the Price instance variable
    int getPrice();

    int setPrice(int price);
    
    // only for skeleton
    String getSName();

    String setSName(String sName);

}
