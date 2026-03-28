package environment.lane.lanestates;

import environment.lane.Lane;
import skeleton.SkeletonManager;
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
    }

    /**
     * Az időjárási hatások kezelése feltört jeges állapotban.
     * Ebben az állapotban a sáv havazás hatására is feltört jeges állapotban marad.
     */
    @Override
    public void snowLogic() {
        SkeletonManager.call("BrokenIceState.snowLogic()");

        // Nem csinál semmit, mert a feltört jeges sávon nem történik további hófelhalmozódás.

        SkeletonManager.ret("void");
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
        SkeletonManager.call(sName +".handleVehicle(" + c.getSName() + ")");

        boolean foundNewPath = c.recalculateRoute();
        if(!foundNewPath){
            c.stop();
        }

        SkeletonManager.ret("false");
        return false;
    }

    /**
     * A már feltört jeget nem lehet újra feltörni.
     * 
     * @return Mindig false, a jégtörés hatástalan.
     */
    @Override
    public boolean brakeIce() {
        SkeletonManager.call(sName + ".brakeIce()");
        SkeletonManager.ret("false");
        return false;
    }

    @Override
    public boolean sweep(int laneCount) {
        SkeletonManager.call(sName + ".sweep()");
        SkeletonManager.ret("false");
        return false;
    }

}
