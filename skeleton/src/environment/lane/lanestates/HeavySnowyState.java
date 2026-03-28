package environment.lane.lanestates;

import environment.lane.Lane;
import skeleton.SkeletonManager;
import vehicles.Car;

/**
 * Az erősen havas sávot reprezentáló állapot.
 * <p>
 * Ebben az állapotban az útszakasz a normál forgalom számára 
 * járhatatlanná válik, így blokkolja az autók, buszok haladását, viszont a hókotró ráhajthat. 
 * A hóvastagság itt elérte a maximumot, így a további havazás nem változtat 
 * a sáv állapotán.
 * </p>
 */
public class HeavySnowyState extends LaneState {

    /**
     * Konstruktor, amely összekapcsolja az állapotot a sávval.
     * @param l A sáv, amely ebben az állapotban van.
     */
    public HeavySnowyState(Lane l) {
        super(l);
    }

    /**
     * Kezeli az autó interakcióját az erősen havas sávval.
     * <p>
     * Mivel a sáv járhatatlan, az autó nem tud áthaladni rajta. 
     * A metódus meghívja az autó {@code recalculateRoute()} függvényét.
     * Sikeres újratervezés esetén az autó elindul az új irányba, sikertelenség esetén pedig megáll.
     * </p>
     * @param c Az autó, amely megpróbál a sávra hajtani.
     */
    @Override
    public void handleVehicle(Car c) {
        SkeletonManager.call("HeavySnowyState.handleVehicle(Car)");

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
     * Az időjárási hatások kezelése nagyon havas állapotban.
     * <p>
     * Ebben az állapotban a sáv már nem tud több havat befogadni, ezért a havazásna nincs hatása.
     * </p>
     */
    @Override
    public void snowLogic() {
        SkeletonManager.call("HeavySnowyState.snowLogic()");

        // Nem csinál semmit, mert az nagyon havas sávon nem történik további hófelhalmozódás.

        SkeletonManager.ret("void");
    }
    
}
