package testhandler;

/**
 * A szöveges parancsértelmező tesztelési infrastruktúra belépési pontja.
 * <p>
 * Elindítja a {@link CommandInterpreter}-t, amely soronként olvassa a standard
 * bemenetet, és szöveges parancsok alapján építi fel, majd szimulálja a pályát.
 * </p>
 */
public class TestMain {

    /**
     * Az alkalmazás belépési pontja.
     * <p>
     * Létrehoz egy új {@link CommandInterpreter} példányt, és átadja neki
     * a standard bemenetet és kimenetet.
     * </p>
     *
     * @param args parancssori argumentumok (nem használt)
     * @throws Exception ha a parancsértelmező váratlan kivételt dob
     */
    public static void main(String[] args) throws Exception {
        new CommandInterpreter().run(System.in, System.out);
    }
}
