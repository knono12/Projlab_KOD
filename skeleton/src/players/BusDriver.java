package players;

import java.util.ArrayList;
import java.util.List;

import finance.Purchasable;
import skeleton.SkeletonManager;
import vehicles.Bus;
import vehicles.Snowplow;

/**
 * A buszsofőrt reprezentáló osztály.
 * A buszsofőr buszokat vásárolhat és vezet, körök teljesítéséért pénzt kap.
 * Emellett tárgyakat ({@link Purchasable}) vásárolhat és tárolhat az inventoryjában,
 * valamint hókotrót ({@link Snowplow}) is kezelhet.
 */
public class BusDriver extends Player{
    /** A buszsofőr neve (kiíratáshoz). */
    private String name;
    /** A sofőr pénzegyenlege. Kezdőérték: 500. */
    private int money;
    /** A sofőr által vásárolt tárgyak listája. */
    private List<Purchasable> inventory;
    /** A sofőr által tulajdonolt buszok listája. */
    private List<Bus> ownedBuses;
    /** A sofőr aktuálisan vezetett busza. */
    private Bus currentBus;

    /**
     * A BusDriver osztály konstruktora.
     * @param name A buszsofőr neve.
     */
    public BusDriver(String sName, String name) {
        super(sName, name);
    }

    /**
     * A BusDriver osztály konstruktora, amely a skeletonbeli nevet, a játékos nevét és a készletét is beállítja.
     * 
     * @param sName  Az egyedi név, amelyet naplózás és tesztelés céljából
     *               használnak.
     * @param name   A játékos neve.
     * @param inventory A játékos készlete, amely a megvásárolt elemeket tartalmazza.
     */
    public BusDriver(String sName, String name, List<Purchasable> inventory) {
        super(sName, name, inventory);
    }

    /**
     * Visszaadja a buszsofőr nevét.
     * @return A sofőr neve.
     */
    public String getName() {
        return name;
    }

    /**
     * 50 egység jutalmat fizet a sofőrnek.
     * 
     */
    public void roundCompletedByBus() {
        receiveMoney(50);
    }

    /**
     * Pénzt fogad (pl. körteljesítési jutalom).
     * @param amount A kapott pénzmennyiség.
     */
    public void receiveMoney(int amount) {
        money += amount;
    }

    /**
     * Hozzáad egy tárgyat a sofőr inventoryjához.
     * @param p A hozzáadandó tárgy.
     */
    public void addToInventory(Purchasable p) {
        inventory.add(p);
    }

    /**
     * Buszt rendel a sofőrhöz.
     * @param s A hozzárendelendő busz.
     */
    public void addBus(Bus b) {
        ownedBuses.add(b);
    }

    /**
     * Megvásárol egy buszt 200 egység pénzért, és a sofőr tulajdonába veszi.
     * Ha nincs elég pénz, a vásárlás sikertelen.
     * @param bus A megvásárolni kívánt busz.
     */
    public void buyBus(Bus bus) {
        int price = bus.getPrice();
        if (money >= price) {
            money -= price;
            ownedBuses.add(bus);
        }
    }

    /**
     * Beállítja az aktuálisan vezetett buszt, ha a sofőr tulajdonában van.
     * @param bus A beállítandó busz.
     */
    public void setCurrentBus(Bus bus) {
        if (ownedBuses.contains(bus) || (bus.getDriver() != null && bus.getDriver() == this)) {
            currentBus = bus;
        }
    }

    /**
     * Visszaadja a sofőr aktuális pénzegyenlegét.
     * @return A pénzmennyiség.
     */
    public int getMoney() {
        return money;
    }

    /**
     * Visszaadja a sofőr tulajdonában lévő buszokat.
     * @return A buszok listája.
     */
    public List<Bus> getBuses() {
        return ownedBuses;
    }
    /**
     * Elem vásárlására szolgáló metódus, amely a buszvezető pénztárcájából levonja az elem árát, majd meghívja az elem boughtByCleaner metódusát, és hozzáadja az elemet a készlethez.
     * @param item Az elem, amelyet vásárolni szeretne.
     */
    @Override
    public void purchaseItem(Purchasable item) {
        wallet.deductMoney(item.getPrice());
        item.boughtByBusDriver(this);
        addToInventory(item);
    }
}
