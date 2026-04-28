package environment.lane.lanestates;

import environment.lane.Lane;
import vehicles.Bus;
import vehicles.Car;

/**
 * A sózott sávot reprezentáló állapot.
 * A besózott úton a takarítóeszközök már nem tudnak további tisztítást végezni,
 * cserébe egy ideig a hó sem marad meg rajta.
 */
public class SaltedState extends LaneState {
    private int saltDuration;
    /**
     * Konstruktor, amely összekapcsolja az állapotot a sávval.
     * * @param l A sáv referenciája.
     * 
     * @param n Az állapot neve.
     */
    public SaltedState(Lane l, String n) {
        super(l, n);
        l.setSnowThickness(0);
        saltDuration = 3;
    }

    /**
     * Az időjárási hatások kezelése felsózott állapotban.
     * Ebben az állapotban a sáv havazás hatására is felsózott állapotban marad.
     */
    @Override
    public void snowLogic() {
        saltDuration--;

        if(saltDuration == 0){
            lane.changeState(new ClearState(lane, "clearState"));
        }
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
        lane.getFromNode().leaveNode(c);
        lane.enterLane(c);

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
