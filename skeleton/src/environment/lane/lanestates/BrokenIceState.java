package environment.lane.lanestates;

import environment.lane.Lane;
import skeleton.SkeletonManager;
import vehicles.Bus;
import vehicles.Car;

/**
 * A feltört jeges sávot reprezentáló állapot.
 * Ez az állapot akkor jön létre, ha egy jeges ({@link IcyState}) utat
 * jégtörővel takarítanak.
 */
public class BrokenIceState extends LaneState {

    /**
     * Konstruktor, amely összekapcsolja az állapotot a sávval.
     * 
     * @param l A sáv, amely ebben az állapotban van.
     */
    public BrokenIceState(Lane l, String n) {
        super(l, n);
        l.setSnowThickness(0);
    }

    /**
     * Az időjárási hatások kezelése feltört jeges állapotban.
     * Ebben az állapotban a sáv havazás hatására is feltört jeges állapotban marad.
     */
    @Override
    public void snowLogic() {
        // Nem csinál semmit, mert a feltört jeges sávon nem történik további hófelhalmozódás.
    }

    /**
     * Megakadályozza az autó rálépését a feltört jeges sávra.
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
     * Megakadályozza a busz rálépését a feltört jeges sávra.
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
