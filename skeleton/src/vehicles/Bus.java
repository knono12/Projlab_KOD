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
     * 
     * @param name   A busz azonosító neve a szkeletonhoz.
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
     * 
     * @param name      A busz neve.
     * @param driver    A busz sofőrje.
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
     * 
     * @return A busz sofőrje.
     */
    public BusDriver getDriver() {
        return driver;
    }

    /**
     * Beállítja a busz sofőrjét.
     * 
     * @param driver Az új sofőr.
     */
    public void setDriver(BusDriver driver) {
        this.driver = driver;
    }

    /**
     * Beállítja a busz aktuális végállomását.
     * 
     * @param terminal Az aktuális végállomás.
     */
    public void setCurrentTerminal(BusStop terminal) {
        this.currentTerminal = terminal;
    }

    /**
     * Beállítja a busz célvégállomását.
     * 
     * @param terminal A cél végállomás.
     */
    public void setDestinationTerminal(BusStop terminal) {
        this.destinationTerminal = terminal;
    }

    /**
     * Megérkezés egy végállomásra.
     * Ha a megérkező állomás egyezik a célállomással, kör lezárul és új célállomás
     * kerül kisorsolásra.
     * 
     * @param terminal A végállomás, ahová a busz megérkezett.
     */
    public void arriveAtTerminal(BusStop terminal) {
        if (terminal == destinationTerminal) {
            completeRound();
            drawNewDestination();
        }
    }

    /**
     * Lezár egy kört: növeli a körszámlálót, értesíti a sofőrt.
     */
    public void completeRound() {
        if (driver != null) {
            successfulRounds++;
            driver.roundCompletedByBus();
        }
    }

    /**
     * Új célvégállomást sorsol ki az aktuális végállomástól eltérő megállók közül.
     */
    public void drawNewDestination() {
        if (currentTerminal != null) {
            BusStop newDestination = currentTerminal.getRandomBusStop();
            if (newDestination != null) {
                destinationTerminal = newDestination;
                return;
            }
        }
    }

    /**
     * Megjavítja a buszt, ha sérült állapotban van, majd újraindítja.
     */
    public void repair() {
        if (damaged) {
            damaged = false;
            start();
        }
    }

    /**
     * Megadja, hogy a busz képes-e ütközni.
     * Sérült busz nem tud részt venni ütközésben.
     * 
     * @return Igaz, ha a busz nem sérült.
     */
    @Override
    public boolean canCollide() {
        return !damaged;
    }

    /**
     * Interakcióba lép az adott épülettel vagy megállóval.
     * Az épület {@code acceptBus()} metódusán keresztül fogadja a buszt.
     * 
     * @param s Az épület vagy megálló, amellyel a busz interakcióba lép.
     */
    @Override
    public void interactWithStructure(Structure s) {
        s.acceptBus(this);
    }

    /**
     * Elhagyja az aktuális épületet vagy megállót.
     * 
     * @param s Az elhagyandó épület vagy megálló.
     */
    @Override
    public void departFromStructure(Structure s) {
        s.removeBus(this);
    }

    /**
     * Megállítja a buszt, az {@code isMoving} jelzőt hamisra állítja.
     */
    @Override
    public void stop() {
        isMoving = false;
    }

    /**
     * Elindítja a buszt, ha nincs sérült állapotban.
     */
    @Override
    public void start() {
        if (!damaged) {
            isMoving = true;
        }
    }

    /** Baleset végének utóhatásai – skeletonban nem implementált. */
    @Override
    public void accidentOverAction() {
        start();
    }

    /**
     * A busz elszenved egy ütközést: sérült állapotba kerül és megáll.
     */
    @Override
    public void sufferCollision() {
        damaged = true;
        stop();
    }

    /**
     * A buszsofőr megvásárolja a buszt, regisztrálja nála.
     * 
     * @param b A vásárlást végző buszsofőr.
     */
    @Override
    public void boughtByBusDriver(BusDriver b) {
        b.buyBus(this);
    }

    /**
     * Takarító általi vásárlás – busz esetén nem értelmezett.
     * 
     * @param c A vásárlást megkísérlő takarító.
     */
    @Override
    public void boughtByCleaner(Cleaner c) {
        // Buszsofőr nem vásárol buszt, így ez a metódus üres marad.
    }

    /**
     * Visszaadja a busz árát.
     * 
     * @return A busz ára.
     */
    @Override
    public int getPrice() {
        return price;
    }

    /**
     * Beállítja a busz árát.
     * 
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
     * 
     * @param sName Az új név.
     * @return A beállított név.
     */
    @Override
    public String setSName(String sName) {
        return super.setSName(sName);
    }

    /**
     * Visszaadja a sikeresen teljesített körök számát.
     * 
     * @return A teljesített körök száma.
     */
    public int getSuccessfulRounds() {
        return successfulRounds;
    }

    @Override
    public void moveOntoLane() {
        if (nextLane != null) {
            currentLane = nextLane;
            isActionSuccess = nextLane.handleVehicle(this);
        }
    }

    @Override
    public void moveOntoNode() {
        if (isActionSuccess)
            enterNextNode();
    }
}