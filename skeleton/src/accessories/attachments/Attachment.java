package accessories.attachments;

import environment.lane.Lane;
import finance.Purchasable;

/**
 * A hókotróra szerelhető takarítófejeket reprezentáló absztrakt ősosztály.
 * A fejek határozzák meg, hogy a hókotró milyen módon próbálja megtisztítani az adott sávot.
 * A fejek megvásárolhatók ({@link Purchasable}).
 */
public abstract class Attachment implements Purchasable{
    protected String name;

    /**
     * Konstruktor a név beállításához.
     * @param n A takarítófej neve.
     */
    public Attachment(String n){
        name = n;
    }

    /**
     * Végrehajtja a fejre jellemző takarítási logikát a megadott sávon.
     * @param l A sáv, amelyet meg kell tisztítani.
     * @return Igaz, ha a takarítás sikeres volt, különben hamis.
     */
    public abstract boolean clean(Lane l);

    /**
     * Visszaadja a takarítófej nevét.
     * @return A fej azonosító neve.
     */
    public String getName(){
        return name;
    }
}
