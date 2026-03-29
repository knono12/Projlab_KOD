package accessories.fuels;

import finance.Purchasable;
import skeleton.SkeletonManager;

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
     * Konstruktor, amely beállítja az induló mennyiséget és a nevet.
     * 
     * @param amount A kezdeti mennyiség.
     * @param sName  Az azonosító név.
     */
    public Fuel(int amount, String sName) {
        this.amount = amount;
        this.sName = sName;
    }

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

    /**
     * Megkísérel elfogyasztani egy egységnyi üzemanyagot a takarításhoz.
     * A szkeleton fázisban a felhasználótól kérdezi meg, hogy van-e elegendő
     * mennyiség.
     * 
     * @return Igaz, ha volt elég üzemanyag és a fogyasztás sikeres volt, különben
     *         hamis.
     */
    public boolean consume() {
        SkeletonManager.call(sName + ".consume()");
        boolean isEnoughAmont = SkeletonManager.ask("Van-e elég üzemanyag a takarításhoz? ");
        SkeletonManager.ret(String.valueOf(isEnoughAmont));
        return isEnoughAmont;
    }

}