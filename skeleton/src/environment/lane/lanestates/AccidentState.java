package environment.lane.lanestates;

import java.util.ArrayList;

import environment.lane.Lane;
import vehicles.Bus;
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
        accidentInterval = 3;
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
        accidentInterval--;

        if(accidentInterval == 0){
            for(Vehicle v: new ArrayList<>(lane.getVehicles())){
                v.accidentOverAction();
            }
            lane.changeState(new IcyState(lane, "icyState"));
        }
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
        boolean foundNewPath = c.recalculateRoute();
        if(!foundNewPath){
            c.stop();
        }

        return false;
    }

    /**
     * Megakadályozza a busz rálépését a balesetes sávra.
     * * @param b A belépni próbáló busz.
     * 
     * @return Mindig false.
     */
    @Override
    public boolean handleVehicle(Bus b) {
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
        return false;
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
     * * @return mindig hamis
     */
    @Override
    public boolean isPassableSnowplow(){
        return false;
    }

}
