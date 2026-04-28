package finance;

import players.BusDriver;
import players.Cleaner;

/**
 * Interfész a megvásárolható tárgyak számára.
 * Minden vásárolható elem (busz, hókotró, üzemanyag, fej) implementálja.
 */
public interface Purchasable {

    /**
     * Kezeli a takarító általi vásárlást; a pontos viselkedés az elem típusától függ.
     *
     * @param cleaner A vásárlást végző takarító.
     */
    public void boughtByCleaner(Cleaner cleaner);

    /**
     * Kezeli a buszsofőr általi vásárlást; a pontos viselkedés az elem típusától függ.
     *
     * @param busDriver A vásárlást végző buszsofőr.
     */
    public void boughtByBusDriver(BusDriver busDriver);

    /**
     * Visszaadja az elem árát.
     *
     * @return Az elem ára.
     */
    public int getPrice();

    /**
     * Beállítja az elem árát.
     *
     * @param price Az új ár.
     */
    public void setPrice(int price);

    /**
     * Visszaadja az elem azonosító nevét.
     *
     * @return Az elem azonosító neve.
     */
    public String getSName();

    /**
     * Beállítja az elem azonosító nevét.
     *
     * @param sName Az új azonosító név.
     * @return A beállított azonosító név.
     */
    public String setSName(String sName);

}
