package environment.lane.lanestates;

import environment.lane.Lane;
import skeleton.SkeletonManager;
import vehicles.Bus;
import vehicles.Car;

/**
 * Az enyhén havas sávot reprezentáló állapot.
 * Ezen még minden jármű tud haladni.
 */
public class LightSnowyState extends LaneState {
    int carsPassed;
    /**
     * Konstruktor, amely összekapcsolja az állapotot a sávval.
     * * @param l A sáv referenciája.
     * 
     * @param n Az állapot neve.
     */
    public LightSnowyState(Lane l, String n) {
        super(l, n);
        carsPassed = 0;
    }

    /**
     * Az időjárási hatások kezelése enyhén havas állapotban.
     * Havazás többszörös hatására {@link HeavySnowyState} állapotba vált.
     */
    @Override
    public void snowLogic() {
        lane.snowBuildUp();

        if(lane.getSnowThickness() >= 6){
            lane.changeState(new HeavySnowyState(lane, "heavySnowyState"));
        }
    }

    /**
     * Az autó akadály nélkül fel tud hajtani a sávra.
     * Autók többszörös áthajtásának hatására {@link IcyState} állapotba vált.
     * 
     * * @param c A belépni próbáló autó.
     * 
     * @return Mindig true.
     */
    @Override
    public boolean handleVehicle(Car c) {
        lane.getFromNode().leaveNode(c);
        lane.enterLane(c);
        carsPassed++;

        if(carsPassed >= 3){
            lane.changeState(new IcyState(lane, "icyState"));
        }
        return true;
    }

    /**
     * A busz akadály nélkül fel tud hajtani a sávra.
     * 
     * * @param b A belépni próbáló busz.
     * 
     * @return Mindig true.
     */
    @Override
    public boolean handleVehicle(Bus b) {
        lane.getFromNode().leaveNode(b);
        lane.enterLane(b);

        return true;
    }

    @Override
    public boolean sweep(int laneCount) {
        SkeletonManager.call(sName + ".sweep(" + laneCount + ")");

        lane.pushSnowRight(laneCount);
        lane.changeState(new ClearState(lane, "clearState"));

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

    @Override
    public boolean melt() {
        SkeletonManager.call(sName + ".melt()");

        lane.changeState(new ClearState(lane, "clearState"));

        SkeletonManager.ret("true");
        return true;
    }

    /**
     * Visszaadja hogy a sáv járható-e autók és buszok által
     * * @return mindig igaz
     */
    @Override
    public boolean isPassable(){
        return true;
    }

    /**
     * Visszaadja hogy a sáv járható-e hókotrók által
     * * @return mindig igaz
     */
    @Override
    public boolean isPassableSnowplow(){
        return true;
    }


}
