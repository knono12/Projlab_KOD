package accessories.fuels;

public class Gravel extends Fuel {
    
    /**
     * A Gravel osztály konstruktora.
     * 
     * @param sName  Az azonosító név.
     * @param amount A kezdeti kavicsmennyiség.
     * @param price Az üzemanyag ára a boltban.
     */
    public Gravel(String sName, int amount, int price) {
        super(sName, amount, price);
    }
}
