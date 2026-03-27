package environment.lane.lanestates;

import environment.lane.Lane;
import skeleton.SkeletonManager;
import vehicles.Snowplow;

/**
 * A balesetes sávot reprezentáló állapot.
 * Ebben az állapotban az útszakasz mindenki számára járhatatlanná válik, blokkolja az összes járműv haladását.
 * </p>
 */
public class AccidentState extends LaneState {
    int accidentInterval;

    /**
     * Konstruktor a balesetes állapot létrehozásához.
     * @param l A sáv referenciája.
     * @param n Az állapot neve.
     */
    public AccidentState(Lane l, String n) {
        super(l, n);
    }

    /**
     * Megakadályozza a hókotró rálépését a balesetes sávra.
     * Mivel a sáv blokkolva van, a jármű a csomópontban marad.
     * * @param sp A belépni próbáló hókotró.
     * @return Mindig false, a lépés és a takarítás is meghiúsul.
     */
    @Override
    public boolean handleVehicle(Snowplow sp){
        SkeletonManager.call(name + ".handleVehicle(" + sp.getName() + ")");
        SkeletonManager.ret("false");
        return false;
    }

    /**
     * Egy balesetes sávot nem lehet normál módon söpörni.
     * @return Mindig false.
     */
    @Override
    public boolean sweep(int laneCount){
        SkeletonManager.call(name + ".sweep(" + laneCount + ")");        
        SkeletonManager.ret("false");
        return false;
    }

    /**
     * Egy balesetes sávon a jégtörés nem végrehajtható.
     * @return Mindig false.
     */
    @Override
    public boolean brakeIce(){
        SkeletonManager.call(name + ".brakeIce()");
        SkeletonManager.ret("false");
        return false;
    }

    /**
     * Egy balesetes sávon nem lehet sót szorni.
     * @return Mindig false.
     */
    @Override
    public boolean salt(){
        SkeletonManager.call(name + ".salt()");
        SkeletonManager.ret("false");
        return false;
    }

    /**
     * Egy balesetes sávon nem lehet sárkányfejjel sem takarítani.
     * @return Mindig false.
     */
    @Override
    public boolean melt(){
        SkeletonManager.call(name + ".melt()");
        SkeletonManager.ret("false");
        return false;
    }
}