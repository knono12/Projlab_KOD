package vehicles;
import java.util.List;

import environment.lane.Lane;
import environment.nodes.Node;
import environment.nodes.structures.Structure;
import environment.road.Road;
import skeleton.SkeletonManager;

/**
 * Absztrakt ősosztály a járművek számára.
 * Definiálja a közös állapotokat és az alapvető mozgási metódusokat.
 */
public abstract class Vehicle {
    protected boolean damaged = false;
    protected Lane currentLane;
    protected Node currentNode;
    protected boolean isSlipping = false;
    protected String sName;

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

    /**
     * Kiválasztja a következő utat a csomópontból.
     * @return A kiválasztott út, vagy null, ha nincs járható út.
     */
    public Road chooseNextRoad() {
        SkeletonManager.call(sName + ".chooseNextRoad()");
        boolean foundNextLane = SkeletonManager.ask("Talált járható utat?");

        if (foundNextLane) {
            SkeletonManager.ret(currentNode.getRoads().get(0).getSName());
            // a teszteseteinkben csak egy út van, így mindig azt választja
            return currentNode.getRoads().get(0);
        }
        else{
            SkeletonManager.ret("null");
            return null;
        }
    }

    /**
     * Kiválasztja a következő sávot a lehetséges jó irányú sávok közül.
     * @param lanes A választható jó irányú sávok listája.
     * @return A kiválasztott sáv, vagy null, ha nincs.
     */
    public Lane chooseNextLane(List<Lane> lanes) {
        SkeletonManager.call(sName + ".chooseNextLane(freeLanes)");
        boolean foundNextLane = SkeletonManager.ask("Talált jó irányú sávot?");

        if (foundNextLane) {
            SkeletonManager.ret(lanes.get(0).getSName());
            return lanes.get(0);
        }
        else{
            SkeletonManager.ret("null");
            return null;
        }
    }
    
    /**
     * A jármű fizikai haladását megvalósító metódus.
     */
    public abstract void move();

    /**
     * Beállítja a járművet megcsúszó állapotba.
     */
    public abstract void slip();

    /**
     * Kiértékeli, hogy a megcsúszás következtében történt-e ütközés.
     * @return true, ha baleset történt, egyébként false.
     */
    public abstract boolean evaluateCollisions();

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
        SkeletonManager.call(sName + ".chooseNextRoad()");

        currentLane.getToNode().enterNode(this);
        currentLane = null;
        isSlipping = false;

        SkeletonManager.ret("void");
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

