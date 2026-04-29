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
public class BusDriver {
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
    public BusDriver(String name) {
        this.name = name;
        this.money = 500;
        this.inventory = new ArrayList<>();
        this.ownedBuses = new ArrayList<>();
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
     * Megvásárol egy {@link Purchasable} tárgyat és az inventoryba helyezi.
     * @param item A megvásárolni kívánt tárgy.
     */
    public void purchase(Purchasable item) {
        SkeletonManager.call(name + ".purchase(item)");
        addToInventory(item);
        SkeletonManager.ret("void");
    }

    /**
     * Hozzáad egy tárgyat a sofőr inventoryjához.
     * @param p A hozzáadandó tárgy.
     */
    public void addToInventory(Purchasable p) {
        SkeletonManager.call(name + ".addToInventory(item)");
        inventory.add(p);
        SkeletonManager.ret("void");
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
        SkeletonManager.call(name + ".buyBus(bus)");

        if (money >= 200) {
            money -= 200;
            ownedBuses.add(bus);
            bus.setDriver(this);
            SkeletonManager.ret("void (successful, remaining: " + money + ")");
        } else {
            SkeletonManager.ret("void (insufficient funds, need: 200, have: " + money + ")");
        }
    }

    /**
     * Beállítja az aktuálisan vezetett buszt, ha a sofőr tulajdonában van.
     * @param bus A beállítandó busz.
     */
    public void setCurrentBus(Bus bus) {
        SkeletonManager.call(name + ".setCurrentBus(bus)");
        if (ownedBuses.contains(bus) || (bus.getDriver() != null && bus.getDriver() == this)) {
            currentBus = bus;
            SkeletonManager.ret("void (bus set)");
        } else {
            SkeletonManager.ret("void (bus not owned)");
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
}
