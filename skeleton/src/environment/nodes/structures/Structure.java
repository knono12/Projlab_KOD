package environment.nodes.structures;
import vehicles.*;

/**
 * Az épületeket és állomásokat reprezentáló absztrakt ősosztály.
 * Minden csomóponthoz ({@link environment.nodes.Node}) tartozhat egy Structure,
 * amellyel a járművek interakcióba léphetnek belépéskor és kilépéskor.
 * Az alosztályok (pl. {@link BusStop}, {@link Building}) határozzák meg a konkrét viselkedést.
 */
public abstract class Structure {
    /** Az épület neve (kiíratáshoz és azonosításhoz). */
    protected String name;

    /**
     * A Structure osztály konstruktora.
     * @param name Az épület neve.
     */
    public Structure(String name) {
        this.name = name;
    }

    /**
     * Visszaadja az épület nevét.
     * @return Az épület neve.
     */
    public String getName() {
        return name;
    }

    /**
     * Visszaadja az épület skeleton-azonosító nevét.
     * @return Az épület skeleton neve.
     */
    public String getSName() {
        return name;
    }

    /** @param c A fogadandó autó. */
    public abstract void acceptCar(Car c);
    /** @param b A fogadandó busz. */
    public abstract void acceptBus(Bus b);
    /** @param s A fogadandó hókotró. */
    public abstract void acceptSnowplow(Snowplow s);
    /** @param c Az eltávolítandó autó. */
    public abstract void removeCar(Car c);
    /** @param b Az eltávolítandó busz. */
    public abstract void removeBus(Bus b);
    /** @param s Az eltávolítandó hókotró. */
    public abstract void removeSnowplow(Snowplow s);
}
