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
 * <p>
 * Képes útvonalat tervezni a munkahelyére ({@code job}) és otthonába ({@code home}), és ha elakad, 
 * újratervezi azt.
 * </p>
 */
public class Car extends Vehicle {
    boolean isWating;
    Building job;
    Building home; 
    List<Node> route;
    Node destination;

    public Car(String n){
        super(n);
        isWating = false;
        currentLane = null;
        currentNode = home.getNode();
        destination = job.getNode();
        route = new ArrayList<>();
    }

    public void findPath(Node from, Node to){
        SkeletonManager.call(getName() + ".findPath("+ from.getName() + ", " + to.getName() + ")");

        route.clear();

        //A szkeletonban csak egy lépést adunk hozzá, hogy a route lista ne legyen üres, és a move() tudjon mivel dolgozni.
        route.add(to);

        SkeletonManager.ret("void");
    }

    public boolean recalculateRoute(){
        SkeletonManager.call(getName() + ".recalculateRoute()");

        boolean foundNewPath = SkeletonManager.ask("Talált új útvonalat?");

        if (foundNewPath) {
            findPath(currentNode, destination);
        }

        SkeletonManager.ret(String.valueOf(foundNewPath));
        return foundNewPath;
    }

    @Override
    public boolean canCollide() {
        SkeletonManager.call(getName() + ".canCollide()");
        SkeletonManager.ret("true");
        return true;
    }

    @Override
    public void sufferCollision() {
        SkeletonManager.call(getName() + ".sufferCollision()");
        damaged = true;
        SkeletonManager.ret("void");
    }

    @Override
    public void slip() {
        isSlipping = true;
    }

    @Override
    public void evaluateCollisions() {
        SkeletonManager.call(getName() + ".evaluateCollisions()");

        boolean accident = SkeletonManager.ask("Történt-e ütközés? ");
        if(accident){
            for(Vehicle v : currentLane.getVehicles()){
                v.sufferCollision();
            }
            currentLane.changeState(new AccidentState(currentLane));
        }

        SkeletonManager.ret("void");
    }

    @Override
    public void stop() {
        SkeletonManager.call(getName() + ".stop()");
        currentNode = currentLane.getFromNode();
        currentLane = null;
        isWating = true;
        SkeletonManager.ret("void");
    }

    @Override
    public void start() {
        SkeletonManager.call(getName() + ".start()");
        move();
        SkeletonManager.ret("void");
    }

    @Override
    public void accidentOverAction() {
        currentLane.removeVehicle(this);
    }

    @Override
    public void interactWithStructure(Structure s) {
        SkeletonManager.call(getName() + ".interactWithStructure(Sructure)");

        s.acceptCar(this);

        SkeletonManager.ret("void");
    }

    @Override
    public void departFromStructure(Structure s) {
        SkeletonManager.call(getName() + ".departFromStructure(" + s.getName() + ")");
        s.removeCar(this);
        SkeletonManager.ret("void");
    }

    
    
}
