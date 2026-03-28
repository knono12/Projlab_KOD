package environment.lane.lanestates;

import environment.lane.Lane;
import skeleton.SkeletonManager;
import vehicles.Bus;
import vehicles.Snowplow;

/**
 * A sávok állapotát megvalósító absztrakt ősosztály.
 * Definiálja a takarítási műveletek és a járművekkel való interakciók alapértelmezett viselkedését. 
 * A leszármazott konkrét állapotok (pl. IcyState, ClearState) felülírják ezeket a metódusokat a saját szabályaik szerint.
 */
public abstract class LaneState {
    protected Lane lane;
    protected String name;

    /**
     * Konstruktor, amely összekapcsolja az állapotot a hozzá tartozó sávval.
     * * @param l A sáv, amely ezt az állapotot tárolja.
     * @param name Az állapot azonosító neve.
     */
    public LaneState(Lane l, String name){
        lane = l;
        this.name = name;
    }

    /**
     * Kezeli a hókotró belépését és interakcióját a sávval.
     * Alapértelmezetten kiveszi a hókotrót a csomópontból, beteszi a sávba, majd meghívja a hókotró aktuális fejének takarító metódusát.
     * * @param sp A sávra lépő hókotró.
     * @return Igaz, ha a művelet és a takarítás sikeres volt.
     */
    public boolean handleVehicle(Snowplow sp){
        SkeletonManager.call(lane.getName() + ".handleVehicle(" + sp.getName() + ")");

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
        SkeletonManager.call(lane.getName() + ".handleVehicle(" + b.getName() + ")");

        lane.getFromNode().leaveNode(b);
        lane.enterLane(b);

        SkeletonManager.ret("true");
        return true;
    }

    /**
     * Söprés kezdeményezése az állapoton.
     * @param laneCount Sávok száma a hó jobbra tolásához.
     * @return Alapértelmezetten igaz (de a specifikus állapotok felülírják).
     */
    public boolean sweep(int laneCount){
        SkeletonManager.call(name + ".sweep(" + laneCount + ")");

        lane.pushSnowRight(laneCount);
        lane.changeState(new ClearState(lane, "clearState"));
        
        SkeletonManager.ret("true");
        return true;
    }

    /**
     * Jégtörés kezdeményezése az állapoton.
     * @return Alapértelmezetten igaz.
     */
    public boolean brakeIce(){
        SkeletonManager.call(name + ".brakeIce()");

        lane.changeState(new BrokenIceState(lane, "brokenIceState"));

        SkeletonManager.ret("true");
        return true;
    }

    /**
     * Sózás kezdeményezése az állapoton.
     * @return Alapértelmezetten igaz.
     */
    public boolean salt(){
        SkeletonManager.call(name + ".salt()");

        ////Kérdés feltevés
        lane.changeState(new SaltedState(lane, "saltedState"));

        SkeletonManager.ret("true");
        return true;
    }

    /**
     * Olvasztás kezdeményezése az állapoton.
     * @return Alapértelmezetten igaz.
     */
    public boolean melt(){
        SkeletonManager.call(name + ".melt()");

        lane.changeState(new ClearState(lane, "clearState"));

        SkeletonManager.ret("true");
        return true;
    }

    /**
     * Visszaadja a sáv állapot nevét.
     * * @return A sáv állapotot azonosító név.
     */
    public String getName(){
        return name;
    }

}