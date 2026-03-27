package accessories.attachments;

import environment.lane.Lane;
import players.BusDriver;
import players.Cleaner;
import skeleton.SkeletonManager;

/**
 * A söprőfejet reprezentáló osztály.
 * Ez a fej 1 sávval jobbra tolja a havat.
 * Nem fogyaszt üzemanyagot, de csak havas állapotokon használható hatékonyan (Light/HeavySnowyState, BrokenIceState).
 */
public class SweeperAttachment extends Attachment{
    protected int price;

    /**
     * Konstruktor a söprőfej létrehozásához.
     * @param name A fej neve.
     * @param price A fej ára.
     */
    public SweeperAttachment(String name, int price){
        super(name);
        this.price = price;
    }

    /**
     * Megkísérli lesöpörni a havat a sávon (1 sávnyi eltolással).
     * @param l A tisztítandó sáv.
     * @return Igaz, ha a söprés lehetséges és sikeres volt.
     */
    @Override
    public boolean clean(Lane l){
        SkeletonManager.call(name + ".clean(" + l.getName() + ")");
        boolean success = l.sweep(1); 
        SkeletonManager.ret(String.valueOf(success));
        return success;
    }

    /**
     * A takarító megvásárolja a söprőfejet, és az bekerül az eszköztárába.
     * @param c A vásárló takarító.
     */
    @Override
    public void boughtByCleaner(Cleaner c) {
        SkeletonManager.call(name + ".boughtByCleaner(" + c.getName() + ")");
        c.addToInventory(this);
        SkeletonManager.ret("void");
    }

    @Override
    public void boughtByBusDriver(BusDriver b) {}
}
