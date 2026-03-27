package vehicles;

public abstract class Vehicle {
    protected String sName;

    protected Vehicle(String sName) {
        this.sName = sName;
    }

    public String getSName() {
        return sName;
    }

    public String setSName(String sName) {
        this.sName = sName;
        return this.sName;
    }
}