package environment.road;

import skeleton.SkeletonManager;

/**
 * Az alagutat megvalósító osztály, amely a {@link Road} leszármazottja.
 * <p>
 * Olyan út, amelyet nem befolyásol az időjárás, tiszta marad.
 * </p>
 */
public class Tunnel extends Road {

    /**
     * A havazás logikáját hajtja végre az alagútban.
     * <p>
     * Szándékosan nem hívja meg a sávokon a havazást.
     * </p>
     */
    @Override
    public void snowLogic() {
        SkeletonManager.call("Tunnel.snowLogic()");
        
        // Nem csinál semmit, mert az alagútba nem esik be a hó.
        
        SkeletonManager.ret("void");
    }
    
}
