package environment.lane.lanestates;

import environment.lane.Lane;
import skeleton.SkeletonManager;

/**
 * Az enyhén havas sávot reprezentáló állapot.
 * Ezen még minden jármű tud haladni.
 */
public class LightSnowyState extends LaneState {
    /**
     * Konstruktor, amely összekapcsolja az állapotot a sávval.
     * * @param l A sáv referenciája.
     * @param n Az állapot neve.
     */
    public LightSnowyState(Lane l, String n) {
        super(l, n);
    }

    /**
     * A havas úton a jégtörés hatástalan.
     * * @return Mindig false.
     */
    @Override
    public boolean brakeIce(){
        SkeletonManager.call(name + ".brakeIce()");
        SkeletonManager.ret("false");
        return false;
    }

}