package accessories.fuels;

import players.BusDriver;
import players.Cleaner;
import skeleton.SkeletonManager;

/**
 * A sót reprezentáló osztály, amely a {@link Fuel} osztályból származik.
 * Ezt a fogyóeszközt a sószóró fej használja az utak besózásához.
 */
public class Salt extends Fuel {

    protected int price;

    /**
     * A Salt osztály konstruktora.
     * @param amount A kezdeti sómennyiség.
     * @param name Az azonosító név.
     * @param price A só egységára a boltban.
     */
    public Salt(int amount, String name, int price){
        super(amount, name);
        this.price = price;
    }

    /**
     * A takarító (Cleaner) megvásárolja a sót.
     * Ennek hatására a só bekerül a takarító eszköztárába (inventory).
     * @param c A vásárlást végző takarító játékos.
     */
    @Override
    public void boughtByCleaner(Cleaner c) {
        SkeletonManager.call(name + ".boughtByCleaner(" + c.getName() + ")");
        c.addToInventory(this);
        SkeletonManager.ret("void");
    }

    /**
     * A buszsofőr általi vásárlás metódusa (a Purchasable interfész miatt kötelező).
     * Mivel buszsofőr nem vesz sót, a metódus törzse üres.
     * @param b A vásárlást megkísérlő buszsofőr.
     */
    @Override
    public void boughtByBusDriver(BusDriver b) {}
}
