package environment.nodes.structures;

import java.util.ArrayList;
import java.util.List;

import environment.nodes.Node;
import vehicles.*;

/**
 * Hókotró-állomást megvalósító osztály.
 * Az állomáson a hókotró fejet cserélhet és feltölthet.
 */
public class SnowplowStation extends Structure {
    String sName;
    Node node;
    
    private List<Snowplow> snowplows;

    /**
     * Konstruktor a hókotró-állomás létrehozásához.
     *
     * @param sName Az állomás skeleton neve.
     * @param node  A csomópont, amelyhez az állomás tartozik.
     */
    public SnowplowStation(String sName, Node node) {
        super(sName);
        this.sName = sName;
        this.node = node;
        snowplows = new ArrayList<>();
    }

    /** @param c A fogadandó autó (állomáson nem releváns). */
    @Override
    public void acceptCar(Car c) {}

    /** @param b A fogadandó busz (állomáson nem releváns). */
    @Override
    public void acceptBus(Bus b) {}

    /** @param c Az eltávolítandó autó (állomáson nem releváns). */
    @Override
    public void removeCar(Car c) {}

    /** @param b Az eltávolítandó busz (állomáson nem releváns). */
    @Override
    public void removeBus(Bus b) {}

    /**
     * Fogadja a megérkező hókotrót, és lehetővé teszi az állomáson való tevékenységet.
     *
     * @param snowplow A megérkező hókotró.
     */
    @Override
    public void acceptSnowplow(Snowplow snowplow) {
        if (!snowplows.contains(snowplow)) {
            snowplows.add(snowplow);
        }
        snowplow.onStation();
    }

    /**
     * Eltávolítja a hókotrót az állomásról, amikor az elhagyja azt.
     *
     * @param snowplow Az állomást elhagyó hókotró.
     */
    @Override
    public void removeSnowplow(Snowplow snowplow) {
        snowplow.departFromStructure(this);
    }

    public List<Snowplow> getSnowplows() {
        return snowplows;
    }
}
