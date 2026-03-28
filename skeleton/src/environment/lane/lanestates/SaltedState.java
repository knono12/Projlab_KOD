package environment.lane.lanestates;

import environment.lane.Lane;
import skeleton.SkeletonManager;
import vehicles.Car;

public class SaltedState extends LaneState {
    int saltDuration;

    /**
     * Konstruktor, amely összekapcsolja az állapotot a sávval.
     * @param l A sáv, amely ebben az állapotban van.
     */
    public SaltedState(Lane l) {
        super(l);
    }

    /**
     * Kezeli az autó interakcióját az felsózott sávval.
     * <p>
     * Az autó akadály nélkül fel tud hajtani a sávra.
     * </p>
     * @param c Az autó, amely a sávra szeretne hajtani.
     */
    @Override
    public void handleVehicle(Car c) {
        SkeletonManager.call("SaltedState.handleVehicle(Car)");

        lane.getFromNode().leaveNode(c);
        lane.enterLane(c);

        SkeletonManager.ret("void");
    }

    /**
     * Az időjárási hatások kezelése felsózott állapotban.
     * <p>
     * Ebben az állapotban a sáv havazás hatására is felsózott állapotban marad.
     * </p>
     */
    @Override
    public void snowLogic() {
        SkeletonManager.call("SaltedState.snowLogic()");

        boolean isExpired = SkeletonManager.ask("Lejárt már a só? ");
        if(isExpired){
            lane.changeState(new ClearState(lane));
        }

        SkeletonManager.ret("void");
    }
}
