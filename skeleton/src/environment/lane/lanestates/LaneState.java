package environment.lane.lanestates;

import environment.lane.Lane;
import skeleton.SkeletonManager;
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
     * Söprés kezdeményezése az állapoton.
     * 
     * @param laneCount Sávok száma a hó jobbra tolásához.
     * @return Alapértelmezetten igaz (de a specifikus állapotok felülírják).
     */
    public boolean sweep(int laneCount) {
        SkeletonManager.call(sName + ".sweep(" + laneCount + ")");

        lane.pushSnowRight(laneCount);
        lane.changeState(new ClearState(lane, "clearState"));

        SkeletonManager.ret("true");
        return true;
    }

    /**
     * Jégtörés kezdeményezése az állapoton.
     * 
     * @return Alapértelmezetten igaz.
     */
    public boolean brakeIce() {
        SkeletonManager.call(sName + ".brakeIce()");

        lane.changeState(new BrokenIceState(lane, "brokenIceState"));

        SkeletonManager.ret("true");
        return true;
    }

    /**
     * Sózás kezdeményezése az állapoton.
     * 
     * @return Alapértelmezetten igaz.
     */
    public boolean salt() {
        SkeletonManager.call(sName + ".salt()");

        // Kérdés feltevés
        lane.changeState(new SaltedState(lane, "saltedState"));

        SkeletonManager.ret("true");
        return true;
    }

    /**
     * Olvasztás kezdeményezése az állapoton.
     * 
     * @return Alapértelmezetten igaz.
     */
    public boolean melt() {
        SkeletonManager.call(sName + ".melt()");

        lane.changeState(new ClearState(lane, "clearState"));

        SkeletonManager.ret("true");
        return true;
    }

    /**
     * Visszaadja a sáv állapot nevét.
     * * @return A sáv állapotot azonosító név.
     */
    public String getSName(){
        return sName;
    }

}
