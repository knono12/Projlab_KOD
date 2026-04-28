package accessories.fuels;

import players.Cleaner;
import players.BusDriver;
import skeleton.SkeletonManager;

public class Gravel extends Fuel {
    
    /**
     * A BioKerosene osztály konstruktora.
     * 
     * @param amount A kezdeti biokerozin mennyiség.
     * @param sName  Az azonosító név.
     * @param price  A biokerozin ára a boltban.
     */
    public Gravel(int amount, String sName, int price) {
        super(amount, sName);
        setPrice(price);
    }

    /**
     * A takarító megvásárolja a biokerozint.
     * Ennek hatására a biokerozin bekerül a takarító eszköztárába (inventory).
     * 
     * @param c A vásárlást végző takarító játékos.
     */
    @Override
    public void boughtByCleaner(Cleaner c) {
        SkeletonManager.call(getSName() + ".boughtByCleaner(" + c.getSName() + ")");
        c.addToInventory(this);
        SkeletonManager.ret("void");
    }

    /**
     * A buszsofőr általi vásárlás metódusa (a Purchasable interfész miatt
     * kötelező).
     * Mivel buszsofőr nem vesz biokerozint, a metódus törzse üres.
     * 
     * @param b A vásárlást megkísérlő buszsofőr.
     */
    @Override
    public void boughtByBusDriver(BusDriver b) {
    }
}
