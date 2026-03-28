package environment.lane.lanestates;

import environment.lane.Lane;
import skeleton.SkeletonManager;
import vehicles.Car;
import vehicles.Vehicle;

public class LightSnowyState extends LaneState {
    /**
     * Konstruktor, amely összekapcsolja az állapotot a sávval.
     * @param l A sáv, amely ebben az állapotban van.
     */
    public LightSnowyState(Lane l) {
        super(l);
    }

    /**
     * Kezeli az autó interakcióját az enyhén havas sávval.
     * <p>
     * Az autó akadály nélkül fel tud hajtani a sávra.
     * </p>
     * @param c Az autó, amely a sávra szeretne hajtani.
     */
    @Override
    public void handleVehicle(Car c) {
        SkeletonManager.call("LightSnowyState.handleVehicle(Car)");

        lane.getFromNode().leaveNode(c);
        lane.enterLane(c);

        SkeletonManager.ret("void");
    }

    /**
     * Az időjárási hatások kezelése enyhén havas állapotban.
     * <p>
     * Ebben az állapotban a sáv havazás többszörös hatására {@link HeavySnowyState} állapotba vált.
     * </p>
     */
    @Override
    public void snowLogic() {
        SkeletonManager.call("LightSnowyState.snowLogic()");

        boolean enoughSnow = SkeletonManager.ask("Elég hó esett hogy nagyon havas sáv legyen? ");
        if(enoughSnow){
            lane.changeState(new HeavySnowyState(lane));
        }

        SkeletonManager.ret("void");
    }
}
