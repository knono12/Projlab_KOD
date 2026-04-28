package environment.lane.lanestates;

import environment.lane.Lane;
import skeleton.SkeletonManager;
import vehicles.Bus;
import vehicles.Car;

public class GravelState extends LaneState{
    /**
     * Konstruktor, amely összekapcsolja az állapotot a sávval.
     * 
     * @param l A sáv, amely ebben az állapotban van.
     */
    public GravelState(Lane l, String n) {
        super(l, n);
    }

    @Override
    public void snowLogic() {
        lane.changeState(new LightSnowyState(lane, "lightSnowyState"));
    }

    @Override
    public boolean handleVehicle(Car c) {
        lane.getFromNode().leaveNode(c);
        lane.enterLane(c);

        return true;
    }

    @Override
    public boolean sweep(int laneCount) {
        SkeletonManager.call(sName + ".sweep(" + laneCount + ")");

        lane.pushSnowRight(laneCount);
        lane.changeState(new IcyState(lane, "icyState"));

        SkeletonManager.ret("true");
        return true;
    }
}
