package environment.road;

import java.util.ArrayList;
import java.util.List;

import environment.lane.Lane;
import environment.nodes.Node;
import skeleton.SkeletonManager;


/**
 * Absztrakt ősosztály, amely egy utat valósít meg.
 * <p>
 * Fő felelőssége a hozzá tartozó sávok ({@link Lane}) és a végpontokat jelentő
 * csomópontok ({@link Node}) nyilvántartása. Továbbá ez az osztály fogadja be
 * a rálépő járműveket, és indítja el az útspecifikus havazási logikát.
 * </p>
 */
public abstract class Road {
    protected List<Node> nodes;
    protected List<Lane> lanes;
    String sName;

    /** Az osztály konstruktora, mely inicializálja az úthoz tartozó végpontok és sávok listáját */
    public Road(String name){
        nodes = new ArrayList<>();
        lanes = new ArrayList<>();
        sName = name;
    }

    /**
     * Az havazás logikáját indítja el az úton.
     * Mivel ez egy absztrakt metódus, a pontos viselkedést a leszármazottak
     * (pl. {@link Street}, {@link Tunnel}) valósítják meg.
     */
    public abstract void snowLogic();

    /**
     * Hozzáad egy sávot ({@link Lane}) a jelenlegi útszakaszhoz.
     *
     * @param l A hozzáadandó sáv.
     */
    public void addLane(Lane l) {
        SkeletonManager.call(sName + ".addLane(" + l.getSName() + ")");
        lanes.add(l);
        SkeletonManager.ret("void");
    }

    /**
     * Hozzáad egy csomópontot ({@link Node}) a jelenlegi útszakaszhoz.
     *
     * @param n A hozzáadandó csomópont.
     */
    public void addNode(Node n) {
        SkeletonManager.call(sName + ".addNode(" + n.getSName() + ")");
        nodes.add(n);
        SkeletonManager.ret("void");
    }

    /**
     * Visszaadja azokat a sávokat, amelyek a megadott csomópontból indulnak.
     * 
     * A metódus végigiterál az út összes sávján, és ellenőrzi, hogy a sáv 
     * belépő csomópontja ({@code fromNode}) megegyezik-e a paraméterként 
     * kapott csomóponttal.
     *
     * @param from A csomópont, ahonnan a szabad sávokat keressük.
     * @return A megadott csomópontból induló sávok listája.
     */
    public List<Lane> getFreeLanes(Node from){
        SkeletonManager.call(sName + ".getFreeLanes(" + from.getSName() + ")");

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

    public String getSName(){
        return sName;
    }


}
