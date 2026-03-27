package environment.nodes.structures;

import java.util.*;

import skeleton.SkeletonManager;
import vehicles.*;

public class BusStop extends Structure {
    private List<Bus> waitingBuses;
    private List<Bus> parkedBuses;
    private List<BusStop> allBusStops; // Az összes buszmegálló a városban
    
    public BusStop(String name) {
        super(name);
        this.waitingBuses = new ArrayList<>();
        this.parkedBuses = new ArrayList<>();
        this.allBusStops = new ArrayList<>();
    }
    
    public void setAllBusStops(List<BusStop> stops) {
        this.allBusStops = stops;
    }
    
    @Override
    public void enterStructure(Vehicle v) {
        SkeletonManager.call(name + ".enterStructure(" + v.getName() + ")");
        if (v instanceof Bus) {
            acceptBus((Bus) v);
        }
        SkeletonManager.ret("void");
    }
    
    @Override
    public void leaveStructure(Vehicle v) {
        SkeletonManager.call(name + ".leaveStructure(" + v.getName() + ")");
        if (v instanceof Bus) {
            removeBus((Bus) v);
        }
        SkeletonManager.ret("void");
    }
    
    @Override
    public void acceptCar(Car c) {
        SkeletonManager.call(name + ".acceptCar(" + c.getName() + ")");
        SkeletonManager.ret("void");
    }
    
    @Override
    public void acceptBus(Bus b) {
        SkeletonManager.call(name + ".acceptBus(" + b.getName() + ")");
        if (!waitingBuses.contains(b)) {
            waitingBuses.add(b);
            b.arriveAtTerminal(this);
        }
        SkeletonManager.ret("void");
    }
    
    @Override
    public void acceptSnowplow(Snowplow s) {
        SkeletonManager.call(name + ".acceptSnowplow(" + s.getName() + ")");
        SkeletonManager.ret("void");
    }
    
    @Override
    public void removeCar(Car c) {
        SkeletonManager.call(name + ".removeCar(" + c.getName() + ")");
        SkeletonManager.ret("void");
    }
    
    @Override
    public void removeBus(Bus b) {
        SkeletonManager.call(name + ".removeBus(" + b.getName() + ")");
        waitingBuses.remove(b);
        parkedBuses.remove(b);
        SkeletonManager.ret("void");
    }
    
    @Override
    public void removeSnowplow(Snowplow s) {
        SkeletonManager.call(name + ".removeSnowplow(" + s.getName() + ")");
        SkeletonManager.ret("void");
    }
    
    public void parkBus(Bus b) {
        SkeletonManager.call(name + ".parkBus(" + b.getName() + ")");
        waitingBuses.remove(b);
        parkedBuses.add(b);
        SkeletonManager.ret("void");
    }
    
    public BusStop getRandomBusStop() {
        SkeletonManager.call(name + ".getRandomBusStop()");
        BusStop result = null;
        if (allBusStops != null && allBusStops.size() > 1) {
            Random rand = new Random();
            BusStop newStop;
            do {
                newStop = allBusStops.get(rand.nextInt(allBusStops.size()));
            } while (newStop == this); // Ne ugyanazt a megállót sorsolja
            result = newStop;
        } else {
            result = this;
        }
        SkeletonManager.ret("BusStop (" + (result != null ? result.getName() : "null") + ")");
        return result;
    }
}
