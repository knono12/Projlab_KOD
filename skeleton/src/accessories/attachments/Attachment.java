package accessories.attachments;

import environment.lane.Lane;
import finance.Purchasable;
import players.BusDriver;
import players.Cleaner;

/**
 * A hókotróra szerelhető takarítófejeket reprezentáló absztrakt ősosztály.
 * A fejek határozzák meg, hogy a hókotró milyen módon próbálja megtisztítani az
 * adott sávot.
 * A fejek megvásárolhatók ({@link Purchasable}).
 */
public abstract class Attachment implements Purchasable {
    protected int price;
    protected String sName;

    /**
     * Konstruktor a név beállításához.
     * 
     * @param sName A takarítófej neve.
     */
    public Attachment(String sName) {
        this.sName = sName;
    }

    @Override
    public int getPrice() {
        return price;
    }

    @Override
    public void setPrice(int price) {
        this.price = price;
    }

    @Override
    public String getSName() {
        return sName;
    }

    @Override
    public String setSName(String sName) {
        this.sName = sName;
        return this.sName;
    }

    /**
     * Végrehajtja a fejre jellemző takarítási logikát a megadott sávon.
     * 
     * @param l A sáv, amelyet meg kell tisztítani.
     * @return Igaz, ha a takarítás sikeres volt, különben hamis.
     */
    public abstract boolean clean(Lane l);

    /**
     * A takarító megvásárolja a takarítófejet.
     * 
     * @param c A vásárlást végző takarító.
     */
    public void boughtByCleaner(Cleaner c) {
        c.addToInventory(this);
    }
    
    /**
     * A buszsofőr általi vásárlás metódusa.
     * 
     * @param b A vásárlást megkísérlő buszsofőr.
     */
    public void boughtByBusDriver(BusDriver b) {
        // Buszsofőr nem vásárol takarítófejet, így ez a metódus üres marad.
    }
}
