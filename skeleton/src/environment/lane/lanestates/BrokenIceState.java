package environment.lane.lanestates;

import environment.lane.Lane;
import skeleton.SkeletonManager;
import vehicles.Car;

/**
 * A feltört jeges sávot reprezentáló állapot.
 * <p>
 * Ebben az állapotban az útszakasz a normál forgalom számára 
 * járhatatlanná válik, így blokkolja az autók, buszok haladását, viszont a hókotró ráhajthat. 
 * A havazás nem változtat az állapoton, nem fedi el a feltört jeget.
 * </p>
 */
public class BrokenIceState extends LaneState {
    
    /**
     * Konstruktor, amely összekapcsolja az állapotot a sávval.
     * @param l A sáv, amely ebben az állapotban van.
     */
    public BrokenIceState(Lane l) {
        super(l);
    }

    /**
     * Kezeli az autó interakcióját az feltört jeges sávval.
     * <p>
     * Mivel a sáv járhatatlan, az autó nem tud áthaladni rajta. 
     * A metódus meghívja az autó {@code recalculateRoute()} függvényét.
     * Sikeres újratervezés esetén az autó elindul az új irányba, sikertelenség esetén pedig megáll.
     * </p>
     * @param c Az autó, amely megpróbál a sávra hajtani.
     */
    @Override
    public void handleVehicle(Car c) {
        SkeletonManager.call("BrokenIceState.handleVehicle(Car)");

        boolean foundNewPath = c.recalculateRoute();
        if(foundNewPath){
            c.start();
        }
        else {
            c.stop();
        }

        SkeletonManager.ret("void");
    }

    /**
     * Az időjárási hatások kezelése feltört jeges állapotban.
     * <p>
     * Ebben az állapotban a sáv havazás hatására is feltört jeges állapotban marad.
     * </p>
     */
    @Override
    public void snowLogic() {
        SkeletonManager.call("BrokenIceState.snowLogic()");

        // Nem csinál semmit, mert a feltört jeges sávon nem történik további hófelhalmozódás.

        SkeletonManager.ret("void");
    }
}
