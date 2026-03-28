package vehicles;

import java.util.List;

import environment.lane.Lane;
import environment.nodes.Node;
import environment.nodes.structures.Structure;
import environment.road.Road;
import skeleton.SkeletonManager;

public abstract class Vehicle {
    protected boolean damaged = false;
    protected Lane currentLane;
    protected Node currentNode;
    protected boolean isSlipping = false;
    protected String name;

    public Vehicle(String n){
        name = n;
    }

    public abstract boolean canCollide();

    public abstract void sufferCollision();

    public abstract void interactWithStructure(Structure s);

    public abstract void departFromStructure(Structure s);

    public Road chooseNextRoad() {
        SkeletonManager.call(getName() + ".chooseNextRoad()");
        boolean foundNextLane = SkeletonManager.ask("Talált járható utat?");

        if (foundNextLane) {
            SkeletonManager.ret(currentNode.getRoads().get(0).getName());
            // a teszteseteinkben csak egy út van, így mindig azt választja
            return currentNode.getRoads().get(0);
        }
        else{
            SkeletonManager.ret("null");
            return null;
        }
    }

    public Lane chooseNextLane(List<Lane> lanes) {
        SkeletonManager.call(getName() + ".chooseNextLane(lanes)");
        boolean foundNextLane = SkeletonManager.ask("Talált járható sávot?");

        if (foundNextLane) {
            SkeletonManager.ret(lanes.get(0).getName());
            return lanes.get(0);
        }
        else{
            SkeletonManager.ret("null");
            return null;
        }
    }
    

    public void move(){
        SkeletonManager.call(getName() + ".move()");
        chooseNextRoad();
        SkeletonManager.ret("void");
    }

    public abstract void slip();

    public abstract void evaluateCollisions();

    public abstract void stop();

    public abstract void start();

    public abstract void accidentOverAction();

    public void enterNextNode() {
        SkeletonManager.call(getName() + ".chooseNextRoad()");

        currentLane.getToNode().enterNode(this);
        currentLane = null;
        isSlipping = false;

        SkeletonManager.ret("void");
    }

    public String getName(){
        return name;
    }
}
