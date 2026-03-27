package players;

import java.util.ArrayList;
import java.util.List;

import finance.Purchasable;
import skeleton.SkeletonManager;
import vehicles.Bus;
import vehicles.Snowplow;

public class BusDriver {
    private String name;
    private int successfulRounds;
    private int money;
    private List<Purchasable> inventory;
    private List<Bus> ownedBuses;
    private Bus currentBus;

    public BusDriver(String name) {
        this.name = name;
        this.successfulRounds = 0;
        this.money = 500;
        this.inventory = new ArrayList<>();
        this.ownedBuses = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void completeRound() {
        SkeletonManager.call(name + ".completeRound()");
        successfulRounds++;
        SkeletonManager.ret("void");
    }

    public void receiveMoney(int amount) {
        SkeletonManager.call(name + ".receiveMoney(" + amount + ")");
        money += amount;
        SkeletonManager.ret("void (new balance: " + money + ")");
    }

    public void purchase(Purchasable item) {
        SkeletonManager.call(name + ".purchase(item)");
        addToInventory(item);
        SkeletonManager.ret("void");
    }

    public void addToInventory(Purchasable p) {
        SkeletonManager.call(name + ".addToInventory(item)");
        inventory.add(p);
        SkeletonManager.ret("void");
    }

    public void addSnowplow(Snowplow s) {
        SkeletonManager.call(name + ".addSnowplow(snowplow)");
        SkeletonManager.ret("void");
    }

    public void buyBus(Bus bus) {
        SkeletonManager.call(name + ".buyBus(bus)");

        if (money >= 200) {
            money -= 200;
            ownedBuses.add(bus);
            bus.setDriver(this);
            SkeletonManager.ret("void (successful, remaining: " + money + ")");
        } else {
            SkeletonManager.ret("void (insufficient funds, need: 200, have: " + money + ")");
        }
    }

    public void setCurrentBus(Bus bus) {
        SkeletonManager.call(name + ".setCurrentBus(bus)");
        if (ownedBuses.contains(bus) || (bus.getDriver() != null && bus.getDriver() == this)) {
            currentBus = bus;
            SkeletonManager.ret("void (bus set)");
        } else {
            SkeletonManager.ret("void (bus not owned)");
        }
    }

    public int getMoney() {
        return money;
    }

    public int getSuccessfulRounds() {
        return successfulRounds;
    }
}
