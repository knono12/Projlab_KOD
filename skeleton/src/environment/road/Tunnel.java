package environment.road;

/**
 * Az alagutat megvalósító osztály, amely a {@link Road} leszármazottja.
 * Olyan út, amelyet nem befolyásol az időjárás, tiszta marad.
 */
public class Tunnel extends Road {

    public Tunnel(String name) {
        super(name);
    }

    /**
     * A havazás logikáját hajtja végre az alagútban.
     * Szándékosan nem hívja meg a sávokon a havazást.
     */
    @Override
    public void snowLogic() {
        // Nem csinál semmit, mert az alagútba nem esik be a hó.
    }
    
}
