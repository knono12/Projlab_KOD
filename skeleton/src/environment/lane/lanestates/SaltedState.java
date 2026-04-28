package environment.lane.lanestates;

import environment.lane.Lane;
import skeleton.SkeletonManager;
import vehicles.Car;

/**
 * A sózott sávot reprezentáló állapot.
 * A besózott úton a takarítóeszközök már nem tudnak további tisztítást végezni,
 * cserébe egy ideig a hó sem marad meg rajta.
 */
public class SaltedState extends LaneState {

    /**
     * Konstruktor, amely összekapcsolja az állapotot a sávval.
     * * @param l A sáv referenciája.
     * 
     * @param n Az állapot neve.
     */
    public SaltedState(Lane l, String n) {
        super(l, n);
    }

    /**
     * Az időjárási hatások kezelése felsózott állapotban.
     * Ebben az állapotban a sáv havazás hatására is felsózott állapotban marad.
     */
    @Override
    public void snowLogic() {
        SkeletonManager.call(sName + ".snowLogic()");

        boolean isExpired = SkeletonManager.ask("Lejárt már a só? ");
        if(isExpired){
            lane.changeState(new ClearState(lane, "clearState"));
        }

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

}
