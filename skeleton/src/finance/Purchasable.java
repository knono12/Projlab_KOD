package finance;

import players.BusDriver;
import players.Cleaner;

public interface Purchasable {
    public void boughtByCleaner(Cleaner c);
    public void boughtByBusDriver(BusDriver b);
}
