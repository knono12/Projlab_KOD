package environment.nodes;

import java.util.ArrayList;
import java.util.List;

import environment.nodes.structures.Structure;
import environment.road.Road;
import vehicles.Vehicle;

/**
 * A gráf csomópontjait reprezentáló osztály.
 * A csomópontok kötik össze az utakat ({@link Road}), itt várakoznak a járművek ({@link Vehicle}), amíg tovább nem haladnak.
 * Egy csomóponthoz tartozhat egy épület vagy állomás ({@link Structure}) is, amellyel a járművek interakcióba léphetnek.
 */
public class Node {
    protected List<Road> connectedRoads;
    protected Structure structure;
    protected List<Vehicle> vehicles;
    protected List<Vehicle> waitingVehicles;
    protected String sName;

    /**
     * A Node osztály konstruktora.
     * Inicializálja a csatlakozó utak és a várakozó járművek listáját.
     * * @param name A csomópont neve.
     */
    public Node(String name){
        connectedRoads = new ArrayList<>();
        vehicles = new ArrayList<>();
        waitingVehicles = new ArrayList<>();
        this.sName = name;
    }

    /**
     * Egy jármű belép a csomópontba.
     * A jármű bekerül a várakozó járművek listájába, majd megpróbál interakcióba lépni a csomóponton található épülettel (ha van ilyen).
     * * @param v A csomópontba belépő jármű.
     */
    public void enterNode(Vehicle v){
        if(v.getCurrentLane() != null)
            v.getCurrentLane().removeVehicle(v);
        vehicles.add(v);
        if (structure != null) {
            v.interactWithStructure(structure);
        }
    }

    /**
     * Egy jármű elhagyja a csomópontot.
     * A jármű kikerül a várakozó járművek listájából, és elhagyja a csomóponton lévő épületet is.
     * * @param v A csomópontot elhagyó jármű.
     */
    public void leaveNode(Vehicle v) {
        vehicles.remove(v);

        waitingVehicles.remove(v);

        if (structure != null) {
            v.departFromStructure(structure);
        }
    }

    public void addWaitingVehicle(Vehicle v) {
        if(!waitingVehicles.contains(v))
            waitingVehicles.add(v);
    }
    
    public void removeWaitingVehicle(Vehicle v) {
        waitingVehicles.remove(v);
    }
    /**
     * Hozzáad egy utat a csomóponthoz.
     * Ezzel a metódussal építhető fel az úthálózat (gráf) az inicializálásakor.
     * * @param r A csomóponthoz csatlakoztatni kívánt út.
     */
    public void addRoad(Road r){
        connectedRoads.add(r);
    }

    /**
     * Beállít egy épületet vagy állomást a csomóponthoz.
     * * @param s A csomópontra helyezendő épület (BusStop / SnowplowStation).
     */
    public void addStructure(Structure s) {
        structure = s;
    }

    public Structure getStructure() {
        return structure;
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
    public String getSName(){
        return sName;
    }

    /**
     * Visszaadja a csomóponton lévő autók listáját.
     * * @return A csomóponthoz tartozó lehúzódott autók listája.
     */
    public List<Vehicle> getVehicles(){
        return vehicles;
    }

    /**
     * Visszaadja a lehúzódott autók listáját.
     * * @return A csomóponthoz tartozó lehúzódott autók listája.
     */
    public List<Vehicle> getWaitingVehicles(){
        return waitingVehicles;
    }
}