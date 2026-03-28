package accessories.attachments;

import environment.lane.Lane;
import players.BusDriver;
import players.Cleaner;
import skeleton.SkeletonManager;

/**
 * A jégtörő fejet reprezentáló osztály.
 * Képes a jeges utak (IcyState) feltörésére (BrokenIceState állapotba
 * hozására), de havon vagy már tiszta úton hatástalan.
 */
public class IceBrakerAttachment extends Attachment {

    /**
     * Konstruktor a jégtörő fej létrehozásához.
     * 
     * @param sName A fej neve.
     * @param price A fej ára.
     */
    public IceBrakerAttachment(String sName, int price) {
        super(sName);
        setPrice(price);
    }

    /**
     * Megkísérli feltörni a jeget az adott sávon.
     * 
     * @param l A tisztítandó sáv.
     * @return Igaz, ha az út jeges volt és a jégtörés sikeresen megtörtént.
     */
    @Override
    public boolean clean(Lane l) {
        SkeletonManager.call(getSName() + ".clean(" + l.getName() + ")");
        boolean success = l.brakeIce();
        SkeletonManager.ret(String.valueOf(success));
        return success;
    }

    /**
     * A takarító megvásárolja a jégtörő fejet.
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
