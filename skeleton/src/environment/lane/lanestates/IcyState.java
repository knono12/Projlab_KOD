package environment.lane.lanestates;

import environment.lane.Lane;
import skeleton.SkeletonManager;
import vehicles.Car;

/**
 * A jeges sávot reprezentáló állapot.
 * Ezen az úton a járművek megcsúszhatnak és balesetet szenvedhetnek.
 * A jeget söpörni nem lehet, csak jégtörővel (brakeIce), sóval (salt) vagy
 * lángszóróval (melt) lehet megszüntetni.
 */
public class IcyState extends LaneState {

    /**
     * Konstruktor a jeges állapot létrehozásához.
     * 
     * @param l A sáv referenciája.
     * @param n Az állapot neve.
     */
    public IcyState(Lane l, String n) {
        super(l, n);
    }

    /**
     * Az időjárási hatások kezelése jeges állapotban.
     * Ebben az állapotban a sáv havazás hatására is jeges állapotban marad.
     */
    @Override
    public void snowLogic() {
        SkeletonManager.call("IcyState.snowLogic()");

        // Nem csinál semmit, mert a jeges sávon nem történik hófelhalmozódás.

        SkeletonManager.ret("void");
    }

    /**
     * Mivel a sáv jeges, az autó megcsúszik és elindul a baleset kiértékelés.
     * Ha nem történik baleset, akkor az autó végighajt a sávon.
     * Ha történik baleset, akkor az autó egy idő után elvontatásra kerül.
     * 
     * * @param c A belépni próbáló autó.
     * 
     * @return true, ha nincs baleset, false, ha történik.
     */
    @Override
    public boolean handleVehicle(Car c) {
        SkeletonManager.call(sName + ".handleVehicle(" + c.getSName() + ")");

        lane.getFromNode().leaveNode(c);
        lane.enterLane(c);

        c.slip();
        
        boolean success = !c.evaluateCollisions();        

        SkeletonManager.ret(String.valueOf(success));
        return success;
    }
    
    @Override
    public boolean brakeIce() {
        lane.changeState(new BrokenIceState(lane, "brokenIceState"));
        return true;
    }

    @Override
    public boolean salt() {
        lane.changeState(new SaltedState(lane, "saltedState"));
        return true;
    }

    @Override
    public boolean melt() {
        lane.changeState(new ClearState(lane, "clearState"));
        return true;
    }

    @Override
    public boolean gravel(){
        lane.changeState(new GravelState(lane, "gravelState"));
        return true;
    }
}


