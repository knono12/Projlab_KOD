package vehicles;
import java.util.List;

import environment.lane.Lane;
import environment.nodes.Node;
import environment.nodes.structures.Structure;
import environment.road.Road;

/**
 * Absztrakt ősosztály a járművek számára.
 * Definiálja a közös állapotokat és az alapvető mozgási metódusokat.
 */
public abstract class Vehicle {
    protected boolean damaged = false;
    protected Lane currentLane;
    protected Node currentNode;
    protected String sName;
    protected Road nextRoad;
    protected Lane nextLane;
    protected boolean isActionSuccess = false;

    /**
     * Konstruktor a jármű nevének beállításához.
     * @param n A jármű neve.
     */
    protected Vehicle(String n){
        sName = n;
    }

    /**
     * Megadja, hogy a jármű képes-e balesetet szenvedni.
     * @return true, ha roncsolódhat, egyébként false.
     */
    public abstract boolean canCollide();

    /**
     * Végrehajtja a járművön a baleset okozta sérüléseket.
     */
    public abstract void sufferCollision();

    /**
     * A jármű belép egy építménybe.
     * @param s Az érintett építmény.
     */
    public abstract void interactWithStructure(Structure s);

    /**
     * A jármű elhagyja az építményt.
     * @param s Az elhagyott építmény.
     */
    public abstract void departFromStructure(Structure s);


    public void setNextRoad(Road r){
        nextRoad = r;
    }

    public void setNextLane(Lane l){
        nextLane = l;
    }
    
    /**
     * A jármű fizikai haladását megvalósító metódus.
     */
    public abstract void moveOntoLane();

    public abstract void moveOntoNode();

    /**
     * Jármű lehúzódásánál hívódik meg.
     */
    public abstract void stop();

    /**
     * Elindítja a járművet a várakozásból.
     */
    public abstract void start();

    /**
     * A baleset elhárítása utáni műveletek végrehajtása.
     */
    public abstract void accidentOverAction();

    /**
     * A jármű beléptetése a következő csomópontba az aktuális sávról.
     */
    public void enterNextNode() {
        Node nextNode = currentLane.getToNode();
        nextNode.enterNode(this);
        currentLane = null;
        currentNode = nextNode;
    }

    /**
     * Visszaadja a jármű azonosító nevét.
     * @return A jármű neve.
     */
    public String getSName(){
        return sName;
    }

    /**
     * Beállítja a jármű azonosító nevét.
     * @param name Az új név.
     * @return A beállított név.
     */
    public String setSName(String name) {
        this.sName = name;
        return this.sName;
    }

    /**
     * Beállítja a jármű aktuális csomópontját (skeleton-teszteléshez).
     * @param n Az új csomópont.
     */
    public void setCurrentNode(Node n) {
        this.currentNode = n;
    }

    /**
     * Beállítja a jármű aktuális sávját (skeleton-teszteléshez).
     * @param l Az új sáv.
     */
    public void setCurrentLane(Lane l) {
        this.currentLane = l;
    }

    public Lane getCurrentLane(){
        return currentLane;
    }
}

