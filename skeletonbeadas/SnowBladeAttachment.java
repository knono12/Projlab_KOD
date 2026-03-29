package accessories.attachments;

import environment.lane.Lane;
import players.BusDriver;
import players.Cleaner;
import skeleton.SkeletonManager;

/**
 * A hányófejet reprezentáló osztály.
 * Ez a fej erősebb a sima söprűnél: 2 sávval jobbra tolja a havat, de ez is
 * csak Light/HeavySnowyState, BrokenIceState-n tud takarítani.
 */
public class SnowBladeAttachment extends Attachment {

    /**
     * Konstruktor a hányófej létrehozásához.
     * 
     * @param sName A fej neve.
     * @param price A fej ára.
     */
    public SnowBladeAttachment(String sName, int price) {
        super(sName);
        setPrice(price);
    }

    /**
     * Megkísérli letolni a havat a sávon (2 sávnyi eltolással).
     * 
     * @param l A tisztítandó sáv.
     * @return Igaz, ha a tolni lehetséges és sikeres volt.
     */
    @Override
    public boolean clean(Lane l) {
        SkeletonManager.call(getSName() + ".clean(" + l.getSName() + ")");
        boolean success = l.sweep(2);
        SkeletonManager.ret(String.valueOf(success));
        return success;
    }

    /**
     * A takarító megvásárolja a hányófejet.
     * 
     * @param c A vásárló takarító.
     */
    @Override
    public void boughtByCleaner(Cleaner c) {
        SkeletonManager.call(getSName() + ".boughtByCleaner(" + c.getSName() + ")");
        c.addToInventory(this);
        SkeletonManager.ret("void");
    }

    @Override
    public void boughtByBusDriver(BusDriver b) {
    }
}
