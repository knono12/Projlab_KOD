package environment.lane.lanestates;

import environment.lane.Lane;
import skeleton.SkeletonManager;
import vehicles.Car;
import vehicles.Snowplow;
import vehicles.Vehicle;

/**
 * A balesetes sávot reprezentáló állapot.
 * Ebben az állapotban az útszakasz mindenki számára járhatatlanná válik,
 * blokkolja az összes járműv haladását.
 */
public class AccidentState extends LaneState {
    int accidentInterval;

    /**
     * Konstruktor a balesetes állapot létrehozásához.
     * 
     * @param l A sáv referenciája.
     * @param n Az állapot neve.
     */
    public AccidentState(Lane l, String n) {
        super(l, n);
    }

    /**
     * Az időjárási hatások kezelése baleset állapotban.
     * 
     * Ebben az állapotban a sáv havazás hatására is baleset állapotban marad.
     * Emellett mivel a havazás az idő múlását is jelzi, itt fogja számlálni, hogy mennyi idő múlva járható a sáv.
     * Ha vége a balesetnek a balesetet szenvedő járműveket elvontatják/újraindulnak és a sáv állapota {@code IcyState} lesz.
     */
    @Override
    public void snowLogic() {
        SkeletonManager.call(sName + ".snowLogic()");

        boolean accidentOver = SkeletonManager.ask("Vége a balesetnek? ");
        if(accidentOver){
            for(Vehicle v: lane.getVehicles()){
                v.accidentOverAction();
            }
            lane.changeState(new IcyState(lane, "icyState"));
        }

        SkeletonManager.ret("void");
    }

    /**
     * Megakadályozza az autó rálépését a balesetes sávra.
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
     * Megakadályozza a hókotró rálépését a balesetes sávra.
     * Mivel a sáv blokkolva van, a jármű a csomópontban marad.
     * * @param sp A belépni próbáló hókotró.
     * 
     * @return Mindig false, a lépés és a takarítás is meghiúsul.
     */
    @Override
    public boolean handleVehicle(Snowplow sp) {
        SkeletonManager.call(sName + ".handleVehicle(" + sp.getSName() + ")");
        SkeletonManager.ret("false");
        return false;
    }

    /**
     * Egy balesetes sávot nem lehet normál módon söpörni.
     * 
     * @return Mindig false.
     */
    @Override
    public boolean sweep(int laneCount) {
        SkeletonManager.call(sName + ".sweep(" + laneCount + ")");
        SkeletonManager.ret("false");
        return false;
    }

    /**
     * Egy balesetes sávon a jégtörés nem végrehajtható.
     * 
     * @return Mindig false.
     */
    @Override
    public boolean brakeIce() {
        SkeletonManager.call(sName + ".brakeIce()");
        SkeletonManager.ret("false");
        return false;
    }

    /**
     * Egy balesetes sávon nem lehet sót szorni.
     * 
     * @return Mindig false.
     */
    @Override
    public boolean salt() {
        SkeletonManager.call(sName + ".salt()");
        SkeletonManager.ret("false");
        return false;
    }

    /**
     * Egy balesetes sávon nem lehet sárkányfejjel sem takarítani.
     * 
     * @return Mindig false.
     */
    @Override
    public boolean melt() {
        SkeletonManager.call(sName + ".melt()");
        SkeletonManager.ret("false");
        return false;
    }

}
