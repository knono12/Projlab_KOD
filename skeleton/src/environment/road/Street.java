package environment.road;

import environment.lane.Lane;

/**
 * Az utcát és hidat megvalósító osztály, amely a {@link Road} leszármazottja.
 * 
 * Az {@code elevation} változója határozza meg, hogy utca vagy híd,
 * valamint hatnak rá az időjárási viszonyok.
 */
public class Street extends Road {
    boolean elevation;

    /** 
     * Konstruktor, amely beállítja, melyik közlekedési szinthez tartozik az út 
     * Ha a paraméter igaz, akkor híd, ha hamis, akkor utca szinthez tartozik az út.
     *
     * @param e A beállítandó közlekedési szint.
     */
    public Street(String name, boolean e){
        super(name);
        elevation = e;
    }

    /**
     * A havazás logikáját hajtja végre a nyitott utcán.
     * Végigiterál a nyilvántartott sávokon ({@link Lane}), és mindegyiken
     * meghívja a {@code snowLogic()} metódust, jelezve a hó hullás szándékát.
     */
    @Override
    public void snowLogic() {
        for (Lane l : lanes) {
            l.snowLogic();
        }
    }
}
