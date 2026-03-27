package environment.nodes.structures;

import skeleton.SkeletonManager;
import vehicles.*;

public class Building extends Structure {
    private boolean isWorkplace;
    
    public Building(String name, boolean isWorkplace) {
        super(name);
        this.isWorkplace = isWorkplace;
    }
    
    public boolean isWorkplace() {
        return isWorkplace;
    }
    
    @Override
    public void enterStructure(Vehicle v) {
        SkeletonManager.call(name + ".enterStructure(" + v.getName() + ")");
        SkeletonManager.ret("void");
    }
    
    @Override
    public void leaveStructure(Vehicle v) {
        SkeletonManager.call(name + ".leaveStructure(" + v.getName() + ")");
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
        SkeletonManager.ret("void");
    }
    
    @Override
    public void removeSnowplow(Snowplow s) {
        SkeletonManager.call(name + ".removeSnowplow(" + s.getName() + ")");
        SkeletonManager.ret("void");
    }
}
