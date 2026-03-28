package environment.road;

import environment.lane.Lane;
import skeleton.SkeletonManager;

/**
 * Az utcát és hidat megvalósító osztály, amely a {@link Road} leszármazottja.
 * <p>
 * Az {@code elevation} változója határozza meg, hogy utca vagy híd,
 * valamint hatnak rá az időjárási viszonyok.
 * </p>
 */
public class Street extends Road {
    boolean elevation;

    /** 
     * Konstruktor, amely beállítja, melyik közlekedési szinthez tartozik az út 
     * <p>
     * Ha a paraméter igaz, akkor híd, ha hamis, akkor utca szinthez tartozik az út.
     * </p>
     *
     * @param e A beállítandó közlekedési szint.
     */
    public Street(boolean e){
        super();
        elevation = e;
    }

    /**
     * A havazás logikáját hajtja végre a nyitott utcán.
     * <p>
     * Végigiterál a nyilvántartott sávokon ({@link Lane}), és mindegyiken
     * meghívja a {@code snowLogic()} metódust, jelezve a hó hullás szándékát.
     * </p>
     */
    @Override
    public void snowLogic() {
        SkeletonManager.call("Street.snowLogic()");
        
        for (Lane l : lanes) {
            l.snowLogic();
        }
        
        SkeletonManager.ret("void");
    }
}
