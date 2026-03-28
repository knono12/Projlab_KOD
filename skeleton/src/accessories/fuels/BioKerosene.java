package accessories.fuels;

import players.Cleaner;
import players.BusDriver;
import skeleton.SkeletonManager;

/**
 * A biokerozint reprezentáló osztály, amely a {@link Fuel} osztályból
 * származik.
 * Ezt a fogyóeszközt a sárkányfej használja az utakon lévő hó / jég gyors
 * felolvasztásához.
 */
public class BioKerosene extends Fuel {

    /**
     * A BioKerosene osztály konstruktora.
     * 
     * @param amount A kezdeti biokerozin mennyiség.
     * @param sName  Az azonosító név.
     * @param price  A biokerozin ára a boltban.
     */
    public BioKerosene(int amount, String sName, int price) {
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
