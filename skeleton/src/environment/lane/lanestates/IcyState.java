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
        SkeletonManager.call(sName + ".brakeIce()");

        lane.changeState(new BrokenIceState(lane, "brokenIceState"));

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

    @Override
    public boolean gravel(){
        SkeletonManager.call(sName + ".gravel()");

        lane.changeState(new GravelState(lane, "gravelState"));

        SkeletonManager.ret("true");
        return true;
    }
}


