package environment.nodes.structures;

import environment.nodes.Node;
import skeleton.SkeletonManager;
import vehicles.*;

/**
 * Egy épületet (lakóház vagy munkahely) reprezentáló osztály, amely a {@link Structure} ősosztályból származik.
 * Az épület fogadhat autókat, buszokat és hókotrokat, de a skeleton szinten
 * ezek az interakciók csak naplózásra kerülnek.
 */
public class Building extends Structure {
    /** Igaz, ha az épület munkahely; hamis, ha lakóház. */
    private boolean isWorkplace;

    /**
     * A Building osztály konstruktora.
     * @param name Az épület neve.
     * @param isWorkplace Igaz, ha az épület munkahely.
     */
    public Building(String name, boolean isWorkplace) {
        super(name);
        this.isWorkplace = isWorkplace;
    }

    /**
     * Visszaadja, hogy az épület munkahely-e.
     * @return Igaz, ha munkahely.
     */
    public boolean isWorkplace() {
        return isWorkplace;
    }

    /**
     * Visszaadja az épülethez tartozó csomópontot.
     * @return Az épület csomópontja.
     */
    public Node getNode() {
        return null;
    }

    /*
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
    */

    /**
     * Autót fogad az épületben (pl. megérkezés célépülethez).
     * @param c A fogadott autó.
     */
    @Override
    public void acceptCar(Car c) {
        SkeletonManager.call(name + ".acceptCar(" + c.getSName() + ")");
        SkeletonManager.ret("void");
    }

    /**
     * Buszt fogad az épületben – skeleton szinten csak naplózás történik.
     * @param b A fogadott busz.
     */
    @Override
    public void acceptBus(Bus b) {
        SkeletonManager.call(name + ".acceptBus(" + b.getSName() + ")");
        SkeletonManager.ret("void");
    }

    /**
     * Hókotrót fogad az épületben – skeleton szinten csak naplózás történik.
     * @param s A fogadott hókotró.
     */
    @Override
    public void acceptSnowplow(Snowplow s) {
        SkeletonManager.call(name + ".acceptSnowplow(" + s.getSName() + ")");
        SkeletonManager.ret("void");
    }

    /**
     * Autót távolít el az épületből.
     * @param c Az eltávolítandó autó.
     */
    @Override
    public void removeCar(Car c) {
        SkeletonManager.call(name + ".removeCar(" + c.getSName() + ")");
        SkeletonManager.ret("void");
    }

    /**
     * Buszt távolít el az épületből.
     * @param b Az eltávolítandó busz.
     */
    @Override
    public void removeBus(Bus b) {
        SkeletonManager.call(name + ".removeBus(" + b.getSName() + ")");
        SkeletonManager.ret("void");
    }

    /**
     * Hókotrót távolít el az épületből.
     * @param s Az eltávolítandó hókotró.
     */
    @Override
    public void removeSnowplow(Snowplow s) {
        SkeletonManager.call(name + ".removeSnowplow(" + s.getSName() + ")");
        SkeletonManager.ret("void");
    }
}
