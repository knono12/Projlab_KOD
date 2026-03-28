package environment.lane.lanestates;

import environment.lane.Lane;
import vehicles.Car;

/**
 * A sávok állapotát megvalósító absztrakt ősosztály.
 */
public abstract class LaneState {
    /** 
     * Referencia a sávra, amelyhez az állapot tartozik. 
     */
    protected Lane lane;

    /**
     * Konstruktor, amely összekapcsolja az állapotot a hozzá tartozó sávval.
     * @param l A sáv, amely az állapotot tárolja.
     */
    public LaneState(Lane l){
        lane = l;
    }

    /**
     * Absztrakt metódus az autónak a sávon való áthaladásának kezelésére.
     * <p>
     * Az egyes leszármazottak itt valósítják meg a rájuk jellemző fizikát,
     * valamint itt döntik el hogy ráhajthat-e a jármű a sávra
     * </p>
     * @param c A sávon haladó autó ({@link Car}).
     */
    public abstract void handleVehicle(Car c);

    /**
     * Absztrakt metódus az havazás kezelésére.
     * <p>
     * A leszármazottak ebben a metódusban döntenek arról, hogy a havazás 
     * hatására történik-e állapotváltás, vagy növekszik-e a hóvastagság.
     * </p>
     */
    public abstract void snowLogic();
}
