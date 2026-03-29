package vehicles;

import java.util.ArrayList;
import java.util.List;

import environment.lane.Lane;
import environment.lane.lanestates.AccidentState;
import environment.nodes.Node;
import environment.nodes.structures.Building;
import environment.nodes.structures.Structure;
import environment.road.Road;
import skeleton.SkeletonManager;

/**
 * Az autót reprezentáló osztály.
 * Képes útvonalat tervezni a munkahelyére és otthonába, és ha elakad, újratervezi azt.
 */
public class Car extends Vehicle {
    boolean isWating;
    Building job;
    Building home; 
    List<Node> route;
    Node destination;

    /**
     * Konstruktor a személyautó inicializálásához.
     * Beállítja az alapértelmezett kezdőállapotot, a kezdőcsomópontot és a célt.
     * @param n Az autó neve a szkeletonhoz.
     */
    public Car(String n){
        super(n);
        isWating = false;
        currentLane = null;
        currentNode = (home != null) ? home.getNode() : null;
        destination = (job != null) ? job.getNode() : null;
        route = new ArrayList<>();
    }

    /**
     * Skeleton-teszteléshez használt konstruktor: megadja a kezdőcsomópontot közvetlenül.
     * @param n Az autó neve.
     * @param startNode A kiindulási csomópont.
     */
    public Car(String n, Node startNode) {
        super(n);
        isWating = false;
        currentLane = null;
        currentNode = startNode;
        destination = null;
        route = new ArrayList<>();
    }

    /**
     * Skeleton-teszteléshez használt konstruktor: megadja a kezdő- és célcsomópontot.
     * @param n Az autó neve.
     * @param startNode A kiindulási csomópont.
     * @param destNode A célcsomópont.
     */
    public Car(String n, Node startNode, Node destNode) {
        super(n);
        isWating = false;
        currentLane = null;
        currentNode = startNode;
        destination = destNode;
        route = new ArrayList<>();
    }

    /**
     * Végrehajtja az autó haladását.
     * Utat és sávot választ, majd megkísérel rálépni. Siker esetén befejezi a mozgást,
     * belép az út másik végpontjába.
     */
    @Override
    public void move(){
        SkeletonManager.call(sName + ".move()");
        Road nextRoad = chooseNextRoad();
        List<Lane> freeLanes = nextRoad.getFreeLanes(currentNode);
        Lane nextLane = chooseNextLane(freeLanes);

        boolean isSuccess = false;
        if (nextLane != null) {
            currentLane = nextLane;
            isSuccess = nextLane.handleVehicle(this);
        }

        if (isSuccess) {
            currentLane.getToNode().enterNode(this);
        }
        
        SkeletonManager.ret("void");
    }

    /**
     * Kiszámítja az útvonalat a megadott forrás és cél csomópontok között.
     * Most csak egy fiktív útvonallépést rögzít.
     * @param from A kiindulási csomópont.
     * @param to A célállomás csomópontja.
     */
    public void findPath(Node from, Node to){
        SkeletonManager.call(getSName() + ".findPath("+ from.getSName() + ", " + to.getSName() + ")");

        route.clear();

        //A szkeletonban csak egy lépést adunk hozzá, hogy a route lista ne legyen üres, és a move() tudjon mivel dolgozni.
        route.add(to);

        SkeletonManager.ret("void");
    }

    /**
     * Újrakalkulálja az útvonalat egy esetleges elakadás után.
     * @return true, ha talált új utat, egyébként false.
     */
    public boolean recalculateRoute(){
        SkeletonManager.call(getSName() + ".recalculateRoute()");

        boolean foundNewPath = SkeletonManager.ask("Talált új útvonalat?");

        if (foundNewPath) {
            findPath(currentNode, destination);
        }

        SkeletonManager.ret(String.valueOf(foundNewPath));
        return foundNewPath;
    }

    /**
     * Megadja, hogy az autó képes-e balesetet szenvedni.
     * @return Mindig true.
     */
    @Override
    public boolean canCollide() {
        SkeletonManager.call(getSName() + ".canCollide()");
        SkeletonManager.ret("true");
        return true;
    }

    /**
     * Végrehajtja az autó károsodását egy ütközés után.
     */
    @Override
    public void sufferCollision() {
        SkeletonManager.call(getSName() + ".sufferCollision()");
        damaged = true;
        SkeletonManager.ret("void");
    }

    /**
     * Beállítja az autót megcsúszó állapotba.
     */
    @Override
    public void slip() {
        isSlipping = true;
    }

    /**
     * Kiértékeli a baleset bekövetkeztét a sávon megcsúszás után.
     * @return true, ha baleset történt, egyébként false.
     */
    @Override
    public boolean evaluateCollisions() {
        SkeletonManager.call(getSName() + ".evaluateCollisions()");

        boolean accident = SkeletonManager.ask("Történt-e ütközés? ");
        if(accident){
            for(Vehicle v : currentLane.getVehicles()){
                v.sufferCollision();
            }
            currentLane.changeState(new AccidentState(currentLane, "accidentState"));
        }

        SkeletonManager.ret(String.valueOf(accident));
        return accident;
    }

    /**
     * Autó lehúzódik az út kezdő csomópontjába.
     * A csomópontba lehúzódott autók listájához hozzáadódik.
     */
    @Override
    public void stop() {
        SkeletonManager.call(getSName() + ".stop()");
        currentNode = currentLane.getFromNode();
        currentLane = null;
        isWating = true;
        currentNode.enterNode(this);
        SkeletonManager.ret("void");
    }

    /**
     * Autónál nem csinál semmit.
     */
    @Override
    public void start() {}

    /**
     * Elvontatja az autót a sávról, ha vége a baleseti állapotnak.
     */
    @Override
    public void accidentOverAction() {
        currentLane.removeVehicle(this);
    }

    /**
     * Kezeli az autó behajtását egy épületbe.
     * @param s A célépület.
     */
    @Override
    public void interactWithStructure(Structure s) {
        SkeletonManager.call(getSName() + ".interactWithStructure(Sructure)");

        s.acceptCar(this);

        SkeletonManager.ret("void");
    }

    /**
     * Kezeli az autó távozását egy épületből.
     * @param s Az elhagyandó épület.
     */
    @Override
    public void departFromStructure(Structure s) {
        SkeletonManager.call(getSName() + ".departFromStructure(" + s.getSName() + ")");
        s.removeCar(this);
        SkeletonManager.ret("void");
    }

    
    
}
