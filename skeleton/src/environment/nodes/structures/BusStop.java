package environment.nodes.structures;

import java.util.*;
import vehicles.*;

/**
 * A buszmegállót (végállomást) reprezentáló osztály, amely a {@link Structure}
 * ősosztályból származik.
 * A buszmegálló nyilvántartja a várakozó és parkoló buszokat, valamint ismeri a
 * városban
 * lévő összes megállót, hogy véletlenszerű célállomást tudjon kisorsolni.
 */
public class BusStop extends Structure {
    private List<Bus> waitingBuses;
    /**
     * A BusStop osztály konstruktora.
     * 
     * @param name A buszmegálló neve.
     */
    public BusStop(String name) {
        super(name);
        this.waitingBuses = new ArrayList<>();
    }

    /**
     * Autót fogad – buszmegállóban nincs teendő autóval.
     * 
     * @param c A fogadott autó.
     */
    @Override
    public void acceptCar(Car c) {
        //* Buszmegállóban nincs teendő autóval. */
    }

    /**
     * Buszt fogad: a várakozók listájába veszi, majd értesíti a buszt a
     * megérkezésről.
     * 
     * @param b A megérkező busz.
     */
    @Override
    public void acceptBus(Bus b) {
        if (!waitingBuses.contains(b)) {
            waitingBuses.add(b);
            b.arriveAtTerminal(this);
        }
    }

    /**
     * Hókotrót fogad – buszmegállóban nincs teendő hókotrókkal.
     * 
     * @param s A fogadott hókotró.
     */
    @Override
    public void acceptSnowplow(Snowplow s) {
        //* Buszmegállóban nincs teendő hókotrókkal. */
    }

    /**
     * Autót távolít el – buszmegállóban nincs teendő autóval.
     * 
     * @param c Az eltávolítandó autó.
     */
    @Override
    public void removeCar(Car c) {
        //* Buszmegállóban nincs teendő autóval. */
    }

    /**
     * Eltávolít egy buszt mind a várakozó, mind a parkoló listából.
     * 
     * @param b Az eltávolítandó busz.
     */
    @Override
    public void removeBus(Bus b) {
        waitingBuses.remove(b);
    }

    /**
     * Hókotrót távolít el – buszmegállóban nincs teendő hókotrókkal.
     * 
     * @param s Az eltávolítandó hókotró.
     */
    @Override
    public void removeSnowplow(Snowplow s) {
        //* Buszmegállóban nincs teendő hókotrókkal. */
    }
}
