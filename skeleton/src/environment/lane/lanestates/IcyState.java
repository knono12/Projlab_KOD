package environment.lane.lanestates;

import environment.lane.Lane;
import skeleton.SkeletonManager;
import vehicles.Car;
import vehicles.Vehicle;

public class IcyState extends LaneState {

    /**
     * Konstruktor, amely összekapcsolja az állapotot a sávval.
     * @param l A sáv, amely ebben az állapotban van.
     */
    public IcyState(Lane l) {
        super(l);
    }

    /**
     * Kezeli az autó interakcióját a jeges sávval.
     * <p>
     * Mivel a sáv jeges, az autó felhajtás után megcsúszik a {@code slip()} függvényével. 
     * A metódus meghívja az autó {@code evaluateCollisions()} függvényét.
     * Baleset bekövezése esetén sávon lévő összes autó {@code sufferCollision()} függvénye meghívódik,
     * valamint a sáv állapota {@link AccidentState} lesz.
     * </p>
     * @param c Az autó, amely a sávra szeretne hajtani.
     */
    @Override
    public void handleVehicle(Car c) {
        SkeletonManager.call("IcyState.handleVehicle(Car)");

        lane.getFromNode().leaveNode(c);
        lane.enterLane(c);

        c.slip();
        
        c.evaluateCollisions();        

        SkeletonManager.ret("void");
    }

    /**
     * Az időjárási hatások kezelése jeges állapotban.
     * <p>
     * Ebben az állapotban a sáv havazás hatására is jeges állapotban marad.
     * </p>
     */
    @Override
    public void snowLogic() {
        SkeletonManager.call("IcyState.snowLogic()");

        // Nem csinál semmit, mert a jeges sávon nem történik hófelhalmozódás.

        SkeletonManager.ret("void");
    }
    
}
