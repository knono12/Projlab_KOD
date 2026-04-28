package environment.lane.lanestates;

import environment.lane.Lane;
import vehicles.Bus;
import vehicles.Car;

/**
 * Az erősen havas sávot reprezentáló állapot.
 * Ezen már busz és autó nem közlekedhet.
 */
public class HeavySnowyState extends LaneState {

    /**
     * Konstruktor, amely összekapcsolja az állapotot a sávval.
     * * @param l A sáv referenciája.
     * 
     * @param n Az állapot neve.
     */
    public HeavySnowyState(Lane l, String n) {
        super(l, n);
    }

    /**
     * Az időjárási hatások kezelése nagyon havas állapotban.
     * Ebben az állapotban a sáv már nem tud több havat befogadni, ezért a havazásna nincs hatása.
     */
    @Override
    public void snowLogic() {
        // Nem csinál semmit, mert az nagyon havas sávon nem történik további hófelhalmozódás.
    }

    /**
     * Megakadályozza az autó rálépését a nagyon havas sávra.
     * Mivel a sáv blokkolva van, az autó újratervez, ha ez sikeres következő ütemben elindul
     * az új útvonalon, ha nem, akkor lehúzódik.
     * * @param c A belépni próbáló autó.
     * 
     * @return Mindig false.
     */
    @Override
    public boolean handleVehicle(Car c) {
        boolean foundNewPath = c.recalculateRoute();
        if(!foundNewPath){
            c.stop();
        }
        
        return false;
    }

    /**
     * Megakadályozza a busz rálépését a nagyon havas sávra.
     * * @param b A belépni próbáló busz.
     * 
     * @return Mindig false.
     */
    @Override
    public boolean handleVehicle(Bus b) {
        return false;
    }

    @Override
    public boolean sweep(int laneCount) {
        lane.pushSnowRight(laneCount);
        lane.changeState(new ClearState(lane, "clearState"));

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

    /**
     * Visszaadja hogy a sáv járható-e autók és buszok által
     * * @return mindig hamis
     */
    @Override
    public boolean isPassable(){
        return false;
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
