package environment.lane.lanestates;

import environment.lane.Lane;
import skeleton.SkeletonManager;

/**
 * A feltört jeges sávot reprezentáló állapot.
 * Ez az állapot akkor jön létre, ha egy jeges ({@link IcyState}) utat
 * jégtörővel takarítanak.
 */
public class BrokenIceState extends LaneState {

    /**
     * Konstruktor, amely összekapcsolja az állapotot a sávval.
     * 
     * @param l A sáv, amely ebben az állapotban van.
     */
    public BrokenIceState(Lane l, String n) {
        super(l, n);
    }

    /**
     * A már feltört jeget nem lehet újra feltörni.
     * 
     * @return Mindig false, a jégtörés hatástalan.
     */
    @Override
    public boolean brakeIce() {
        SkeletonManager.call(sName + ".brakeIce()");
        SkeletonManager.ret("false");
        return false;
    }

    @Override
    public boolean sweep() {
        SkeletonManager.call(sName + ".sweep()");
        SkeletonManager.ret("false");
        return false;
    }

}