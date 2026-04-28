package environment.lane.lanestates;

import environment.lane.Lane;
import vehicles.Bus;
import vehicles.Car;

/**
 * A tiszta sávot reprezentáló állapot.
 * Ebben az állapotban az útszakasz mindenki számára biztonságosan járható. 
 * Mivel nincs rajta hó vagy jég, a takarító eszközök (söprű, jégtörő, stb.) használata hatástalan.
 */
public class ClearState extends LaneState {
    /**
     * Konstruktor a tiszta állapot létrehozásához.
     * @param l A sáv referenciája.
     * @param n Az állapot neve.
     */
    public ClearState(Lane l, String n) {
        super(l, n);
        l.setSnowThickness(0);
    }

    /**
     * Az időjárási hatások kezelése tiszta állapotban.
     * Ebben az állapotban a sáv havazás hatására {@link LightSnowyState} állapotba vált,
     * ha a hó vastagsága elért egy bizonyos szintet.
     */
    @Override
    public void snowLogic() {
        lane.snowBuildUp();

        if(lane.getSnowThickness() >= 3){
            lane.changeState(new LightSnowyState(lane, "lightSnowyState"));
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

    @Override
    public boolean salt() {
        lane.changeState(new SaltedState(lane, "saltedState"));
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
