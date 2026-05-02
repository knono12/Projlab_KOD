package vehicles;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

import environment.lane.Lane;
import environment.lane.lanestates.AccidentState;
import environment.nodes.Node;
import environment.nodes.structures.Building;
import environment.nodes.structures.Structure;
import environment.road.Road;

/**
 * Az autót reprezentáló osztály.
 * Képes útvonalat tervezni a munkahelyére és otthonába, és ha elakad, újratervezi azt.
 */
public class Car extends Vehicle {
    private boolean isWaiting;
    private Building job;
    private Building home; 
    private List<Node> route;
    private Node destination;

    /**
     * Konstruktor a személyautó inicializálásához.
     * Beállítja az alapértelmezett kezdőállapotot, a kezdőcsomópontot és a célt.
     * @param n Az autó neve a szkeletonhoz.
     */
    public Car(String n){
        super(n);
        isWaiting = false;
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
        isWaiting = false;
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
        isWaiting = false;
        currentLane = null;
        currentNode = startNode;
        destination = destNode;
        route = new ArrayList<>();
    }

    /**
     * Végrehajtja az autó ráhajtását és haladását egy sávon.
     */
    @Override
    public void moveOntoLane(){
        isActionSuccess = false;
        
        if(damaged){
            return;
        }

        if(isWaiting){
            boolean recalculateSuccess = recalculateRoute();
            if(!recalculateSuccess){
                return;
            }
            start();
        }
        
        nextRoad = chooseNextRoad();
        
        if (nextRoad == null) {
            boolean recalculateSuccess = recalculateRoute();
            if(!recalculateSuccess){
                stop();
            }
            return;
        }
        
        List<Lane> freeLanes = nextRoad.getFreeLanes(currentNode);
        nextLane = chooseNextLane(freeLanes);

        if(nextLane == null){
            boolean recalculateSuccess = recalculateRoute();
            if(!recalculateSuccess){
                stop();
            }
            return;
        }

        isActionSuccess = nextLane.handleVehicle(this);

    }

    /*
     * Ha a sávon való áthaladás sikeres, belép az út másik végpontjába.
     */
    @Override
    public void moveOntoNode(){
        if (isActionSuccess && !damaged) {
            enterNextNode();
        } else {
            currentLane = null;
        }
    }

    /**
     * Kiszámítja az útvonalat a megadott forrás és cél csomópontok között.
     * Mivel a városban a csomópontok közötti távolságok megegyeznek, egy módosított BFS algoritmussal valósul meg az útvonalkeresés.
     * Ha nem talált útvonalat üres lista lesz az útvonal változó.
     * @param from A kiindulási csomópont.
     * @param to A célállomás csomópontja.
     */
    public void findPath(Node fromNode, Node toNode) {
        Queue<Node> queue = new LinkedList<>();
        queue.add(fromNode);
        
        Map<Node, Node> cameFrom = new HashMap<>();
        cameFrom.put(fromNode, null);
        
        // gráf bejárása
        while (!queue.isEmpty()) {
            Node currentNode = queue.poll();
            
            // cél elérése
            if (currentNode.equals(toNode)) {
                break;
            }
            
            // szomszédos utak vizsgálata
            for (Road road : currentNode.getRoads()) {
                List<Lane> freeLanes = road.getFreeLanes(currentNode);
                
                // járható és jó irányú sáv
                if (freeLanes != null && !freeLanes.isEmpty()) {
                    Node nextNode = freeLanes.get(0).getToNode();
                    
                    // nem vizsgált csomópont
                    if (!cameFrom.containsKey(nextNode)) {
                        queue.add(nextNode);
                        cameFrom.put(nextNode, currentNode);
                    }
                }
            }
        }
        
        List<Node> newRoute = new ArrayList<>();
        
        // nem találtunk útvonalat
        if (!cameFrom.containsKey(toNode)) {
            this.route = newRoute;
            return;
        }
        
        Node backtrackNode = toNode;
        
        // útvonal összeállítása
        while (backtrackNode != null) {
            newRoute.add(0, backtrackNode); // lista elejére szúrás miatt, jó lesz végül a sorrend
            backtrackNode = cameFrom.get(backtrackNode);
        }
        
        this.route = newRoute;
    }

    /**
     * Beállítja a célcsomópontot és újraszámolja az útvonalat.
     * @param newDestination Az új célcsomópont.
     */
    public void setDestination(Node newDestination) {
        destination = newDestination;
        findPath(currentNode, newDestination);
    }

    /**
     * Újrakalkulálja az útvonalat egy esetleges elakadás után.
     * @return true, ha talált új utat, egyébként false.
     */
    public boolean recalculateRoute(){
        findPath(currentNode, destination);
        return !route.isEmpty();
    }

    /**
     * Megadja, hogy az autó képes-e balesetet szenvedni.
     * @return Mindig true.
     */
    @Override
    public boolean canCollide() {
        return true;
    }

    /**
     * Végrehajtja az autó károsodását egy ütközés után.
     */
    @Override
    public void sufferCollision() {
        damaged = true;
    }

    /**
     * Beállítja az autót megcsúszó állapotba.
     * @return true, ha baleset történt, egyébként false.
     */
    public boolean slip() {
        return evaluateCollisions();
    }

    /**
     * Kiértékeli a baleset bekövetkeztét a sávon megcsúszás után.
     * Baleset megtöténése a sávon lévő járművek számától és típusától függ.
     * @return true, ha baleset történt, egyébként false.
     */
    private boolean evaluateCollisions() {
        List<Vehicle> vehiclesOnLane = currentLane.getVehicles();
        int vehicleCount = vehiclesOnLane.size();

        for (Vehicle vehicle : vehiclesOnLane) {
            if (!vehicle.canCollide()) {
                return false;
            }
        }

        if (vehicleCount < 2) {
            return false;
        }

        int baseProbability = 30;
        int accidentChance = baseProbability + (vehicleCount * 15);

        int randomValue = sharedRandom.nextInt(100) + 1;

        if (randomValue <= accidentChance) {
            
            for (Vehicle vehicle : vehiclesOnLane) {
                vehicle.sufferCollision();
            }
            currentLane.changeState(new AccidentState(currentLane, "accidentState"));
            return true;
        }

        return false;
    }

    /**
     * Autó lehúzódik.
     * A csomópontba lehúzódott autók listájához hozzáadódik.
     */
    @Override
    public void stop() {
        isWaiting = true;
        currentNode.getWaitingVehicles().add(this);
    }

    /**
     * Autó elindul.
     * A csomópontba lehúzódott autók listájából kilép.
     */
    @Override
    public void start() {
        isWaiting = false;
        currentNode.getWaitingVehicles().remove(this);
    }

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
        s.acceptCar(this);
    }

    /**
     * Kezeli az autó távozását egy épületből.
     * @param s Az elhagyandó épület.
     */
    @Override
    public void departFromStructure(Structure s) {
        s.removeCar(this);
    }

    /**
     * Autó az útvonalán haladva kiválasztja a következő utat a csomópontból.
     * @return A kiválasztott út, vagy null, ha nincs járható út.
     */
    private Road chooseNextRoad() {
        while (!route.isEmpty() && route.get(0).equals(currentNode)) {
            route.remove(0);
        }
        if (route.isEmpty()) {
            return null;
        }
        Node nextNode = route.get(0);
        for (Road r : currentNode.getRoads()) {
            if (r.getNodes().contains(nextNode)) {
                route.remove(0);
                return r;
            }
        }
        return null;
    }

    /**
     * Autó automatikusan kiválasztja a következő sávot a lehetséges jó irányú és járható sávok közül.
     * @param lanes A választható jó irányú sávok listája.
     * @return A kiválasztott sáv, vagy null, ha nincs.
     */
    private Lane chooseNextLane(List<Lane> lanes) {
        if (lanes.isEmpty()) {
            return null;
        }
        if (lanes.size() == 1) {
            return lanes.get(0);
        }
        return lanes.get(sharedRandom.nextInt(lanes.size()));
    }
    
    
}
