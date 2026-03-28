package environment.nodes.structures;

import java.util.*;

import skeleton.SkeletonManager;
import vehicles.*;

/**
 * A buszmegállót (végállomást) reprezentáló osztály, amely a {@link Structure} ősosztályból származik.
 * A buszmegálló nyilvántartja a várakozó és parkoló buszokat, valamint ismeri a városban
 * lévő összes megállót, hogy véletlenszerű célállomást tudjon kisorsolni.
 */
public class BusStop extends Structure {
    /** A megállóban várakozó buszok listája. */
    private List<Bus> waitingBuses;
    /** A megállóban parkolt (végállomáson álló) buszok listája. */
    private List<Bus> parkedBuses;
    /** A városban lévő összes buszmegálló listája (véletlenszerű célsorsoláshoz). */
    private List<BusStop> allBusStops;

    /**
     * A BusStop osztály konstruktora.
     * @param name A buszmegálló neve.
     */
    public BusStop(String name) {
        super(name);
        this.waitingBuses = new ArrayList<>();
        this.parkedBuses = new ArrayList<>();
        this.allBusStops = new ArrayList<>();
    }

    /**
     * Beállítja az összes buszmegálló listáját (véletlenszerű célsorsoláshoz szükséges).
     * @param stops Az összes megálló listája.
     */
    public void setAllBusStops(List<BusStop> stops) {
        this.allBusStops = stops;
    }
/*
    /**
     * Egy jármű belép a megállóba. Ha busz, meghívja az {@link #acceptBus(Bus)} metódust.
     * @param v A belépő jármű.
     
    @Override
    public void enterStructure(Vehicle v) {
        SkeletonManager.call(name + ".enterStructure(" + v.getName() + ")");
        if (v instanceof Bus) {
            acceptBus((Bus) v);
        }
        SkeletonManager.ret("void");
    }

    /**
     * Egy jármű elhagyja a megállót. Ha busz, meghívja a {@link #removeBus(Bus)} metódust.
     * @param v A kilépő jármű.
     
    @Override
    public void leaveStructure(Vehicle v) {
        SkeletonManager.call(name + ".leaveStructure(" + v.getName() + ")");
        if (v instanceof Bus) {
            removeBus((Bus) v);
        }
        SkeletonManager.ret("void");
    }
*/
    /**
     * Autót fogad – buszmegállóban nincs teendő autóval.
     * @param c A fogadott autó.
     */
    @Override
    public void acceptCar(Car c) {
        SkeletonManager.call(name + ".acceptCar(" + c.getSName() + ")");
        SkeletonManager.ret("void");
    }

    /**
     * Buszt fogad: a várakozók listájába veszi, majd értesíti a buszt a megérkezésről.
     * @param b A megérkező busz.
     */
    @Override
    public void acceptBus(Bus b) {
        SkeletonManager.call(name + ".acceptBus(" + b.getSName() + ")");
        if (!waitingBuses.contains(b)) {
            waitingBuses.add(b);
            b.arriveAtTerminal(this);
        }
        SkeletonManager.ret("void");
    }

    /**
     * Hókotrót fogad – buszmegállóban nincs teendő hókotrókkal.
     * @param s A fogadott hókotró.
     */
    @Override
    public void acceptSnowplow(Snowplow s) {
        SkeletonManager.call(name + ".acceptSnowplow(" + s.getSName() + ")");
        SkeletonManager.ret("void");
    }

    /**
     * Autót távolít el – buszmegállóban nincs teendő autóval.
     * @param c Az eltávolítandó autó.
     */
    @Override
    public void removeCar(Car c) {
        SkeletonManager.call(name + ".removeCar(" + c.getSName() + ")");
        SkeletonManager.ret("void");
    }

    /**
     * Eltávolít egy buszt mind a várakozó, mind a parkoló listából.
     * @param b Az eltávolítandó busz.
     */
    @Override
    public void removeBus(Bus b) {
        SkeletonManager.call(name + ".removeBus(" + b.getSName() + ")");
        waitingBuses.remove(b);
        parkedBuses.remove(b);
        SkeletonManager.ret("void");
    }

    /**
     * Hókotrót távolít el – buszmegállóban nincs teendő hókotrókkal.
     * @param s Az eltávolítandó hókotró.
     */
    @Override
    public void removeSnowplow(Snowplow s) {
        SkeletonManager.call(name + ".removeSnowplow(" + s.getSName() + ")");
        SkeletonManager.ret("void");
    }

    /**
     * Átparkol egy buszt a várakozó listából a parkoló listába.
     * @param b A parkolásra kerülő busz.
     */
    public void parkBus(Bus b) {
        SkeletonManager.call(name + ".parkBus(" + b.getSName() + ")");
        waitingBuses.remove(b);
        parkedBuses.add(b);
        SkeletonManager.ret("void");
    }

    /**
     * Véletlenszerűen kiválaszt egy, az aktuálistól eltérő buszmegállót.
     * Ha csak egy megálló létezik, önmagát adja vissza.
     * @return A kisorsolott buszmegálló.
     */
    public BusStop getRandomBusStop() {
        SkeletonManager.call(name + ".getRandomBusStop()");
        BusStop result = null;
        if (allBusStops != null && allBusStops.size() > 1) {
            Random rand = new Random();
            BusStop newStop;
            do {
                newStop = allBusStops.get(rand.nextInt(allBusStops.size()));
            } while (newStop == this); // Ne ugyanazt a megállót sorsolja
            result = newStop;
        } else {
            result = this;
        }
        SkeletonManager.ret("BusStop (" + (result != null ? result.getName() : "null") + ")");
        return result;
    }
}
