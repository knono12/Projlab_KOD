package accessories.attachments;

import accessories.fuels.Salt;
import environment.lane.Lane;
import players.BusDriver;
import players.Cleaner;
import skeleton.SkeletonManager;

/**
 * A sószóró fejet reprezentáló osztály.
 * Takarítás közben sót ({@link Salt}) fogyaszt. Ha a fogyasztás sikeres,
 * besózza az utat, így az (SaltedState) állapotba kerül, és egy ideig védett
 * lesz a hótól/jégtől.
 */
public class GritterAttachment extends Attachment {
    Salt salt;

    /**
     * Konstruktor a sószóró fej létrehozásához.
     * 
     * @param sName A fej neve.
     * @param price A fej ára.
     */
    public GritterAttachment(String sName, int price) {
        super(sName);
        setPrice(price);
    }

    /**
     * Betölti a sót a sószóró fejbe.
     * 
     * @param salt A betöltendő só objektum.
     */
    public void setSalt(Salt salt) {
        this.salt = salt;
    }

    /**
     * Megkísérel sót fogyasztani, és ha sikerül, besózza az utat.
     * 
     * @param l A tisztítandó (sózandó) sáv.
     * @return Igaz, ha volt elég só és a sáv sikeresen be lett sózva.
     */
    @Override
    public boolean clean(Lane l) {
        SkeletonManager.call(getSName() + ".clean(" + l.getSName() + ")");
        boolean success = false;
        if (salt != null && salt.consume()) {
            success = l.salt();
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
