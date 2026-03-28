package vehicles;

import java.util.List;

import environment.lane.Lane;
import environment.nodes.structures.BusStop;
import environment.nodes.structures.Building;
import environment.nodes.structures.Structure;
import environment.road.Road;
import players.BusDriver;
import skeleton.SkeletonManager;

/**
 * A buszt reprezentáló osztály, amely a {@link Vehicle} ősosztályból származik.
 * A busz feladata, hogy végállomások között közlekedjen, köröket teljesítsen,
 * és a sikeresen teljesített körökért a {@link BusDriver} buszsofőrt fizesse.
 * Sérült állapotban a busz nem tud mozogni, javításig megáll.
 */
public class Bus extends Vehicle {
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
        this.damaged = false;
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
        SkeletonManager.call(name + ".arriveAtTerminal(" + terminal.getName() + ")");

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
        SkeletonManager.call(name + ".completeRound()");
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
        SkeletonManager.call(name + ".drawNewDestination()");

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
        SkeletonManager.call(name + ".repair()");

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
        SkeletonManager.call(name + ".canCollide()");
        boolean result = !damaged;
        SkeletonManager.ret(String.valueOf(result));
        return result;
    }

    /**
     * Megadja, hogy a busz sérült-e (felületi ütközés szempontjából).
     * @return Igaz, ha a busz sérült.
     */
    @Override
    public boolean surfaceCollision() {
        SkeletonManager.call(name + ".surfaceCollision()");
        boolean result = damaged;
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
        SkeletonManager.call(name + ".interactWithStructure(" + s.getName() + ")");
        s.acceptBus(this);
        SkeletonManager.ret("void");
    }

    /**
     * Elhagyja az aktuális épületet vagy megállót.
     * @param s Az elhagyandó épület vagy megálló.
     */
    @Override
    public void departFromStructure(Structure s) {
        SkeletonManager.call(name + ".departFromStructure(" + s.getName() + ")");
        s.removeBus(this);
        SkeletonManager.ret("void");
    }

    /**
     * Kiválasztja a következő utat. Skeletonban nincs implementálva, null-t ad vissza.
     * @return null (skeleton)
     */
    @Override
    public Road chooseNextRoad() {
        SkeletonManager.call(name + ".chooseNextRoad()");
        SkeletonManager.ret("null");
        return null;
    }

    /**
     * Kiválasztja a következő sávot a kapott listából. Skeletonban nincs implementálva, null-t ad vissza.
     * @param lanes Az elérhető sávok listája.
     * @return null (skeleton)
     */
    @Override
    public Lane chooseNextLane(List<Lane> lanes) {
        SkeletonManager.call(name + ".chooseNextLane(lanes)");
        SkeletonManager.ret("null");
        return null;
    }

    /**
     * Mozgatja a buszt. Sérült busz nem tud mozogni.
     * Ha még nem indult el, meghívja a {@link #start()} metódust.
     */
    @Override
    public void move() {
        SkeletonManager.call(name + ".move()");

        if (damaged) {
            SkeletonManager.ret("void (damaged, cannot move)");
            return;
        }

        if (!isMoving) {
            start();
        }

        SkeletonManager.ret("void");
    }

    /**
     * Megállítja a buszt, az {@code isMoving} jelzőt hamisra állítja.
     */
    @Override
    public void stop() {
        SkeletonManager.call(name + ".stop()");
        isMoving = false;
        SkeletonManager.ret("void");
    }

    /**
     * Elindítja a buszt, ha nincs sérült állapotban.
     */
    @Override
    public void start() {
        SkeletonManager.call(name + ".start()");

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

    /** Ütközések kiértékelése – skeletonban nem implementált. */
    @Override
    public void evaluateCollisions() {}

    /** Baleset végének jelzése – skeletonban nem implementált. */
    @Override
    public void accidentOver() {}

    /**
     * A busz elszenved egy ütközést: sérült állapotba kerül és megáll.
     */
    @Override
    public void sufferCollision() {
        SkeletonManager.call(name + ".sufferCollision()");
        damaged = true;
        stop();
        SkeletonManager.ret("void");
    }

    /**
     * Visszaadja a sikeresen teljesített körök számát.
     * @return A teljesített körök száma.
     */
    public int getSuccessfulRounds() {
        return successfulRounds;
    }
}
