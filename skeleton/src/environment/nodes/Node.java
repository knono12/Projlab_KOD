package environment.nodes;

import java.util.ArrayList;
import java.util.List;

import environment.nodes.structures.Structure;
import environment.road.Road;
import skeleton.SkeletonManager;
import vehicles.Vehicle;

public class Node {
    protected List<Road> connectedRoads;
    Structure structure;
    List<Vehicle> waitingVehicles;

    public Node(){
        connectedRoads = new ArrayList<>();
        waitingVehicles = new ArrayList<>();
    }

    public void enterNode(Vehicle v){
        SkeletonManager.call("Node.enterNode(Vehicle)");
        waitingVehicles.add(v);
        v.interactWithStructure(structure);
        SkeletonManager.ret("void");
    }

    public void leaveNode(Vehicle v) {
        SkeletonManager.call("Node.leaveNode(Vehicle)");
        waitingVehicles.remove(v);
        SkeletonManager.ret("void");
    }

    public void addRoad(Road r){
        SkeletonManager.call("Node.addRoad(Road)");
        connectedRoads.add(r);
        SkeletonManager.ret("void");
    }

    public void addStructure(Structure s) {
        SkeletonManager.call("Node.addStructure(Structure)");
        structure = s;
        SkeletonManager.ret("void");
    }

    public List<Road> getRoads(){
        return connectedRoads;
    }


}
