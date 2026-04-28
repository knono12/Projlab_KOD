package environment.lane.lanestates;

import environment.lane.Lane;
import skeleton.SkeletonManager;
import vehicles.Bus;
import vehicles.Car;
import vehicles.Snowplow;

/**
 * A sávok állapotát megvalósító absztrakt ősosztály.
 * Definiálja a takarítási műveletek és a járművekkel való interakciók
 * alapértelmezett viselkedését.
 * A leszármazott konkrét állapotok (pl. IcyState, ClearState) felülírják ezeket
 * a metódusokat a saját szabályaik szerint.
 */
public abstract class LaneState {
    protected Lane lane;
    protected String sName;

    /**
     * Konstruktor, amely összekapcsolja az állapotot a hozzá tartozó sávval.
     * * @param l A sáv, amely ezt az állapotot tárolja.
     * 
     * @param sName Az állapot azonosító neve.
     */
    protected LaneState(Lane l, String sName) {
        lane = l;
        this.sName = sName;
    }

    /**
     * Absztrakt metódus a havazás kezelésére.
     * 
     * A leszármazottak ebben a metódusban döntenek arról, hogy a havazás 
     * hatására történik-e állapotváltás, vagy növekszik-e a hóvastagság.
     */
    public abstract void snowLogic();
    
    /**
     * Absztrakt metódus az autó sávval való interkacióinak (pl. belépés, baleset) kezelésére.
     * * @param c A sávra lépő autó.
     * 
     * @return Igaz, ha a sikeresen végig tudott hajtani az autó a sávon.
     */
    public abstract boolean handleVehicle(Car c);

    /**
     * Kezeli a hókotró belépését és interakcióját a sávval.
     * Alapértelmezetten kiveszi a hókotrót a csomópontból, beteszi a sávba, majd
     * meghívja a hókotró aktuális fejének takarító metódusát.
     * * @param sp A sávra lépő hókotró.
     * 
     * @return Igaz, ha a művelet és a takarítás sikeres volt.
     */
    public boolean handleVehicle(Snowplow sp) {
        lane.getFromNode().leaveNode(sp);
        lane.enterLane(sp);
        boolean success = sp.getCurrentAttachment().clean(lane);

        return success;
    }

    /**
     * Absztrakt metódus egy busz sávra való belépésének és áthaladásának kezelésére.
     * A busz elhagyja a csomópontot, belép a sávba,
     * majd a sáv állapota dönt arról, hogy az áthaladás sikeres volt-e.
     * @param b A sávra lépő busz.
     * @return Igaz, ha a busz sikeresen áthaladt a sávon.
     */
    public abstract boolean handleVehicle(Bus b);

    /**
     * Söprés kezdeményezése az állapoton.
     * 
     * @param laneCount Sávok száma a hó jobbra tolásához.
     * @return Alapértelmezetten hamis (de a specifikus állapotok felülírják).
     */
    public boolean sweep(int laneCount) {
        return false;
    }

    /**
     * Jégtörés kezdeményezése az állapoton.
     * 
     * @return Alapértelmezetten hamis.
     */
    public boolean brakeIce() {
        return false;
    }

    /**
     * Sózás kezdeményezése az állapoton.
     * 
     * @return Alapértelmezetten hamis.
     */
    public boolean salt() {
        SkeletonManager.call(sName + ".salt()");
        SkeletonManager.ret("false");
        return false;
    }

    /**
     * hó/jég olvasztásának kezdeményezése az állapoton.
     * 
     * @return Alapértelmezetten hamis.
     */
    public boolean melt() {
        return false;
    }

    /**
     * Zúzalékszórás kezdeményezése az állapoton.
     * 
     * @return Alapértelmezetten hamis.
     */
    public boolean gravel(){
        return false;
    }

    /**
     * Visszaadja a sáv állapot nevét.
     * * @return A sáv állapotot azonosító név.
     */
    public String getSName(){
        return sName;
    }

    /**
     * Visszaadja hogy a sáv járható-e autók és buszok által
     * * @return igaz, ha járható, egyébként hamis
     */
    public abstract boolean isPassable();

    /**
     * Visszaadja hogy a sáv járható-e hókotrók által
     * * @return igaz, ha járható, egyébként hamis
     */
    public abstract boolean isPassableSnowplow();

}
