package players;

import java.util.ArrayList;
import java.util.List;

import finance.Purchasable;
import finance.Wallet;

/**
 * Az abstract Player osztály, amely a játékosok közös tulajdonságait és viselkedését definiálja.
 */
public abstract class Player {
    protected String sName;
    
    protected List<Purchasable> inventory;

    protected String name;
    protected Wallet wallet;
    
    /**
     * A Player osztály konstruktora, amely csak a skeletonbeli nevet és a játékos nevét állítja be.
    * 
    * @param sName A játékos neve a skeletonban, amelyet naplózás és tesztelés céljából használnak.
    * @param name  A játékos neve.
    */
    protected Player(String sName, String name) {
        this.sName = sName;
        this.name = name;
        this.wallet = new Wallet();
        this.inventory = new ArrayList<>();
    }

    /**
     * A Player osztály konstruktora, amely a skeletonbeli nevet, a játékos nevét és az készletét is beállítja.
     * 
     * @param sName  A játékos neve a skeletonban, amelyet naplózás és
     *               tesztelés céljából használnak.
     * @param name   A játékos neve.
     * @param inventory A játékos készlete, amely a megvásárolt elemeket tartalmazza.
     */
    protected Player(String sName, String name, List<Purchasable> inventory) {
        this.sName = sName;
        this.name = name;
        this.wallet = new Wallet();
        this.inventory = inventory;
    }
    
    /**
    * Hozzáad egy elemet a takarító készletéhez (inventory).
    * 
    * @param item Az elem, amelyet hozzá szeretne adni a készlethez.
    */
    public void addToInventory(Purchasable item) {
        inventory.add(item);
    }
    
    /**
     * Metódus a pénz fogadására, amelynek implementációja a konkrét játékos típusától függ.
     * 
     * @param amount A jóváírt pénz összege.
     */
    public void receiveMoney(int amount){
        wallet.addMoney(amount);
    }
    
    /**
     * Absztrakt metódus egy elem vásárlására, amelynek implementációja a konkrét játékos típusától függ.
     * 
     * @param item Az elem, amelyet vásárolni szeretne.
     */
    public abstract void purchaseItem(Purchasable item);
    
    /**
     * Metódus a játékos egyedi nevének lekérésére.
     * 
     * @return A játékos egyedi neve.
     */
    public String getSName() {
        return sName;
    }

    /**
     * Metódus a játékos egyedi nevének beállítására.
     * 
     * @param sName Az új egyedi név.
     * @return Az új egyedi név.
     */
    public String setSName(String sName) {
        this.sName = sName;
        return this.sName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
