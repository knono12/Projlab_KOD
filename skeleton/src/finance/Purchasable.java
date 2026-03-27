package finance;

import players.BusDriver;
import players.Cleaner;

public interface Purchasable {
    // no instance variable

    /**
     * Method to handle the purchase of an item by a cleaner, the implementation
     * depends on the item type.
     * 
     * @param cleaner The cleaner who is purchasing the item.
     */
    public void boughtByCleaner(Cleaner cleaner);

    /**
     * Method to handle the purchase of an item by a bus driver, the implementation
     * depends on the item type.
     * 
     * @param busDriver The bus driver who is purchasing the item.
     */
    public void boughtByBusDriver(BusDriver busDriver);

    //

    /**
     * Method to get the price of the item, the implementation depends on the item
     * type. Forcing the implementer class to have these methods, so that it
     * implements the Price instance variable.
     * 
     * @return The price of the item.
     */
    public int getPrice();

    /**
     * Method to set the price of the item, the implementation depends on the item
     * type. Forcing the implementer class to have these methods, so that it
     * implements the Price instance variable.
     * 
     * @param price The new price of the item.
     */
    public void setPrice(int price);

    /**
     * Method to get the name of the item in the skeleton, the implementation
     * depends on the item type.
     * 
     * @return The name of the item in the skeleton.
     */
    public String getSName();

    /**
     * Method to set the name of the item in the skeleton, the implementation
     * depends on the item type.
     * 
     * @param sName The new name of the item in the skeleton.
     * @return The new name of the item in the skeleton.
     */
    public String setSName(String sName);

}
