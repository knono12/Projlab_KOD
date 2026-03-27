package finance;

import players.BusDriver;
import players.Cleaner;

public interface Purchasable {
    // no instance variable

    public void boughtByCleaner(Cleaner cleaner);

    public void boughtByBusDriver(BusDriver busDriver);

    // forcing the implementer class to have these methods, so that it implements
    // the Price instance variable
    public int getPrice();

    public int setPrice(int price);
    
    // only for skeleton
    public String getSName();

    public String setSName(String sName);


}
