package vehicles;

import java.util.List;

import environment.lane.Lane;
import environment.nodes.Node;
import environment.nodes.structures.BusStop;
import environment.nodes.structures.Structure;
import environment.road.Road;
import finance.Purchasable;
import players.BusDriver;
import players.Cleaner;
import skeleton.SkeletonManager;

/**
 * A buszt reprezentáló osztály, amely a {@link Vehicle} ősosztályból származik
 * és a {@link Purchasable} interfészt valósítja meg (megvásárolható).
 * A busz feladata, hogy végállomások között közlekedjen, köröket teljesítsen,
 * és a sikeresen teljesített körökért a {@link BusDriver} buszsofőrt fizesse.
 * Sérült állapotban a busz nem tud mozogni, javításig megáll.
 */
public class Bus extends Vehicle implements Purchasable {

    /** A busz sofőrje, aki a körök teljesítéséért jutalmat kap. */
    private BusDriver driver;
    /** A busz jelenlegi végállomása (ahol éppen tartózkodik). */
    private BusStop currentTerminal;
    /** A busz célja, ahová tartani kíván. */
    private BusStop destinationTerminal;
    /** A sikeresen teljesített körök száma. */
    private int successfulRounds;
    /** Igaz, ha a busz éppen mozgásban van. */
    private boolean isMoving;
    /** A busz ára (Purchasable interfész miatt). */
    int price;

    /**
     * A Bus osztály konstruktora.
     * @param name A busz azonosító neve a szkeletonhoz.
     * @param driver A busz sofőrje.
     */
    public Bus(String name, BusDriver driver) {
        super(name);
        this.driver = driver;
        this.successfulRounds = 0;
        this.isMoving = false;
    }

    /**
     * Skeleton-teszteléshez használt konstruktor: kezdőcsomóponttal.
     * @param name A busz neve.
     * @param driver A busz sofőrje.
     * @param startNode A kezdőcsomópont.
     */
    public Bus(String name, BusDriver driver, Node startNode) {
        super(name);
        this.driver = driver;
        this.successfulRounds = 0;
        this.isMoving = false;
        this.currentNode = startNode;
    }

    /**
     * Visszaadja a busz aktuális sofőrjét.
     * @return A busz sofőrje.
     */
    public BusDriver getDriver() {
        return driver;
    }

    /**
     * Beállítja a busz sofőrjét.
     * @param driver Az új sofőr.
     */
    public void setDriver(BusDriver driver) {
        this.driver = driver;
    }

    /**
     * Beállítja a busz aktuális végállomását.
     * @param terminal Az aktuális végállomás.
     */
    public void setCurrentTerminal(BusStop terminal) {
        this.currentTerminal = terminal;
    }

    /**
     * Beállítja a busz célvégállomását.
     * @param terminal A cél végállomás.
     */
    public void setDestinationTerminal(BusStop terminal) {
        this.destinationTerminal = terminal;
    }

    /**
     * Megérkezés egy végállomásra.
     * Ha a megérkező állomás egyezik a célállomással, kör lezárul és új célállomás kerül kisorsolásra.
     * @param terminal A végállomás, ahová a busz megérkezett.
     */
    public void arriveAtTerminal(BusStop terminal) {
        SkeletonManager.call(sName + ".arriveAtTerminal(" + terminal.getName() + ")");

        if (terminal == destinationTerminal) {
            completeRound();
            drawNewDestination();
        }

        SkeletonManager.ret("void");
    }

    /**
     * Lezár egy kört: növeli a körszámlálót, értesíti a sofőrt és 50 egység jutalmat fizet neki.
     */
    public void completeRound() {
        SkeletonManager.call(sName + ".completeRound()");
        successfulRounds++;
        if (driver != null) {
            driver.completeRound();
            driver.receiveMoney(50);
        }
        SkeletonManager.ret("void");
    }

    /**
     * Új célvégállomást sorsol ki az aktuális végállomástól eltérő megállók közül.
     */
    public void drawNewDestination() {
        SkeletonManager.call(sName + ".drawNewDestination()");

        if (currentTerminal != null) {
            BusStop newDestination = currentTerminal.getRandomBusStop();
            if (newDestination != null) {
                destinationTerminal = newDestination;
                SkeletonManager.ret("void (new destination: " + newDestination.getName() + ")");
                return;
            }
        }

        SkeletonManager.ret("void");
    }

    /**
     * Megjavítja a buszt, ha sérült állapotban van, majd újraindítja.
     */
    public void repair() {
        SkeletonManager.call(sName + ".repair()");

        if (damaged) {
            damaged = false;
            start();
            SkeletonManager.ret("void (repaired)");
        } else {
            SkeletonManager.ret("void (not damaged)");
        }
    }

    /**
     * Megadja, hogy a busz képes-e ütközni.
     * Sérült busz nem tud részt venni ütközésben.
     * @return Igaz, ha a busz nem sérült.
     */
    @Override
    public boolean canCollide() {
        SkeletonManager.call(sName + ".canCollide()");
        boolean result = !damaged;
        SkeletonManager.ret(String.valueOf(result));
        return result;
    }

    /**
     * Interakcióba lép az adott épülettel vagy megállóval.
     * Az épület {@code acceptBus()} metódusán keresztül fogadja a buszt.
     * @param s Az épület vagy megálló, amellyel a busz interakcióba lép.
     */
    @Override
    public void interactWithStructure(Structure s) {
        SkeletonManager.call(sName + ".interactWithStructure(" + s.getName() + ")");
        s.acceptBus(this);
        SkeletonManager.ret("void");
    }

    /**
     * Elhagyja az aktuális épületet vagy megállót.
     * @param s Az elhagyandó épület vagy megálló.
     */
    @Override
    public void departFromStructure(Structure s) {
        SkeletonManager.call(sName + ".departFromStructure(" + s.getName() + ")");
        s.removeBus(this);
        SkeletonManager.ret("void");
    }

    /**
     * Mozgatja a buszt. Sérült busz nem tud mozogni.
     * Ha még nem indult el, meghívja a {@link #start()} metódust.
     */
    @Override
    public void move() {
        SkeletonManager.call(sName + ".move()");

        if (damaged) {
            SkeletonManager.ret("void (damaged, cannot move)");
            return;
        }

        if (!isMoving) {
            start();
        }

        Road nextRoad = chooseNextRoad();
        if (nextRoad == null) {
            SkeletonManager.ret("void");
            return;
        }

        List<Lane> freeLanes = nextRoad.getFreeLanes(currentNode);
        Lane nextLane = chooseNextLane(freeLanes);

        boolean isSuccess = false;
        if (nextLane != null) {
            currentLane = nextLane;
            isSuccess = nextLane.handleVehicle(this);
        }

        if (isSuccess) {
            currentLane.getToNode().enterNode(this);
        }

        SkeletonManager.ret("void");
    }

    /**
     * Megállítja a buszt, az {@code isMoving} jelzőt hamisra állítja.
     */
    @Override
    public void stop() {
        SkeletonManager.call(sName + ".stop()");
        isMoving = false;
        SkeletonManager.ret("void");
    }

    /**
     * Elindítja a buszt, ha nincs sérült állapotban.
     */
    @Override
    public void start() {
        SkeletonManager.call(sName + ".start()");

        if (!damaged) {
            isMoving = true;
            SkeletonManager.ret("void (started)");
        } else {
            SkeletonManager.ret("void (cannot start, damaged)");
        }
    }

    /** Csúszás kezelése – skeletonban nem implementált. */
    @Override
    public void slip() {}

    /**
     * Ütközések kiértékelése csúszás után.
     * @return Mindig false (skeleton).
     */
    @Override
    public boolean evaluateCollisions() {
        return false;
    }

    /** Baleset végének utóhatásai – skeletonban nem implementált. */
    @Override
    public void accidentOverAction() {}

    /**
     * A busz elszenved egy ütközést: sérült állapotba kerül és megáll.
     */
    @Override
    public void sufferCollision() {
        SkeletonManager.call(sName + ".sufferCollision()");
        damaged = true;
        stop();
        SkeletonManager.ret("void");
    }

    /**
     * A buszsofőr megvásárolja a buszt, regisztrálja nála.
     * @param b A vásárlást végző buszsofőr.
     */
    @Override
    public void boughtByBusDriver(BusDriver b) {
        SkeletonManager.call(sName + ".boughtByBusDriver(" + b.getName() + ")");
        b.buyBus(this);
        SkeletonManager.ret("void");
    }

    /**
     * Takarító általi vásárlás – busz esetén nem értelmezett.
     * @param c A vásárlást megkísérlő takarító.
     */
    @Override
    public void boughtByCleaner(Cleaner c) {
        SkeletonManager.call(sName + ".boughtByCleaner(" + c.getSName() + ")");
        SkeletonManager.ret("void");
    }

    /**
     * Visszaadja a busz árát.
     * @return A busz ára.
     */
    @Override
    public int getPrice() {
        return price;
    }

    /**
     * Beállítja a busz árát.
     * @param price Az új ár.
     */
    @Override
    public void setPrice(int price) {
        this.price = price;
    }

    /** @return A busz skeleton-neve ({@link Vehicle#getSName()} delegálva). */
    @Override
    public String getSName() {
        return super.getSName();
    }

    /**
     * Beállítja a busz skeleton-nevét.
     * @param sName Az új név.
     * @return A beállított név.
     */
    @Override
    public String setSName(String sName) {
        return super.setSName(sName);
    }

    /**
     * Visszaadja a sikeresen teljesített körök számát.
     * @return A teljesített körök száma.
     */
    public int getSuccessfulRounds() {
        return successfulRounds;
    }
}