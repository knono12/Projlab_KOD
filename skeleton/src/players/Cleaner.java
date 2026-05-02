package players;
import java.util.ArrayList;
import java.util.List;

import accessories.attachments.SnowBladeAttachment;
import skeleton.SkeletonManager;
import finance.Purchasable;
import finance.Wallet;
import vehicles.Snowplow;


/**
 * A Cleaner osztály, amely a Player osztályból származik, és a takarító játékosokat reprezentálja.
 * A takarító felelős az utak tisztításáért hókotrókkal, és különböző eszközöket vásárolhat a boltban.
 */
public class Cleaner extends Player {
    
    private List<Snowplow> snowplows;
    
    /**
     * A cleaner osztály konstruktora, amely a skeletonbeli nevet, a játékos nevét is beállítja.
     * 
     * @param sName  Az egyedi név, amelyet naplózás és tesztelés céljából
     *               használnak.
     * @param name   A játékos neve.
     */
    public Cleaner(String sName, String name) {
        super(sName, name);
        this.snowplows = new ArrayList<>();

        //snowplows.add(new Snowplow(0)); // TODO
        //inventory.add(new SnowBladeAttachment()); // TODO
    }
    
    /**
     * A cleaner osztály konstruktora, amely a skeletonbeli nevet, a játékos nevét és a készletét is beállítja.
     * 
     * @param sName  Az egyedi név, amelyet naplózás és tesztelés céljából
     *               használnak.
     * @param name   A játékos neve.
     * @param inventory A játékos készlete, amely a megvásárolt elemeket tartalmazza.
     */
    public Cleaner(String sName, String name, List<Purchasable> inventory) {
        super(sName, name, inventory);
        this.snowplows = new ArrayList<>();

        //snowplows.add(new Snowplow(0)); // TODO
    }
    
    /**
     * Pénzt fogadó metódus, amely a takarító pénztárcáját növeli a megadott összeggel.
     * @param amount A jóváírt pénz összege.
     */
    @Override
    public void receiveMoney(int amount) {
        wallet.addMoney(amount);
    }
    
    /**
     * Elem vásárlására szolgáló metódus, amely a takarító pénztárcájából levonja az elem árát, majd meghívja az elem boughtByCleaner metódusát, és hozzáadja az elemet a készlethez.
     * @param item Az elem, amelyet vásárolni szeretne.
     */
    @Override
    public void purchaseItem(Purchasable item) {
        wallet.deductMoney(item.getPrice());
        item.boughtByCleaner(this);
        addToInventory(item);
    }
    
    /**
     * Hozzáad egy hókotró járművet a takarító járműveihez.
     * @param snowplow A hozzáadni kívánt hókotró jármű.
     */
    public void addSnowplow(Snowplow snowplow) {
        snowplows.add(snowplow);
    }
}
