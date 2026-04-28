package environment.lane.lanestates;

import environment.lane.Lane;
import vehicles.Bus;
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
        l.setSnowThickness(0);
    }

    /**
     * Az időjárási hatások kezelése jeges állapotban.
     * Ebben az állapotban a sáv havazás hatására is jeges állapotban marad.
     */
    @Override
    public void snowLogic() {
        // Nem csinál semmit, mert a jeges sávon nem történik hófelhalmozódás.
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
        lane.getFromNode().leaveNode(c);
        lane.enterLane(c);

        return !c.slip();
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


