package environment;

import java.util.ArrayList;
import java.util.List;

import environment.nodes.Node;
import environment.nodes.structures.BusStop;
import environment.road.Road;
import skeleton.SkeletonManager;
import vehicles.Vehicle;

/**
 * A szimulációs gráfot reprezentáló osztály.
 * Eltárolja a csomópontokat ({@link Node}) és az utakat ({@link Road}),
 * amelyek a közlekedési hálózatot alkotják.
 */
public class Graph {
    /** A gráfban szereplő csomópontok listája. */
    private List<Node> nodes;
    /** A gráfban szereplő utak listája. */
    private List<Road> roads;
    /** A gráfban szereplő buszmegállók listája. */
    private List<BusStop> allBusStops;
    /** A gráfban szereplő járművek listája. */
    private List<Vehicle> vehicles;

    /**
     * A Graph osztály konstruktora.
     * Inicializálja az üres csomópont- és útlistákat.
     */
    public Graph() {
        nodes = new ArrayList<>();
        roads = new ArrayList<>();
        allBusStops = new ArrayList<>();
        vehicles = new ArrayList<>();
    }

    /**
     * Hozzáad egy csomópontot a gráfhoz.
     * @param n A hozzáadandó csomópont.
     */
    public void addNode(Node n) {
        if(n != null)
            nodes.add(n);
    }

    /**
     * Hozzáad egy utat a gráfhoz.
     * @param r A hozzáadandó út.
     */
    public void addRoad(Road r) {
        if(r != null)
            roads.add(r);
    }

    public void addBusStop(BusStop bs) {
        if(bs != null)
            allBusStops.add(bs);
    }

    /** @return A gráf csomópontjainak listája. */
    public List<Node> getNodes() {
        return nodes;
    }

    /** @return A gráf útjainak listája. */
    public List<Road> getRoads() {
        return roads;
    }

    /** @return A gráf buszmegállóinak listája. */
    public List<BusStop> getBusStops() {
        return allBusStops;
    }
    /** @return A gráf járműveinek listája. */
    public List<Vehicle> getVehicles() {
        return vehicles;
    }
    /**
     * Hozzáad egy járművet a gráfhoz.
     * @param v A hozzáadandó jármű.
     */
    public void addVehicle(Vehicle v) {
        if (v != null)
            vehicles.add(v);
    }

    private void snow() {
        for (Road r : roads) {
            r.snowLogic();
        }
    }

    private void vehicleMove() {
        for (Vehicle v : vehicles) {
            v.moveOntoLane();
        }
        for (Vehicle v : vehicles) {
            v.moveOntoNode();
        }
    }

    private boolean isOver() {
        int stuck = 0;
        for(Vehicle v : vehicles) {
            if(v.getCurrentLane() != null && v.canCollide())
                stuck++;
        }

        return !vehicles.isEmpty() && (double) stuck / vehicles.size() > 0.3;
    }

    public void step() {
        snow();

        vehicleMove();
        
        if (isOver()) {
            System.out.println("A játék véget ért: túl sok jármű ragadt be.");
            System.exit(0);
        }
    }
}
