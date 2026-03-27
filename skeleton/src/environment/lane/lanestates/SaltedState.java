package environment.lane.lanestates;

import environment.lane.Lane;
import skeleton.SkeletonManager;

/**
 * A sózott sávot reprezentáló állapot.
 * A besózott úton a takarítóeszközök már nem tudnak további tisztítást végezni, cserébe egy ideig a hó sem marad meg rajta.
 */
public class SaltedState extends LaneState {

    /**
     * Konstruktor, amely összekapcsolja az állapotot a sávval.
     * * @param l A sáv referenciája.
     * @param n Az állapot neve.
     */
    public SaltedState(Lane l, String n) {
        super(l, n);
    }

    /**
     * Egy sózott sávot nem lehet tovább söpörni.
     * @return Mindig false, a takarítás sikertelen.
     */
    @Override
    public boolean sweep(int laneCount){
        SkeletonManager.call(name + ".sweep(" + laneCount + ")");        
        SkeletonManager.ret("false");
        return false;
    }

    /**
     * Egy sózott sávon nem lehet jeget törni.
     * @return Mindig false, a jégtörés sikertelen.
     */
    @Override
    public boolean brakeIce(){
        SkeletonManager.call(name + ".brakeIce()");
        SkeletonManager.ret("false");
        return false;
    }

    /**
     * Egy sózott sávot nem lehet tovább sózni.
     * @return Mindig false, az olvasztás sikertelen.
     */
    @Override
    public boolean salt(){
        SkeletonManager.call(name + ".salt()");
        SkeletonManager.ret("false");
        return false;
    }

    /**
     * Egy sózott sávon nem lehet havatolvasztani.
     * @return Mindig false, az olvasztás sikertelen.
     */
    @Override
    public boolean melt(){
        SkeletonManager.call(name + ".melt()");
        SkeletonManager.ret("false");
        return false;
    }


}
