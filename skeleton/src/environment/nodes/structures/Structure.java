package environment.nodes.structures;

import vehicles.*;

/**
 * Az épületeket és állomásokat reprezentáló absztrakt ősosztály.
 * Minden csomóponthoz ({@link environment.nodes.Node}) tartozhat egy Structure,
 * amellyel a járművek interakcióba léphetnek belépéskor és kilépéskor.
 * Az alosztályok (pl. {@link BusStop}, {@link Building}) határozzák meg a
 * konkrét viselkedést.
 */
public abstract class Structure {
    /** Az épület neve (kiíratáshoz és azonosításhoz). */
    protected String sName;

    /**
     * A Structure osztály konstruktora.
     * 
     * @param sName Az épület neve.
     */
    protected Structure(String sName) {
        this.sName = sName;
    }

    /**
     * Visszaadja az épület nevét.
     * 
     * @return Az épület neve.
     */
    protected String getsName() {
        return sName;
    }

    /**
     * Visszaadja az épület skeleton-azonosító nevét.
     * 
     * @return Az épület skeleton neve.
     */
    protected void setSName(String sName) {
        this.sName = sName;
    }

    /*
     * accept... metódusok (Beléptetés / Fogadás):
     * 
     * acceptCar(Car c), acceptBus(Bus b), acceptSnowplow(Snowplow s)
     * 
     * Szerepük: Ezek felelnek azért, hogy egy adott típusú járművet beengedjenek és
     * regisztráljanak az épületben (pl. hozzáadják az épület belső listájához).
     * 
     * Működés: Mivel külön függvény van minden járműre, a leszármazott épületek
     * (pl. BusStop) pontosan meg tudják válogatni, kit engednek be. A BusStop
     * felülírja az acceptBus-t (beengedi a buszt), de üresen hagyja az acceptCar-t
     * (így az autókat figyelmen kívül hagyja).
     */

    public abstract void acceptCar(Car c);

    public abstract void acceptBus(Bus b);

    public abstract void acceptSnowplow(Snowplow s);

    /*
     * remove... metódusok (Kiléptetés / Eltávolítás):
     * 
     * removeCar(Car c), removeBus(Bus b), removeSnowplow(Snowplow s)
     * 
     * Szerepük: Ezek felelnek azért, hogy egy adott típusú járművet töröljenek az
     * épület nyilvántartásából, amikor a jármű úgy dönt, hogy elhagyja azt (pl. a
     * busz befejezte a várakozást és elindul a megállóból).
     * 
     * Működés: Ugyanazon a logikán alapul; kiveszi a paraméterként kapott
     * járműpéldányt a megfelelő (pl. várakozó buszok) listából.
     */
    
    public abstract void removeCar(Car c);

    public abstract void removeBus(Bus b);

    public abstract void removeSnowplow(Snowplow s);
}
