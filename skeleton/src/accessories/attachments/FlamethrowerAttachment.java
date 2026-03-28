package accessories.attachments;

import accessories.fuels.BioKerosene;
import environment.lane.Lane;
import players.BusDriver;
import players.Cleaner;
import skeleton.SkeletonManager;

/**
 * A sárkányfejet reprezentáló osztály.
 * Takarítás közben biokerozint ({@link BioKerosene}) fogyaszt. Képes azonnal
 * felolvasztani a jeget és a havat, amivel a sávot ClearState állapotba
 * juttatja.
 */
public class FlamethrowerAttachment extends Attachment {
    BioKerosene bioKerosene;

    /**
     * Konstruktor a sárkányfej létrehozásához.
     * 
     * @param sName A fej neve.
     * @param price A fej ára.
     */
    public FlamethrowerAttachment(String sName, int price) {
        super(sName);
        setPrice(price);
    }

    /**
     * Betölti a biokerozint a fejbe.
     * 
     * @param bioKerosene A betöltendő biokerozin objektum.
     */
    public void setBioKerosene(BioKerosene bioKerosene) {
        this.bioKerosene = bioKerosene;
    }

    /**
     * Megkísérel biokerozint fogyasztani, és ha sikerül, felolvasztja a
     * havat/jeget.
     * 
     * @param l A tisztítandó sáv.
     * @return Igaz, ha volt elég üzemanyag és a sáv sikeresen tiszta lett.
     */
    @Override
    public boolean clean(Lane l) {
        SkeletonManager.call(getSName() + ".clean(" + l.getName() + ")");

        boolean success = false;
        if (bioKerosene != null && bioKerosene.consume()) {
            success = l.melt();
        }
        SkeletonManager.ret(String.valueOf(success));
        return success;
    }

    /**
     * A takarító megvásárolja a sárkányfejet.
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