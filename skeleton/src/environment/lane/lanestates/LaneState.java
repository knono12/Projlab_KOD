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
        SkeletonManager.call(lane.getSName() + ".handleVehicle(" + sp.getSName() + ")");

        lane.getFromNode().leaveNode(sp);
        lane.enterLane(sp);
        boolean success = sp.getCurrentAttachment().clean(lane);

        SkeletonManager.ret(String.valueOf(success));
        return success;
    }

    /**
     * Kezeli egy busz belépését és áthaladását a sávon.
     * Alapértelmezett viselkedés: a busz elhagyja a csomópontot, belép a sávba,
     * majd a sáv állapota dönt arról, hogy az áthaladás sikeres volt-e.
     * Konkrét állapotok (pl. {@code AccidentState}) felülírhatják ezt a viselkedést,
     * hogy balesetkor megakadályozzák az áthaladást, vagy jeges úton csúszást okozzanak.
     * @param b A sávra lépő busz.
     * @return Igaz, ha a busz sikeresen áthaladt a sávon.
     */
    public boolean handleVehicle(Bus b) {
        SkeletonManager.call(lane.getSName() + ".handleVehicle(" + b.getSName() + ")");

        lane.getFromNode().leaveNode(b);
        lane.enterLane(b);

        SkeletonManager.ret("true");
        return true;
    }

    /**
     * Söprés kezdeményezése az állapoton.
     * 
     * @param laneCount Sávok száma a hó jobbra tolásához.
     * @return Alapértelmezetten hamis (de a specifikus állapotok felülírják).
     */
    public boolean sweep(int laneCount) {
        SkeletonManager.call(sName + ".sweep(" + laneCount + ")");
        SkeletonManager.ret("false");
        return false;
    }

    /**
     * Jégtörés kezdeményezése az állapoton.
     * 
     * @return Alapértelmezetten hamis.
     */
    public boolean brakeIce() {
        SkeletonManager.call(sName + ".brakeIce()");
        SkeletonManager.ret("false");
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
        SkeletonManager.call(sName + ".melt()");
        SkeletonManager.ret("false");
        return false;
    }

    /**
     * Zúzalékszórás kezdeményezése az állapoton.
     * 
     * @return Alapértelmezetten hamis.
     */
    public boolean gravel(){
        SkeletonManager.call(sName + ".gravel()");
        SkeletonManager.ret("false");
        return false;
    }

    /**
     * Visszaadja a sáv állapot nevét.
     * * @return A sáv állapotot azonosító név.
     */
    public String getSName(){
        return sName;
    }

}
