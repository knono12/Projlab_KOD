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
import vehicles.Vehicle;

/**
 * Az út egy sávját megvalósító osztály.
 * <p>
 * Felelős a sávon tartózkodó járművek nyilvántartásáért, a sáv állapotának 
 * ({@link LaneState}) kezeléséért, valamint az aktuális hóvastagság tárolásáért.
 * A {@code fromNode} és {@code toNode} csomópontok határozzák meg a sáv irányát.
 * </p>
 */
public class Lane {
    /** A sávon lévő hó vastagsága. */
    private int snowThickness;
    /** A sáv aktuális állapota. */
    private LaneState laneState;
    /** A sávon jelenleg tartózkodó járművek listája. */
    private List<Vehicle> vehicles;
    /** A csomópont, ahonnan a sáv indul. */
    private Node fromNode;
    /** A csomópont, ahová a sáv érkezik. */
    private Node toNode;
    /** Az út, amelyhez a sáv tartozik. */
    private Road road;

    /**
     * Konstruktor a sáv létrehozásához.
     * Alapértelmezetten tiszta állapottal ({@link ClearState}) és nulla hóvastagsággal jön létre.
     * * @param from A forrás csomópont.
     * @param to A cél csomópont.
     */
    public Lane(Node from, Node to){
        snowThickness = 0;
        laneState = new ClearState();
        vehicles = new ArrayList<>();
        fromNode = from;
        toNode = to;
    }

    /**
     * Jármű beléptetése a sávra.
     * @param v A belépő jármű.
     */
    public void enterLane(Vehicle v){
        SkeletonManager.call("Lane.enterLane(Vehicle)");

        vehicles.add(v);

        SkeletonManager.ret("void");
    }

    /**
     * Jármű eltávolítása a sávról (áthaladás vagy baleset utáni elszállítás).
     * @param v Az eltávolítandó jármű.
     */
    public void removeVehicle(Vehicle v){
        SkeletonManager.call("Lane.removeVehicle(Vehicle)");

        vehicles.remove(v);

        SkeletonManager.ret("void");
    }

    /**
     * Lecseréli a sáv aktuális állapotát a paraméterben megadottra.
     * @param newState Az új állapot (pl. {@link IcyState}).
     */
    public void changeState(LaneState newState){
        SkeletonManager.call("Lane.changeState(LaneState)");

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
     * Elindítja a sáv aktuális állapotára jellemző autó viselkedést a sávra lépő autón. 
     * * @param c Az érintett autó.
     */
    public void handleVehicle(Car c){
        SkeletonManager.call("Lane.handleVehicle(Vehicle)");

        laneState.handleVehicle(c);

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
}
