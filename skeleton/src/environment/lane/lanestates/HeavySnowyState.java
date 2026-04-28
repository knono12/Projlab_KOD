package environment.lane.lanestates;

import environment.lane.Lane;
import skeleton.SkeletonManager;
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
        SkeletonManager.call(sName + ".snowLogic()");

        // Nem csinál semmit, mert az nagyon havas sávon nem történik további hófelhalmozódás.

        SkeletonManager.ret("void");
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
        SkeletonManager.call(sName +".handleVehicle(" + c.getSName() + ")");

        boolean foundNewPath = c.recalculateRoute();
        if(!foundNewPath){
            c.stop();
        }

        SkeletonManager.ret("false");
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
}
