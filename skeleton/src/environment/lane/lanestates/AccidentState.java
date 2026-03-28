package environment.lane.lanestates;

import environment.lane.Lane;
import skeleton.SkeletonManager;
import vehicles.Car;
import vehicles.Vehicle;

/**
 * A baleset sávot reprezentáló állapot.
 * <p>
 * Ebben az állapotban az útszakasz a mindenki számára 
 * járhatatlanná válik, így blokkolja az autók, buszok haladását, viszont a hókotró ráhajthat. 
 * A hóesés nem változtat a sáv állapotán, a baleset eltakarítása után visszatér jeges állapotba.
 * </p>
 */
public class AccidentState extends LaneState {
    int accidentInterval;

    /**
     * Konstruktor, amely összekapcsolja az állapotot a sávval.
     * @param l A sáv, amely ebben az állapotban van.
     */
    public AccidentState(Lane l) {
        super(l);
    }

    /**
     * Kezeli az autó interakcióját a baleset sávval.
     * <p>
     * Mivel a sáv járhatatlan, az autó nem tud áthaladni rajta. 
     * A metódus meghívja az autó {@code recalculateRoute()} függvényét.
     * Sikeres újratervezés esetén az autó elindul az új irányba, sikertelenség esetén pedig megáll.
     * </p>
     * @param c Az autó, amely megpróbál a sávra hajtani.
     */
    @Override
    public void handleVehicle(Car c) {
        SkeletonManager.call("AccidentState.handleVehicle(Car)");

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
     * Az időjárási hatások kezelése baleset állapotban.
     * <p>
     * Ebben az állapotban a sáv havazás hatására is baleset állapotban marad.
     * Emellett mivel a havazás az idő múlását is jelzi, itt fogja számlálni, hogy mennyi idő múlva járható a sáv.
     * </p>
     */
    @Override
    public void snowLogic() {
        SkeletonManager.call("AccidentState.snowLogic()");

        boolean accidentOver = SkeletonManager.ask("Vége a balesetnek? ");
        if(accidentOver){
            for(Vehicle v: lane.getVehicles()){
                v.accidentOverAction();
            }
            lane.changeState(new IcyState(lane));
        }

        SkeletonManager.ret("void");
    }
}
