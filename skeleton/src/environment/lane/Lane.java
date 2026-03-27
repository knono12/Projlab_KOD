package environment.lane;

import java.util.ArrayList;
import java.util.List;

import environment.lane.lanestates.ClearState;
import environment.lane.lanestates.LaneState;
import environment.nodes.Node;
import environment.road.Road;
import skeleton.SkeletonManager;
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
    String name;

    /**
     * A Lane osztály konstruktora.
     * Alapértelmezetten tiszta állapottal ({@link ClearState}) és 0 hóvastagsággal jön létre.
     * * @param from A kiinduló csomópont.
     * @param to A cél csomópont.
     * @param name A sáv neve.
     */
    public Lane(Node from, Node to, String name){
        snowThickness = 0;
        laneState = new ClearState();
        vehicles = new ArrayList<>();
        fromNode = from;
        toNode = to;
        this.name = name;
    }

    /**
     * Kezeli a hókotró belépését és takarítási kísérletét a sávon.
     * A tényleges logikát a sáv aktuális állapota ({@link LaneState}) határozza meg.
     * * @param snowplow A sávra lépő hókotró.
     * @return Igaz, ha a hókotró sikeresen elvégezte az állapottal való interakciót (és takarítást).
     */
    public boolean handleVehicle(Snowplow snowplow){
        SkeletonManager.call("Lane.handleVehicle(Snowplow)");

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
        SkeletonManager.call("Lane.sweep()");

        boolean success = laneState.sweep(laneCount); 

        SkeletonManager.ret(String.valueOf(success));
        return success;
    }

    /**
     * Elindítja a jég feltörését a sávon.
     * @return Igaz, ha sikeres volt a jégtörés (azaz az út ténylegesen jeges volt).
     */
    public boolean brakeIce(){
        SkeletonManager.call("Lane.brakeIce()");
        boolean success = laneState.brakeIce();
        SkeletonManager.ret(String.valueOf(success));
        return success;
    }
    
    /**
     * Elindítja a sáv felsózását.
     * @return Igaz, ha az utat be lehetett sózni.
     */
    public boolean salt(){
        SkeletonManager.call("Lane.salt()");
        boolean success = laneState.salt();
        SkeletonManager.ret(String.valueOf(success));
        return success;
    }

    /**
     * Elindítja a sáv felolvasztását lángszóróval.
     * @return Igaz, ha sikeres volt az olvasztás.
     */
    public boolean melt(){
        SkeletonManager.call("Lane.melt()");
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
        SkeletonManager.call("Lane.pushSnowRight(" + laneCount + ")");

        int currLaneIdx = road.getLanes().indexOf(this);
        if (currLaneIdx + laneCount < road.getLanes().size()){
            Lane nextLane = road.getLanes().get(currLaneIdx + laneCount);
            nextLane.snowLogic();
        }

        SkeletonManager.ret("void");
    }

    /**
     * Visszaadja a sáv nevét.
     * * @return A sávot azonosító név.
     */
    public String getName(){
        return name;
    }

}