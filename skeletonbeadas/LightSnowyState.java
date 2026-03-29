package environment.lane.lanestates;

import environment.lane.Lane;
import skeleton.SkeletonManager;
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
        SkeletonManager.call("LightSnowyState.snowLogic()");

        boolean enoughSnow = SkeletonManager.ask("Elég hó esett hogy nagyon havas sáv legyen? ");
        if(enoughSnow){
            lane.changeState(new HeavySnowyState(lane, "heavySnowState"));
        }

        SkeletonManager.ret("void");
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
        SkeletonManager.call(sName + ".handleVehicle(" + c.getSName() + ")");

        lane.getFromNode().leaveNode(c);
        lane.enterLane(c);

        boolean enoughCars = SkeletonManager.ask("Elég autó hajtott rá a sávra, hogy jeges legyen? ");
        if(enoughCars){
            lane.changeState(new IcyState(lane, "icyState"));
        }

        SkeletonManager.ret("true");
        return true;
    }

    /**
     * A havas úton a jégtörés hatástalan.
     * * @return Mindig false.
     */
    @Override
    public boolean brakeIce() {
        SkeletonManager.call(sName + ".brakeIce()");
        SkeletonManager.ret("false");
        return false;
    }

}
