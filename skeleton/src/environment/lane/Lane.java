package environment.lane;

import java.util.ArrayList;
import java.util.List;

import environment.lane.lanestates.ClearState;
import environment.lane.lanestates.IcyState;
import environment.lane.lanestates.LaneState;
import environment.nodes.Node;
import environment.road.Road;
import skeleton.SkeletonManager;
import vehicles.Car;
import vehicles.Snowplow;
import vehicles.Vehicle;

/**
 * Egy utca vagy útszakasz egyetlen sávját reprezentáló osztály.
 * A sáv felelős a rajta lévő járművek nyilvántartásáért, az aktuális útállapot ({@link LaneState}) tárolásáért, 
 * valamint a havazás és a hókotró fejek által kiváltott takarítási folyamatok (söprés, sózás, stb.) delegálásáért az aktuális állapot felé.
 */
public class Lane {
    int snowThickness;
    LaneState laneState;
    List<Vehicle> vehicles;
    Node fromNode;
    Node toNode;
    Road road;
    String sName;

    /**
     * A Lane osztály konstruktora.
     * Alapértelmezetten tiszta állapottal ({@link ClearState}) és 0 hóvastagsággal jön létre.
     * * @param from A kiinduló csomópont.
     * @param to A cél csomópont.
     * @param name A sáv neve.
     */
    public Lane(Node from, Node to, String name){
        snowThickness = 0;
        laneState = new ClearState(this, "clearState");
        vehicles = new ArrayList<>();
        fromNode = from;
        toNode = to;
        this.sName = name;
    }

    /**
     * Jármű beléptetése a sávra.
     * @param v A belépő jármű.
     */
    public void enterLane(Vehicle v){
        SkeletonManager.call(sName + ".enterLane(" + v.getSName() + ")");

        vehicles.add(v);

        SkeletonManager.ret("void");
    }

    /**
     * Kezeli az autó belépését és viselkedését a sávon.
     * A tényleges logikát a sáv aktuális állapota ({@link LaneState}) határozza meg.
     * * @param c A sávra lépő autó.
     * @return Igaz, ha az autó sikeresen végighajtott a sávon.
     */
    public boolean handleVehicle(Car c){
        SkeletonManager.call(sName + ".handleVehicle(" + c.getSName() + ")");

        boolean success = laneState.handleVehicle(c);

        SkeletonManager.ret(String.valueOf(success));
        return success;
    }

    /**
     * Kezeli a hókotró belépését és takarítási kísérletét a sávon.
     * A tényleges logikát a sáv aktuális állapota ({@link LaneState}) határozza meg.
     * * @param snowplow A sávra lépő hókotró.
     * @return Igaz, ha a hókotró sikeresen elvégezte az állapottal való interakciót (és takarítást).
     */
    public boolean handleVehicle(Snowplow snowplow){
        SkeletonManager.call(sName + ".handleVehicle(" + snowplow.getSName() + ")");

        boolean success = laneState.handleVehicle(snowplow);

        SkeletonManager.ret(String.valueOf(success));
        return success;
    }

    /**
     * Elindítja a sáv lesöprését. A megvalósítás az aktuális állapottól függ.
     * * @param laneCount Ahány sávval jobbra toljuk a havat.
     * @return Igaz, ha az aktuális állapotban lehetséges volt a söprés (pl. havas úton).
     */
    public boolean sweep(int laneCount){
        SkeletonManager.call(sName + ".sweep()");

        boolean success = laneState.sweep(laneCount); 

        SkeletonManager.ret(String.valueOf(success));
        return success;
    }

    /**
     * Elindítja a jég feltörését a sávon.
     * @return Igaz, ha sikeres volt a jégtörés (azaz az út ténylegesen jeges volt).
     */
    public boolean brakeIce(){
        SkeletonManager.call(sName + ".brakeIce()");
        boolean success = laneState.brakeIce();
        SkeletonManager.ret(String.valueOf(success));
        return success;
    }
    
    /**
     * Elindítja a sáv felsózását.
     * @return Igaz, ha az utat be lehetett sózni.
     */
    public boolean salt(){
        SkeletonManager.call(sName + ".salt()");
        boolean success = laneState.salt();
        SkeletonManager.ret(String.valueOf(success));
        return success;
    }

    /**
     * Elindítja a sáv felolvasztását lángszóróval.
     * @return Igaz, ha sikeres volt az olvasztás.
     */
    public boolean melt(){
        SkeletonManager.call(sName + ".melt()");
        boolean success = laneState.melt();
        SkeletonManager.ret(String.valueOf(success));
        return success;
    }

    /**
     * A havat a megadott számú sávval jobbra tolja.
     * Megkeresi a jobbra lévő sávot az út sávlistájában, és meghívja rajta a havazás logikát.
     * * @param laneCount A sávok száma, amennyivel jobbra kell tolni a havat.
     */
    public void pushSnowRight(int laneCount){
        SkeletonManager.call(sName + ".pushSnowRight(" + laneCount + ")");

        int currLaneIdx = road.getLanes().indexOf(this);
        if (currLaneIdx + laneCount < road.getLanes().size()){
            Lane nextLane = road.getLanes().get(currLaneIdx + laneCount);
            nextLane.snowLogic();
        }

        SkeletonManager.ret("void");
    }

    /**
     * Jármű eltávolítása a sávról (áthaladás vagy baleset utáni elszállítás).
     * @param v Az eltávolítandó jármű.
     */
    public void removeVehicle(Vehicle v){
        SkeletonManager.call(sName + ".removeVehicle(" + v.getSName() + ")");

        vehicles.remove(v);

        SkeletonManager.ret("void");
    }

    /**
     * Lecseréli a sáv aktuális állapotát a paraméterben megadottra.
     * @param newState Az új állapot (pl. {@link IcyState}).
     */
    public void changeState(LaneState newState){
        SkeletonManager.call(sName + ".changeState(" + newState.getSName() + ")");

        laneState = newState;

        SkeletonManager.ret("void");
    }

    /**
     * Elindítja a sáv aktuális állapotára jellemző havazási logikát.
     */
    public void snowLogic(){
        SkeletonManager.call("Lane.snowLogic()");

        laneState.snowLogic();

        SkeletonManager.ret("void");
    }

    /**
     * Növeli a sávon lévő hó vastagságát.
     */
    public void snowBuildUp(){
        SkeletonManager.call("Lane.snowBuildUp()");

        snowThickness++;

        SkeletonManager.ret("void");
    }

    /** @return A sáv forrás csomópontja. */
    public Node getFromNode(){
        return fromNode;
    }

    /** @return A sáv cél csomópontja. */
    public Node getToNode(){
        return toNode;
    }

    /** @return A sávon lévő hó vastagsága. */
    public int getSnowThickness() {
        return snowThickness;
    }

    /** @return A sávon lévő hó vastagsága. */
    public List<Vehicle> getVehicles() {
        return vehicles;
    }

    /** 
     * Visszaadja a sáv nevét.
     * * @return A sávot azonosító név.
     */
    public String getSName(){
        return sName;
    }

}
