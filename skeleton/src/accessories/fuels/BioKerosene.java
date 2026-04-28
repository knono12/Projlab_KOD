package accessories.fuels;

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
        super(sName, amount, price);
    }
}
