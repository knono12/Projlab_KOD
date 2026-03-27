package environment.nodes.structures;
import vehicles.*;

public abstract class Structure {
    protected String name;
    
    public Structure(String name) {
        this.name = name;
    }
    
    public String getName() {
        return name;
    }
    
    public abstract void enterStructure(Vehicle v);
    public abstract void leaveStructure(Vehicle v);
    public abstract void acceptCar(Car c);
    public abstract void acceptBus(Bus b);
    public abstract void acceptSnowplow(Snowplow s);
    public abstract void removeCar(Car c);
    public abstract void removeBus(Bus b);
    public abstract void removeSnowplow(Snowplow s);
}
