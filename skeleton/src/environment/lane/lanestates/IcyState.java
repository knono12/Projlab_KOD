package environment.lane.lanestates;

import environment.lane.Lane;
import skeleton.SkeletonManager;

/**
 * A jeges sávot reprezentáló állapot.
 * Ezen az úton a járművek megcsúszhatnak és balesetet szenvedhetnek.
 * A jeget söpörni nem lehet, csak jégtörővel (brakeIce), sóval (salt) vagy lángszóróval (melt) lehet megszüntetni.
 */
public class IcyState extends LaneState {

    /**
     * Konstruktor a jeges állapot létrehozásához.
     * @param l A sáv referenciája.
     * @param n Az állapot neve.
     */
    public IcyState(Lane l, String n) {
        super(l, n);
    }
    
    /**
     * A jeges utat nem lehet söprőfejjel feltakarítani.
     * @return Mindig false, a söprés hatástalan.
     */
    @Override
    public boolean sweep(int laneCount){
        SkeletonManager.call(name + ".sweep(" + laneCount + ")");        
        SkeletonManager.ret("false");
        return false;
    }
    
}