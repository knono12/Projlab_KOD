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

    /**
     * Egy tiszta sávot nem lehet tovább söpörni.
     * @return Mindig false, a takarítás sikertelen.
     */
    @Override
    public boolean sweep(int laneCount){
        SkeletonManager.call(sName + ".sweep(" + laneCount + ")");        
        SkeletonManager.ret("false");
        return false;
    }

    /**
     * Egy tiszta sávon nincs jég, amit fel lehetne törni.
     * @return Mindig false, a jégtörés sikertelen.
     */
    @Override
    public boolean brakeIce(){
        SkeletonManager.call(sName + ".brakeIce()");
        SkeletonManager.ret("false");
        return false;
    }

    // A sószóró végigmegy az úton besózza, és kap is érte pénzt. 
    // Azért lehet, hogy utána egy ideig nem eshet rá hó. 

    /**
     * Egy tiszta sávon nincs mit felolvasztani.
     * @return Mindig false, az olvasztás sikertelen.
     */
    @Override
    public boolean melt(){
        SkeletonManager.call(sName + ".melt()");
        SkeletonManager.ret("false");
        return false;
    }

}
