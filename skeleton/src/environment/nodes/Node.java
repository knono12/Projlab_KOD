package environment.nodes;

import java.util.ArrayList;
import java.util.List;

import environment.nodes.structures.Structure;
import environment.road.Road;
import skeleton.SkeletonManager;
import vehicles.Vehicle;

/**
 * A gráf csomópontjait reprezentáló osztály.
 * A csomópontok kötik össze az utakat ({@link Road}), itt várakoznak a járművek ({@link Vehicle}), amíg tovább nem haladnak.
 * Egy csomóponthoz tartozhat egy épület vagy állomás ({@link Structure}) is, amellyel a járművek interakcióba léphetnek.
 */
public class Node {
    protected List<Road> connectedRoads;
    protected Structure structure;
    protected List<Vehicle> waitingVehicles;
    protected String name;

    /**
     * A Node osztály konstruktora.
     * Inicializálja a csatlakozó utak és a várakozó járművek listáját.
     * * @param name A csomópont neve.
     */
    public Node(String name){
        connectedRoads = new ArrayList<>();
        waitingVehicles = new ArrayList<>();
        this.name = name;
    }

    /**
     * Egy jármű belép a csomópontba.
     * A jármű bekerül a várakozó járművek listájába, majd megpróbál interakcióba lépni a csomóponton található épülettel (ha van ilyen).
     * * @param v A csomópontba belépő jármű.
     */
    public void enterNode(Vehicle v){
        SkeletonManager.call(this.name + ".enterNode(" + v.getName() + ")");
        waitingVehicles.add(v);
        v.interactWithStructure(structure);
        SkeletonManager.ret("void");
    }

    /**
     * Egy jármű elhagyja a csomópontot.
     * A jármű kikerül a várakozó járművek listájából, és elhagyja a csomóponton lévő épületet is.
     * * @param v A csomópontot elhagyó jármű.
     */
    public void leaveNode(Vehicle v) {
        SkeletonManager.call(this.name + ".leaveNode(" + v.getName() + ")");
        waitingVehicles.remove(v);
        v.departFromStructure(structure);
        SkeletonManager.ret("void");
    }

    /**
     * Hozzáad egy utat a csomóponthoz.
     * Ezzel a metódussal építhető fel az úthálózat (gráf) az inicializálásakor.
     * * @param r A csomóponthoz csatlakoztatni kívánt út.
     */
    public void addRoad(Road r){
        SkeletonManager.call(this.name + ".addRoad(" + r.getName() + ")");
        connectedRoads.add(r);
        SkeletonManager.ret("void");
    }

    /**
     * Beállít egy épületet vagy állomást a csomóponthoz.
     * * @param s A csomópontra helyezendő épület (BusStop / SnowplowStation).
     */
    public void addStructure(Structure s) {
        SkeletonManager.call(this.name + ".addStructure(" + s.getName() + ")");
        structure = s;
        SkeletonManager.ret("void");
    }

    /**
     * Visszaadja a csomóponthoz csatlakozó utak.
     * * @return A csomóponthoz csatlakozó utak listája.
     */
    public List<Road> getRoads(){
        return connectedRoads;
    }

    /**
     * Visszaadja a csomópont nevét.
     * * @return A csomópont azonosító neve.
     */
    public String getname(){
        return name;
    }

}
