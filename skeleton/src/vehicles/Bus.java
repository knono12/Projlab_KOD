package vehicles;

import java.util.List;

import environment.lane.Lane;
import environment.nodes.structures.BusStop;
import environment.nodes.structures.Building;
import environment.nodes.structures.Structure;
import environment.road.Road;
import players.BusDriver;
import skeleton.SkeletonManager;

public class Bus extends Vehicle {
    private BusDriver driver;
    private BusStop currentTerminal;
    private BusStop destinationTerminal;
    private int successfulRounds;
    private boolean isMoving;

    public Bus(String name, BusDriver driver) {
        super(name);
        this.driver = driver;
        this.successfulRounds = 0;
        this.isMoving = false;
        this.damaged = false;
    }

    public BusDriver getDriver() {
        return driver;
    }

    public void setDriver(BusDriver driver) {
        this.driver = driver;
    }

    public void setCurrentTerminal(BusStop terminal) {
        this.currentTerminal = terminal;
    }

    public void setDestinationTerminal(BusStop terminal) {
        this.destinationTerminal = terminal;
    }

    public void arriveAtTerminal(BusStop terminal) {
        SkeletonManager.call(name + ".arriveAtTerminal(" + terminal.getName() + ")");

        if (terminal == destinationTerminal) {
            completeRound();
            drawNewDestination();
        }

        SkeletonManager.ret("void");
    }

    public void completeRound() {
        SkeletonManager.call(name + ".completeRound()");
        successfulRounds++;
        if (driver != null) {
            driver.completeRound();
            driver.receiveMoney(50);
        }
        SkeletonManager.ret("void");
    }

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

    @Override
    public boolean canCollide() {
        SkeletonManager.call(name + ".canCollide()");
        boolean result = !damaged;
        SkeletonManager.ret(String.valueOf(result));
        return result;
    }

    @Override
    public boolean surfaceCollision() {
        SkeletonManager.call(name + ".surfaceCollision()");
        boolean result = damaged;
        SkeletonManager.ret(String.valueOf(result));
        return result;
    }

    @Override
    public void interactWithStructure(Structure s) {
        SkeletonManager.call(name + ".interactWithStructure(" + s.getName() + ")");
        s.acceptBus(this);
        SkeletonManager.ret("void");
    }

    @Override
    public void departFromStructure(Structure s) {
        SkeletonManager.call(name + ".departFromStructure(" + s.getName() + ")");
        s.removeBus(this);
        SkeletonManager.ret("void");
    }

    @Override
    public Road chooseNextRoad() {
        SkeletonManager.call(name + ".chooseNextRoad()");
        SkeletonManager.ret("null");
        return null;
    }

    @Override
    public Lane chooseNextLane(List<Lane> lanes) {
        SkeletonManager.call(name + ".chooseNextLane(lanes)");
        SkeletonManager.ret("null");
        return null;
    }

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

    @Override
    public void stop() {
        SkeletonManager.call(name + ".stop()");
        isMoving = false;
        SkeletonManager.ret("void");
    }

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

    @Override
    public void slip() {}

    @Override
    public void evaluateCollisions() {}

    @Override
    public void accidentOver() {}

    @Override
    public void sufferCollision() {
        SkeletonManager.call(name + ".sufferCollision()");
        damaged = true;
        stop();
        SkeletonManager.ret("void");
    }

    public int getSuccessfulRounds() {
        return successfulRounds;
    }
}
