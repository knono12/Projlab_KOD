package environment.road;

import java.rmi.server.Skeleton;
import java.util.ArrayList;
import java.util.List;

import environment.lane.Lane;
import environment.nodes.Node;
import skeleton.SkeletonManager;
import vehicles.Vehicle;


/**
 * Absztrakt ősosztály, amely egy utat valósít meg.
 * <p>
 * Fő felelőssége a hozzá tartozó sávok ({@link Lane}) és a végpontokat jelentő
 * csomópontok ({@link Node}) nyilvántartása. Továbbá ez az osztály fogadja be
 * a rálépő járműveket, és indítja el az útspecifikus havazási logikát.
 * </p>
 */
public abstract class Road {
    /** Az út két végpontját jelentő két csomópont listája. */
    protected List<Node> nodes;
    /** Az úthoz tartozó sávok listája. */
    protected List<Lane> lanes;

    /** Az osztály konstruktora, mely inicializálja az úthoz tartozó végpontok és sávok listáját */
    public Road(){
        nodes = new ArrayList<>();
        lanes = new ArrayList<>();
    }

    /**
     * Az havazás logikáját indítja el az úton.
     * <p>
     * Mivel ez egy absztrakt metódus, a pontos viselkedést a leszármazottak
     * (pl. {@link Street}, {@link Tunnel}) valósítják meg.
     * </p>
     */
    public abstract void snowLogic();

    /**
     * Hozzáad egy sávot ({@link Lane}) a jelenlegi útszakaszhoz.
     *
     * @param l A hozzáadandó sáv.
     */
    public void addLane(Lane l) {
        SkeletonManager.call("Road.addLane(Lane)");
        lanes.add(l);
        SkeletonManager.ret("void");
    }

    /**
     * Hozzáad egy csomópontot ({@link Node}) a jelenlegi útszakaszhoz.
     *
     * @param n A hozzáadandó csomópont.
     */
    public void addNode(Node n) {
        SkeletonManager.call("Road.addNode(Node)");
        nodes.add(n);
        SkeletonManager.ret("void");
    }

    /**
     * Visszaadja azokat a sávokat, amelyek a megadott csomópontból indulnak.
     * <p>
     * A metódus végigiterál az út összes sávján, és ellenőrzi, hogy a sáv 
     * belépő csomópontja ({@code fromNode}) megegyezik-e a paraméterként 
     * kapott csomóponttal.
     * </p>
     *
     * @param from A csomópont, ahonnan a szabad sávokat keressük.
     * @return A megadott csomópontból induló sávok listája.
     */
    public List<Lane> getFreeLanes(Node from){
        SkeletonManager.call("Road.getFreeLanes(Node)");

        List<Lane> freeLanes = new ArrayList<>();
        
        for (Lane lane : lanes) {
            if (lane.getFromNode() == from) {
                freeLanes.add(lane);
            }
        }
        SkeletonManager.ret("List<Lane>: freeLanes");
        return freeLanes;
    }

    /** @return Az út két végpontja. */
    public List<Node> getNodes(){
        return nodes;
    }

    /** @return Az úton lévő sávok. */
    public List<Lane> getLanes(){
        return lanes;
    }


}
