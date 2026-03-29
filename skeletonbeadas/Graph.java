package environment;

import java.util.ArrayList;
import java.util.List;

import environment.nodes.Node;
import environment.road.Road;
import skeleton.SkeletonManager;

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

    /**
     * A Graph osztály konstruktora.
     * Inicializálja az üres csomópont- és útlistákat.
     */
    public Graph() {
        nodes = new ArrayList<>();
        roads = new ArrayList<>();
    }

    /**
     * Hozzáad egy csomópontot a gráfhoz.
     * @param n A hozzáadandó csomópont.
     */
    public void addNode(Node n) {
        SkeletonManager.call("graph.addNode(" + n.getSName() + ")");
        nodes.add(n);
        SkeletonManager.ret("void");
    }

    /**
     * Hozzáad egy utat a gráfhoz.
     * @param r A hozzáadandó út.
     */
    public void addRoad(Road r) {
        SkeletonManager.call("graph.addRoad(" + r.getSName() + ")");
        roads.add(r);
        SkeletonManager.ret("void");
    }

    /** @return A gráf csomópontjainak listája. */
    public List<Node> getNodes() {
        return nodes;
    }

    /** @return A gráf útjainak listája. */
    public List<Road> getRoads() {
        return roads;
    }
}
