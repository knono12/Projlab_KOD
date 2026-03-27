package environment.lane.lanestates;

import environment.lane.Lane;
import skeleton.SkeletonManager;
import vehicles.Car;

/**
 * A tiszta sávot reprezentáló állapot.
 * Ebben az állapotban az útszakasz mindenki számára biztonságosan járható. 
 * Mivel nincs rajta hó vagy jég, a takarító eszközök (söprű, jégtörő, stb.) használata hatástalan.
 */
public class ClearState extends LaneState {
    /**
     * Konstruktor a tiszta állapot létrehozásához.
     * @param l A sáv referenciája.
     * @param n Az állapot neve.
     */
    ClearState(Lane l, String n) {
        super(l, n);
    }

    /**
     * Egy tiszta sávot nem lehet tovább söpörni.
     * @return Mindig false, a takarítás sikertelen.
     */
    @Override
    public boolean sweep(int laneCount){
        SkeletonManager.call(name + ".sweep(" + laneCount + ")");        
        SkeletonManager.ret("false");
        return false;
    }

    /**
     * Egy tiszta sávon nincs jég, amit fel lehetne törni.
     * @return Mindig false, a jégtörés sikertelen.
     */
    @Override
    public boolean brakeIce(){
        SkeletonManager.call(name + ".brakeIce()");
        SkeletonManager.ret("false");
        return false;
    }

    // A sószóró végigmegy az úton besózza, és kap is érte pénzt. 
    // Azért lehet, hogy utána egy ideig nem eshet rá hó. 

    /**
     * Egy tiszta sávon nincs mit felolvasztani.
     * @return Mindig false, az olvasztás sikertelen.
     */
    @Override
    public boolean melt(){
        SkeletonManager.call(name + ".melt()");
        SkeletonManager.ret("false");
        return false;
    }
}