package testhandler;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import environment.Graph;
import environment.nodes.Node;
import environment.nodes.structures.BusStop;
import players.BusDriver;
import players.Cleaner;
import players.Player;
import vehicles.Bus;
import vehicles.Snowplow;
import vehicles.Vehicle;

/**
 * Privát és csomag-szintű mezők elérését segítő segédosztály.
 * <p>
 * A szimulációs osztályok számos mezőt nem tesznek közzé publikus gettereken
 * keresztül. Ez az osztály Java Reflection segítségével teszi lehetővé e mezők
 * olvasását és írását a tesztelési infrastruktúra számára – az eredeti forrásfájlok
 * módosítása nélkül.
 * </p>
 */
public class ReflectionHelper {

    /**
     * Visszaadja a jármű aktuális csomópontját.
     * <p>
     * A {@code Vehicle.currentNode} mező nem érhető el publikus getteren keresztül.
     * </p>
     *
     * @param v a lekérdezendő jármű
     * @return a jármű aktuális {@link Node}-ja, vagy {@code null}, ha nem érhető el
     */
    public static Node getVehicleCurrentNode(Vehicle v) {
        try {
            Field f = Vehicle.class.getDeclaredField("currentNode");
            f.setAccessible(true);
            return (Node) f.get(v);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Visszaadja az autó célcsomópontját.
     * <p>
     * A {@code Car.destination} mező privát, és nem érhető el publikus getteren keresztül.
     * </p>
     *
     * @param c a lekérdezendő autó
     * @return az autó célcsomópontja, vagy {@code null}, ha nincs beállítva
     */
    public static Node getCarDestination(vehicles.Car c) {
        try {
            Field f = vehicles.Car.class.getDeclaredField("destination");
            f.setAccessible(true);
            return (Node) f.get(c);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Visszaadja a játékos pénztárcájának egyenlegét.
     * <p>
     * A {@code Player.wallet} mező privát; az egyenleget a {@code Wallet.getBalance()}
     * metódussal olvassuk ki.
     * </p>
     *
     * @param p a lekérdezendő játékos
     * @return a játékos pénztárcájának egyenlege, vagy {@code 0}, ha nem érhető el
     */
    public static int getPlayerWalletBalance(Player p) {
        try {
            Field wf = Player.class.getDeclaredField("wallet");
            wf.setAccessible(true);
            finance.Wallet wallet = (finance.Wallet) wf.get(p);
            return wallet.getBalance();
        } catch (Exception e) {
            return 0;
        }
    }

    /**
     * Beállítja a játékos pénztárcájának egyenlegét.
     * <p>
     * A {@code Wallet.setBalance(int)} metódust hívja a belső {@code wallet} mezőn.
     * </p>
     *
     * @param p      a módosítandó játékos
     * @param amount az új egyenleg (nem negatív)
     */
    public static void setPlayerWalletBalance(Player p, int amount) {
        try {
            Field wf = Player.class.getDeclaredField("wallet");
            wf.setAccessible(true);
            finance.Wallet wallet = (finance.Wallet) wf.get(p);
            wallet.setBalance(amount);
        } catch (Exception e) {
            // ignore
        }
    }

    /**
     * Visszaadja a buszvezetőhöz tartozó pénzösszegét.
     * <p>
     * A {@link BusDriver} saját privát {@code money} mezőt tart nyilván,
     * amelyet a {@code getMoney()} publikus metóduson keresztül kérdezünk le.
     * </p>
     *
     * @param b a lekérdezendő buszvezető
     * @return a buszvezető aktuális pénzegyenlege
     */
    public static int getBusDriverMoney(BusDriver b) {
        return b.getMoney();
    }

    /**
     * Beállítja a buszvezető pénzösszegét.
     * <p>
     * Először a {@code BusDriver.money} privát mezőt próbálja közvetlenül módosítani
     * reflectionnel; ha ez nem sikerül, a különbséget a {@code receiveMoney()} metódussal
     * adja hozzá.
     * </p>
     *
     * @param b      a módosítandó buszvezető
     * @param amount az új pénzösszeg
     */
    public static void setBusDriverMoney(BusDriver b, int amount) {
        try {
            Field f = BusDriver.class.getDeclaredField("money");
            f.setAccessible(true);
            f.set(b, amount);
        } catch (Exception e) {
            // fallback: add difference
            int diff = amount - b.getMoney();
            b.receiveMoney(diff);
        }
    }

    /**
     * Törli a takarító hóekéinak listáját.
     * <p>
     * A {@link Cleaner} konstruktora automatikusan létrehoz egy hóekét, amelyre
     * a tesztek során nincs szükség. Ez a metódus a {@code Cleaner.snowplows} listát
     * reflectionnel üríti ki.
     * </p>
     *
     * @param c a módosítandó takarító
     */
    @SuppressWarnings("unchecked")
    public static void clearCleanerSnowplows(Cleaner c) {
        try {
            Field f = Cleaner.class.getDeclaredField("snowplows");
            f.setAccessible(true);
            List<Snowplow> list = (List<Snowplow>) f.get(c);
            list.clear();
        } catch (Exception e) {
            // ignore
        }
    }

    /**
     * Inicializálja a buszvezető saját buszainak listáját, ha az még {@code null}.
     * <p>
     * A {@link BusDriver} konstruktora nem inicializálja az {@code ownedBuses} listát,
     * ami {@code NullPointerException}-t okozna az {@code addBus()} hívásakor.
     * </p>
     *
     * @param b az inicializálandó buszvezető
     */
    public static void initBusDriverOwnedBuses(BusDriver b) {
        try {
            Field f = BusDriver.class.getDeclaredField("ownedBuses");
            f.setAccessible(true);
            if (f.get(b) == null) {
                f.set(b, new ArrayList<vehicles.Bus>());
            }
        } catch (Exception e) {
            // ignore
        }
    }

    /**
     * Beállítja a hóeke tulajdonos takarítóját.
     * <p>
     * A {@code Snowplow.cleaner} mező privát; a hóeke mozgásakor a takarító
     * {@code receiveMoney()} hívása {@code NullPointerException}-t okozna,
     * ha ez a mező nincs beállítva.
     * </p>
     *
     * @param sp a módosítandó hóeke
     * @param c  a hozzárendelendő takarító
     */
    public static void setSnowplowCleaner(Snowplow sp, Cleaner c) {
        try {
            Field f = Snowplow.class.getDeclaredField("cleaner");
            f.setAccessible(true);
            f.set(sp, c);
        } catch (Exception e) {
            // ignore
        }
    }

    /**
     * Beállítja a busz gráf-referenciáját.
     * <p>
     * A {@code Bus.graph} privát mező a busz útvonaltervezéséhez szükséges.
     * Ha nincs beállítva, a {@code drawNewDestination()} {@code NullPointerException}-t dob.
     * </p>
     *
     * @param b a módosítandó busz
     * @param g a beállítandó gráf
     */
    public static void setBusGraph(Bus b, Graph g) {
        try {
            Field f = Bus.class.getDeclaredField("graph");
            f.setAccessible(true);
            f.set(b, g);
        } catch (Exception e) {
            // ignore
        }
    }

    /**
     * Beállítja a jármű sérült állapotát.
     * <p>
     * A {@code Vehicle.damaged} mező privát; ez a metódus lehetővé teszi
     * a tesztek számára a sérülés szimulálását.
     * </p>
     *
     * @param v       a módosítandó jármű
     * @param damaged {@code true}, ha a jármű sérült; {@code false} egyébként
     */
    public static void setVehicleDamaged(Vehicle v, boolean damaged) {
        try {
            Field f = Vehicle.class.getDeclaredField("damaged");
            f.setAccessible(true);
            f.set(v, damaged);
        } catch (Exception e) {
            // ignore
        }
    }

    /**
     * Visszaadja a busz aktuális végállomását.
     * <p>A {@code Bus.currentTerminal} mező privát, nincs publikus getter.</p>
     *
     * @param b a lekérdezendő busz
     * @return az aktuális {@link BusStop}, vagy {@code null}, ha nem érhető el
     */
    public static BusStop getBusCurrentTerminal(Bus b) {
        try {
            Field f = Bus.class.getDeclaredField("currentTerminal");
            f.setAccessible(true);
            return (BusStop) f.get(b);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Visszaadja a busz célvégállomását.
     * <p>A {@code Bus.destinationTerminal} mező privát, nincs publikus getter.</p>
     *
     * @param b a lekérdezendő busz
     * @return a cél {@link BusStop}, vagy {@code null}, ha nem érhető el
     */
    public static BusStop getBusDestinationTerminal(Bus b) {
        try {
            Field f = Bus.class.getDeclaredField("destinationTerminal");
            f.setAccessible(true);
            return (BusStop) f.get(b);
        } catch (Exception e) {
            return null;
        }
    }
}
