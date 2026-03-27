package environment.lane.lanestates;

import environment.lane.Lane;
import skeleton.SkeletonManager;

/**
 * Az erősen havas sávot reprezentáló állapot.
 * Ezen már busz és autó nem közlekedhet.
 */
public class HeavySnowyState extends LaneState {

    /**
     * Konstruktor, amely összekapcsolja az állapotot a sávval.
     * * @param l A sáv referenciája.
     * 
     * @param n Az állapot neve.
     */
    public HeavySnowyState(Lane l, String n) {
        super(l, n);
    }

    /**
     * A havas úton a jégtörés hatástalan.
     * * @return Mindig false.
     */
    @Override
    public boolean brakeIce() {
        SkeletonManager.call(sName + ".brakeIce()");
        SkeletonManager.ret("false");
        return false;
    }

}