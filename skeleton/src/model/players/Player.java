package model.players;

import java.util.ArrayList;
import java.util.List;

import model.finance.Purchasable;
import model.finance.Wallet;

import observers.InventoryObserver;
import model.accessories.attachments.Attachment;
import model.accessories.fuels.Fuel;

/**
 * Az abstract Player osztály, amely a játékosok közös tulajdonságait és
 * viselkedését definiálja.
 */
public abstract class Player {
    protected String sName;

    protected List<Purchasable> inventory;

    protected String name;
    protected Wallet wallet;

    protected List<InventoryObserver> inventoryObservers = new ArrayList<>();

    /**
     * A Player osztály konstruktora, amely csak a skeletonbeli nevet és a játékos
     * nevét állítja be.
     * 
     * @param sName A játékos neve a skeletonban, amelyet naplózás és tesztelés
     *              céljából használnak.
     * @param name  A játékos neve.
     */
    protected Player(String sName, String name) {
        this.sName = sName;
        this.name = name;
        this.wallet = new Wallet();
        this.inventory = new ArrayList<>();
    }

    /**
     * A Player osztály konstruktora, amely a skeletonbeli nevet, a játékos nevét és
     * az készletét is beállítja.
     * 
     * @param sName     A játékos neve a skeletonban, amelyet naplózás és
     *                  tesztelés céljából használnak.
     * @param name      A játékos neve.
     * @param inventory A játékos készlete, amely a megvásárolt elemeket
     *                  tartalmazza.
     */
    protected Player(String sName, String name, List<Purchasable> inventory) {
        this.sName = sName;
        this.name = name;
        this.wallet = new Wallet();
        this.inventory = inventory;
    }

    /**
     * Hozzáad egy elemet a takarító készletéhez (inventory). Ha az elem egy fuel és
     * már van belőle a készletben, akkor növeli a meglévő mennyiséget. Ha az elem
     * egy attachment és már van belőle a készletben, akkor nem ad hozzá újat. Ez a
     * metódus a vásárlás logikáját kezeli, és frissíti a játékos pénzét is a
     * vásárlás után.
     * 
     * @param item Az elem, amelyet hozzá szeretne adni a készlethez.
     */
    public void addToInventory(Purchasable item) {
        Purchasable existingItem = hasItem(item);
        if (existingItem != null) {
            if (item instanceof Fuel newFuel && existingItem instanceof Fuel existingFuel) {
                wallet.deductMoney(item.getPrice());
                existingFuel.setAmount(existingFuel.getAmount() + newFuel.getAmount());
                notifyInventoryChanged();
                return;
            } else if (item instanceof Attachment) {
                // Ha az item egy Attachment, és már van ilyen típusú item a készletben, akkor
                // nem adunk hozzá újat
                return;
            }
        }

        inventory.add(item);
        wallet.deductMoney(item.getPrice());

        notifyInventoryChanged();
    }


    public Purchasable hasItem(Purchasable item) {
        for (Purchasable invItem : inventory) {
            if (invItem.getClass().equals(item.getClass())) {
                return invItem;
            }
        }
        return null;
    }

    /**
     * Metódus a pénz fogadására, amelynek implementációja a konkrét játékos
     * típusától függ.
     * 
     * @param amount A jóváírt pénz összege.
     */
    public void receiveMoney(int amount) {
        wallet.addMoney(amount);
    }

    /**
     * Absztrakt metódus egy elem vásárlására, amelynek implementációja a konkrét
     * játékos típusától függ.
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

    public void addInventoryObserver(InventoryObserver o) {
        if (!inventoryObservers.contains(o)) {
            inventoryObservers.add(o);
        }
    }

    protected void notifyInventoryChanged() {
        for (InventoryObserver o : inventoryObservers) {
            o.onInventoryChanged();
        }
    }
}
