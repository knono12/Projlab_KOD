package accessories.attachments;

import environment.lane.Lane;
import finance.Purchasable;

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
}
