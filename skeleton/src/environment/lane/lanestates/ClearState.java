package environment.lane.lanestates;

import environment.lane.Lane;
import skeleton.SkeletonManager;
import vehicles.Car;

/**
 * A tiszta sávot reprezentáló állapot.
 * Ebben az állapotban az útszakasz mindenki számára biztonságosan járható. 
 * Mivel nincs rajta hó vagy jég, a takarító eszközök (söprű, jégtörő, stb.) használata hatástalan.
 */
public class ClearState extends LaneState {
    /**
     * Konstruktor a tiszta állapot létrehozásához.
     * @param l A sáv referenciája.
     * @param n Az állapot neve.
     */
    public ClearState(Lane l, String n) {
        super(l, n);
    }

    /**
     * Az időjárási hatások kezelése tiszta állapotban.
     * Ebben az állapotban a sáv havazás hatására {@link LightSnowyState} állapotba vált.
     */
    @Override
    public void snowLogic() {
        SkeletonManager.call(sName + ".snowLogic()");

        lane.changeState(new LightSnowyState(lane, "lightSnowyState"));

        SkeletonManager.ret("void");
    }

    /**
     * Az autó akadály nélkül fel tud hajtani a sávra.
     * 
     * * @param c A belépni próbáló autó.
     * 
     * @return Mindig true.
     */
    @Override
    public boolean handleVehicle(Car c) {
        SkeletonManager.call(sName + ".handleVehicle(" + c.getSName() + ")");

        lane.getFromNode().leaveNode(c);
        lane.enterLane(c);

        SkeletonManager.ret("true");
        return true;
    }

    @Override
    public boolean salt() {
        SkeletonManager.call(sName + ".salt()");

        // Kérdés feltevés
        lane.changeState(new SaltedState(lane, "saltedState"));

        SkeletonManager.ret("true");
        return true;
    }

}
