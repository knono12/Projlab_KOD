package accessories.fuels;

import finance.Purchasable;
import players.BusDriver;
import players.Cleaner;

/**
 * Az üzemanyagokat (só, biokerozin) reprezentáló absztrakt ősosztály.
 * A hókotró bizonyos fejei (sószóró, sárkányfej) takarítás közben üzemanyagot
 * fogyasztanak.
 * Ezek az elemek megvásárolhatóak (Purchasable), és a takarítók (Cleaner) az
 * inventory-jukban tárolják őket.
 */
public abstract class Fuel implements Purchasable {
    protected int amount;
    protected int price;
    protected String sName;

    /**
     * Konstruktor, amely beállítja az induló mennyiséget, nevet és árat.
     * 
     * @param amount A kezdeti mennyiség.
     * @param sName  Az azonosító név.
     * @param price  Az üzemanyag ára a boltban.
     */
    protected Fuel(String sName, int amount, int price) {
        this.sName = sName;
        this.amount = amount;
        this.price = price;
    }

    /**
     * Konstruktor, amely beállítja az árat és a nevet, de a kezdeti mennyiséget
     * 0-ra állítja.
     * 
     * @param sName Az azonosító név.
     * @param price Az üzemanyag ára a boltban.
     */
    protected Fuel(String sName, int price) {
        this.sName = sName;
        this.amount = 0;
        this.price = price;
    }

    /**
     * Megkísérel elfogyasztani egy egységnyi üzemanyagot a takarításhoz.
     * 
     * @return true, ha sikerült elfogyasztani az üzemanyagot (van elég), false egyébként.
     */
    public boolean consume() {
        if (amount > 0) {
            amount--;
            return true;
        }
        return false;
    }
    
    /**
     * A takarító megvásárolja az üzemanyagot.
     * Ennek hatására az üzemanyag bekerül a takarító eszkőztárába (inventory).
     * 
     * @param cleaner A vásárlást végző takarító játékos.
     */
    public void boughtByCleaner(Cleaner cleaner) {
        cleaner.addToInventory(this);
    }
    
    /**
    * A buszsofőr általi vásárlás metódusa (a Purchasable interfész miatt
    * kötelező).
    * Mivel buszsofőr nem vesz üzemanyagot, a metódus törzse üres.
    * 
    * @param busDriver A vásárlást megkísérlő buszsofőr.
    */
    public void boughtByBusDriver(BusDriver busDriver) {
        // Buszsofőr nem vásárol üzemanyagot, így ez a metódus üres marad.
    }
    
    // Getterek és setterek a Purchasable interfész metódusainak implementálásához.
    @Override
    public int getPrice() {
        return price;
    }
    
    @Override
    public void setPrice(int price) {
        this.price = price;
    }

    @Override
    public String getSName() {
        return sName;
    }

    @Override
    public String setSName(String sName) {
        this.sName = sName;
        return this.sName;
    }

    

}