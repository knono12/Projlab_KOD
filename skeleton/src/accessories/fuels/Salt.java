package accessories.fuels;

/**
 * A sót reprezentáló osztály, amely a {@link Fuel} osztályból származik.
 * Ezt a fogyóeszközt a sószóró fej használja az utak besózásához.
 */
public class Salt extends Fuel {

    /**
     * A Salt osztály konstruktora.
     * 
     * @param amount A kezdeti sómennyiség.
     * @param sName  Az azonosító név.
     * @param price  A só egységára a boltban.
     */
    public Salt(String sName, int amount, int price) {
        super(sName, amount, price);
    }
}
