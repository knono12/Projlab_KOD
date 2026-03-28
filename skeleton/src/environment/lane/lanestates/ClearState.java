package environment.lane.lanestates;

import environment.lane.Lane;
import skeleton.SkeletonManager;
import vehicles.Car;

/**
 * A tiszta sávot reprezentáló állapot.
 * <p>
 * Ebben az állapotban az útszakasz a mindenki számára járható.
 * A hóvastagság itt elérte a maximumot, így a további havazás nem változtat 
 * a sáv állapotán.
 * </p>
 */
public class ClearState extends LaneState {
    /**
     * Konstruktor, amely összekapcsolja az állapotot a sávval.
     * @param l A sáv, amely ebben az állapotban van.
     */
    public ClearState(Lane l) {
        super(l);
    }

    /**
     * Kezeli az autó interakcióját a tiszta sávval.
     * <p>
     * Az autó akadály nélkül fel tud hajtani a sávra.
     * </p>
     * @param c Az autó, amely a sávra szeretne hajtani.
     */
    @Override
    public void handleVehicle(Car c) {
        SkeletonManager.call("ClearState.handleVehicle(Car)");

        lane.getFromNode().leaveNode(c);
        lane.enterLane(c);

        SkeletonManager.ret("void");
    }

    /**
     * Az időjárási hatások kezelése tiszta állapotban.
     * <p>
     * Ebben az állapotban a sáv havazás hatására {@link LightSnowyState} állapotba vált.
     * </p>
     */
    @Override
    public void snowLogic() {
        SkeletonManager.call("ClearState.snowLogic()");

        lane.changeState(new LightSnowyState(lane));

        SkeletonManager.ret("void");
    }
}
