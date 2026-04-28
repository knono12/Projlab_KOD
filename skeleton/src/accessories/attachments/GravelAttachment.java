package accessories.attachments;

import accessories.fuels.Gravel;
import environment.lane.Lane;
import players.BusDriver;
import players.Cleaner;
import skeleton.SkeletonManager;

public class GravelAttachment extends Attachment{
    Gravel gravel;

    /**
     * Konstruktor a zúzalékszóró fej létrehozásához.
     * 
     * @param sName A fej neve.
     * @param price A fej ára.
     */
    public GravelAttachment(String sName, int price) {
        super(sName);
        setPrice(price);
    }

    /**
     * Betölti a zúzalékot a zúzalészóró fejbe.
     * 
     * @param gravel A betöltendő só objektum.
     */
    public void setSalt(Gravel gravel) {
        this.gravel = gravel;
    }

    /**
     * Megkísérel zúzalékot fogyasztani, és ha sikerül, beszórja az utat.
     * 
     * @param l A tisztítandó sáv.
     * @return Igaz, ha volt elég zúzalék és a sáv sikeresen be lett sózva.
     */
    @Override
    public boolean clean(Lane l) {
        SkeletonManager.call(getSName() + ".clean(" + l.getSName() + ")");
        boolean success = false;
        if (gravel != null && gravel.consume()) {
            success = l.gravel();
        }
        SkeletonManager.ret(String.valueOf(success));
        return success;
    }

    /**
     * A takarító megvásárolja a sószóró fejet.
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
