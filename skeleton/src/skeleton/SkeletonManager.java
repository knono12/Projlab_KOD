package skeleton;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import accessories.attachments.*;
import accessories.fuels.*;
import environment.Graph;
import environment.lane.Lane;
import environment.lane.lanestates.*;
import environment.nodes.Node;
import environment.nodes.structures.*;
import environment.road.*;
import players.*;
import vehicles.*;

/**
 * A skeleton program belépési pontja és nyomkövető segédosztálya.
 * <p>
 * Felelőssége háromrétű:
 * <ul>
 *   <li>Konzolos menü megjelenítése a tesztelőnek.</li>
 *   <li>Hívási hierarchia nyomkövetése {@link #call(String)} és {@link #ret(String)} metódusokkal.</li>
 *   <li>Interaktív igen/nem kérdések feltétele: {@link #ask(String)}.</li>
 * </ul>
 */
public class SkeletonManager {

    private static int depth = 0;
    private static Scanner scanner = new Scanner(System.in);

    // =========================================================================
    // Nyomkövető segédmetódusok
    // =========================================================================

    public static void call(String msg) {
        for (int i = 0; i < depth; i++) System.out.print("   ");
        System.out.println("-> " + msg);
        depth++;
    }

    public static void ret(String msg) {
        depth--;
        for (int i = 0; i < depth; i++) System.out.print("   ");
        System.out.println("<- " + msg);
    }

    public static boolean ask(String question) {
        for (int i = 0; i < depth; i++) System.out.print("   ");
        System.out.print("? " + question + " (I/N): ");
        System.out.flush();
        String answer = scanner.nextLine().trim().toUpperCase();
        return answer.equals("I");
    }

    // =========================================================================
    // Főmenü
    // =========================================================================

    public static void run() {
        System.out.println("=== Skeleton Tesztelő ===");
        System.out.println(" 0. Kilépés");
        System.out.println(" 1. Játék inicializálása");
        System.out.println(" 2. Autó haladása tiszta sávon");
        System.out.println(" 3. Busz megérkezik végállomására");
        System.out.println(" 4. Autó elakad");
        System.out.println(" 5. Busz javítása");
        System.out.println(" 6. Biokerozin vásárlása");
        System.out.println(" 7. Hókotrófej vásárlása");
        System.out.println(" 8. Busz vásárlása");
        System.out.println(" 9. Autó elindul csomópontból");
        System.out.println("10. Havazás az alagútban");
        System.out.println("11. Havazás a tiszta utcán");
        System.out.println("12. Havazás az enyhén havas utcán");
        System.out.println("13. Busz új végállomásának sorsolása");
        System.out.println("14. Enyhén havas út megfagyása");
        System.out.println("15. Só hatásának elmúlása");
        System.out.println("16. Autó haladása / ütközése jeges úton");
        System.out.println("17. Söprőfej használata");
        System.out.println("18. Hányófej használata");
        System.out.println("19. Jégtörő használata");
        System.out.println("20. Út sózása");
        System.out.println("21. Sárkányfej használata");
        System.out.println("22. Hókotró fejcserélése");
        System.out.println("23. Roncsautók elszállítása");
        System.out.println("24. Hókotró elakadása balesetes úton");
        System.out.println("25. Sikertelen takarítás");
        System.out.println("26. Új kotrófej vásárlása és cserélése");

        while (true) {
            System.out.print("\nVálasszon tesztesetet (0-26): ");
            String input = scanner.nextLine().trim();
            int choice;
            try {
                choice = Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("Érvénytelen bemenet.");
                return;
            }
            if (choice == 0) break;
            System.out.println();
            depth = 0;
            skeletonLogic(choice);
        }
    }

    public static void skeletonLogic(int testCase) {
        switch (testCase) {
            case 1:  testGameInit();           break;
            case 2:  testCarOnClearLane();     break;
            case 3:  testBusArrival();         break;
            case 4:  testCarGetStuck();        break;
            case 5:  testBusRepair();          break;
            case 6:  testBiokerosene();        break;
            case 7:  testAttachmentPurchase(); break;
            case 8:  testBusPurchase();        break;
            case 9:  testCarRestart();         break;
            case 10: testSnowInTunnel();       break;
            case 11: testSnowOnClearStreet();  break;
            case 12: testSnowOnLightSnowy();   break;
            case 13: testBusNewDestination();  break;
            case 14: testLightSnowyFreeze();   break;
            case 15: testSaltDisappear();      break;
            case 16: testCarOnIcyLane();       break;
            case 17: testSweeperHead();        break;
            case 18: testSnowBladeHead();      break;
            case 19: testIceBreaker();         break;
            case 20: testGritter();            break;
            case 21: testFlamethrower();       break;
            case 22: testHeadChange();         break;
            case 23: testWreckRemoval();       break;
            case 24: testSnowplowOnAccident(); break;
            case 25: testFailedCleaning();     break;
            case 26: testAttachmentPurchaseAndChangeAndClean(); break;
            default: System.out.println("Ismeretlen teszteset: " + testCase);
        }
    }

    // =========================================================================
    // Segédmetódusok
    // =========================================================================

    /** Alap kétcsomópontos pályát épít: n1 – st (Street) – n2, l sávval. */
    private static Object[] buildBasicRoad() {
        Node n1 = new Node("n1");
        Node n2 = new Node("n2");
        Street st = new Street("st", false);
        Lane l = new Lane(n1, n2, "l");
        st.addLane(l);
        st.addNode(n1);
        st.addNode(n2);
        n1.addRoad(st);
        n2.addRoad(st);
        return new Object[]{n1, n2, st, l};
    }

    /** Cleaner 500 egyenleggel. */
    private static Cleaner buildCleaner() {
        Cleaner cl = new Cleaner("cl", "Kovács János", new ArrayList<>());
        clearAutoSnowplow(cl);
        cl.receiveMoney(500);
        return cl;
    }

    /** Snowplow a megadott takarítóhoz rendelve. */
    private static Snowplow buildSnowplow(Cleaner cleaner) {
        Snowplow sp = new Snowplow(100, "sp");
        setField(sp, Snowplow.class, "cleaner", cleaner);
        cleaner.addSnowplow(sp);
        return sp;
    }

    /** Jármű mozgása: automatikusan kiválaszt egy passable sávot a csomópontból. */
    private static void performMove(Vehicle v, Node from) {
        for (Road r : from.getRoads()) {
            List<Lane> free = r.getFreeLanes(from);
            if (!free.isEmpty()) {
                v.setNextRoad(r);
                v.setNextLane(free.get(0));
                break;
            }
        }
        v.moveOntoLane();
        v.moveOntoNode();
    }

    /** Hókotró mozgása: snowplow-passable sávokat keres. */
    private static void performSnowplowMove(Snowplow sp, Node from) {
        for (Road r : from.getRoads()) {
            List<Lane> free = r.getFreeLanesSnowplow(from);
            if (!free.isEmpty()) {
                sp.setNextRoad(r);
                sp.setNextLane(free.get(0));
                break;
            }
        }
        sp.moveOntoLane();
        sp.moveOntoNode();
    }

    // =========================================================================
    // TC1  Játék inicializálása
    // =========================================================================
    private static void testGameInit() {
        Graph graph = new Graph();
        Node n1 = new Node("n1");
        Node n2 = new Node("n2");
        Street st = new Street("st", false);
        graph.addNode(n1);
        graph.addNode(n2);
        graph.addRoad(st);
        st.addNode(n1);
        st.addNode(n2);
        n1.addRoad(st);
        n2.addRoad(st);
    }

    // =========================================================================
    // TC2  Autó haladása tiszta sávon
    // =========================================================================
    private static void testCarOnClearLane() {
        Object[] road = buildBasicRoad();
        Node n1 = (Node) road[0];
        Node n2 = (Node) road[1];
        Lane l  = (Lane) road[3];

        Car c1 = new Car("c1", n1, n2);
        c1.setNextLane(l);
        c1.moveOntoLane();
        c1.moveOntoNode();
    }

    // =========================================================================
    // TC3  Busz megérkezik végállomására
    // =========================================================================
    private static void testBusArrival() {
        Graph graph = new Graph();
        Object[] road = buildBasicRoad();
        Node n1 = (Node) road[0];
        Node n2 = (Node) road[1];
        Lane l  = (Lane) road[3];

        BusStop bs1 = new BusStop("bs1");
        BusStop bs2 = new BusStop("bs2");
        graph.addBusStop(bs1);
        graph.addBusStop(bs2);
        n2.addStructure(bs2);

        BusDriver bd = new BusDriver("bd", "Tóth Gábor");
        Bus bus = new Bus("bus", bd, n1);
        setField(bus, Bus.class, "graph", graph);
        bus.setCurrentTerminal(bs1);
        bus.setDestinationTerminal(bs2);

        bus.setNextLane(l);
        bus.moveOntoLane();
        bus.moveOntoNode();
    }

    // =========================================================================
    // TC4  Autó elakad
    // =========================================================================
    private static void testCarGetStuck() {
        Object[] road = buildBasicRoad();
        Node n1 = (Node) road[0];
        Node n2 = (Node) road[1];
        Lane l  = (Lane) road[3];
        l.changeState(new AccidentState(l, "accidentState"));

        Car c1 = new Car("c1", n1, n2);
        performMove(c1, n1);
    }

    // =========================================================================
    // TC5  Busz javítása
    // =========================================================================
    private static void testBusRepair() {
        BusDriver bd = new BusDriver("bd", "Tóth Gábor");
        Bus bus = new Bus("bus", bd);

        bus.sufferCollision();
        bus.moveOntoLane();  // sérült – nem tud mozdulni
        bus.repair();
    }

    // =========================================================================
    // TC6  Biokerozin vásárlása
    // =========================================================================
    private static void testBiokerosene() {
        Cleaner cleaner = buildCleaner();
        BioKerosene kerosene = new BioKerosene(50, "kerozin", 10);

        boolean hasFunds = ask("Van-e elég pénze a takarítónak?");
        if (hasFunds) {
            cleaner.purchaseItem(kerosene);
        }
    }

    // =========================================================================
    // TC7  Hókotrófej vásárlása
    // =========================================================================
    private static void testAttachmentPurchase() {
        Cleaner cleaner = buildCleaner();
        IceBrakerAttachment att = new IceBrakerAttachment("jegoro", 20);

        boolean hasFunds = ask("Van-e elég pénze a takarítónak?");
        if (hasFunds) {
            cleaner.purchaseItem(att);
        }
    }

    // =========================================================================
    // TC8  Busz vásárlása
    // =========================================================================
    private static void testBusPurchase() {
        BusDriver bd = new BusDriver("bd", "Tóth Gábor");
        bd.receiveMoney(500);
        initOwnedBuses(bd);
        Bus bus = new Bus("myBus", bd);
        bus.setPrice(200);

        boolean hasFunds = ask("Van-e elég pénze a buszsofőrnek?");
        if (hasFunds) {
            bus.boughtByBusDriver(bd);
        }
    }

    // =========================================================================
    // TC9  Autó elindul csomópontból
    // =========================================================================
    private static void testCarRestart() {
        Object[] road = buildBasicRoad();
        Node n1 = (Node) road[0];
        Node n2 = (Node) road[1];
        Lane l  = (Lane) road[3];

        Car c1 = new Car("c1", n1, n2);
        c1.findPath(n1, n2);
        c1.setNextLane(l);
        c1.moveOntoLane();
        c1.moveOntoNode();
    }

    // =========================================================================
    // TC10  Havazás az alagútban
    // =========================================================================
    private static void testSnowInTunnel() {
        Tunnel tunnel = new Tunnel("tunnel");
        tunnel.snowLogic();
    }

    // =========================================================================
    // TC11  Havazás a tiszta utcán
    // =========================================================================
    private static void testSnowOnClearStreet() {
        Node n1 = new Node("n1");
        Node n2 = new Node("n2");
        Street st = new Street("st", false);
        Lane l = new Lane(n1, n2, "l");
        st.addLane(l);
        st.snowLogic();
    }

    // =========================================================================
    // TC12  Havazás az enyhén havas utcán
    // =========================================================================
    private static void testSnowOnLightSnowy() {
        Node n1 = new Node("n1");
        Node n2 = new Node("n2");
        Street st = new Street("st", false);
        Lane l = new Lane(n1, n2, "l");
        l.changeState(new LightSnowyState(l, "lightSnowyState"));
        st.addLane(l);
        st.snowLogic();
    }

    // =========================================================================
    // TC13  Busz új végállomásának sorsolása
    // =========================================================================
    private static void testBusNewDestination() {
        Graph graph = new Graph();
        BusStop bs1 = new BusStop("bs1");
        BusStop bs2 = new BusStop("bs2");
        graph.addBusStop(bs1);
        graph.addBusStop(bs2);

        BusDriver bd = new BusDriver("bd", "Tóth Gábor");
        Bus bus = new Bus("bus", bd);
        setField(bus, Bus.class, "graph", graph);
        bus.setCurrentTerminal(bs1);
        bus.setDestinationTerminal(bs2);

        bs2.acceptBus(bus);
    }

    // =========================================================================
    // TC14  Enyhén havas út megfagyása
    // =========================================================================
    private static void testLightSnowyFreeze() {
        Object[] road = buildBasicRoad();
        Node n1 = (Node) road[0];
        Node n2 = (Node) road[1];
        Lane l  = (Lane) road[3];
        l.changeState(new LightSnowyState(l, "lightSnowyState"));

        Car c1 = new Car("c1", n1, n2);
        l.handleVehicle(c1);
    }

    // =========================================================================
    // TC15  Só hatásának elmúlása
    // =========================================================================
    private static void testSaltDisappear() {
        Node n1 = new Node("n1");
        Node n2 = new Node("n2");
        Street st = new Street("st", false);
        Lane l = new Lane(n1, n2, "l");
        l.changeState(new SaltedState(l, "saltedState"));
        st.addLane(l);
        st.snowLogic();
    }

    // =========================================================================
    // TC16  Autó haladása / ütközése jeges úton
    // =========================================================================
    private static void testCarOnIcyLane() {
        Object[] road = buildBasicRoad();
        Node n1 = (Node) road[0];
        Node n2 = (Node) road[1];
        Lane l  = (Lane) road[3];
        l.changeState(new IcyState(l, "icyState"));

        Car c2 = new Car("c2", n1, n2);
        l.enterLane(c2);

        Car c1 = new Car("c1", n1, n2);
        c1.setNextLane(l);
        c1.moveOntoLane();
        c1.moveOntoNode();
    }

    // =========================================================================
    // TC17  Söprőfej használata
    // =========================================================================
    private static void testSweeperHead() {
        Object[] road = buildBasicRoad();
        Node n1 = (Node) road[0];
        Lane l  = (Lane) road[3];
        l.changeState(new LightSnowyState(l, "lightSnowyState"));

        Cleaner cleaner = buildCleaner();
        Snowplow sp = buildSnowplow(cleaner);
        sp.changeAttachment(new SweeperAttachment("sweeper", 20));
        sp.setCurrentNode(n1);

        performSnowplowMove(sp, n1);
    }

    // =========================================================================
    // TC18  Hányófej (SnowBlade) használata
    // =========================================================================
    private static void testSnowBladeHead() {
        Object[] road = buildBasicRoad();
        Node n1 = (Node) road[0];
        Lane l  = (Lane) road[3];
        l.changeState(new LightSnowyState(l, "lightSnowyState"));

        Cleaner cleaner = buildCleaner();
        Snowplow sp = buildSnowplow(cleaner);
        sp.changeAttachment(new SnowBladeAttachment("hanyofej", 15));
        sp.setCurrentNode(n1);

        performSnowplowMove(sp, n1);
    }

    // =========================================================================
    // TC19  Jégtörő használata
    // =========================================================================
    private static void testIceBreaker() {
        Object[] road = buildBasicRoad();
        Node n1 = (Node) road[0];
        Lane l  = (Lane) road[3];
        l.changeState(new IcyState(l, "icyState"));

        Cleaner cleaner = buildCleaner();
        Snowplow sp = buildSnowplow(cleaner);
        sp.changeAttachment(new IceBrakerAttachment("jegoro", 25));
        sp.setCurrentNode(n1);

        performSnowplowMove(sp, n1);
    }

    // =========================================================================
    // TC20  Út sózása
    // =========================================================================
    private static void testGritter() {
        Object[] road = buildBasicRoad();
        Node n1 = (Node) road[0];

        Salt salt = new Salt("so", 5, 10);
        GritterAttachment gritter = new GritterAttachment("soszoro", 30);
        gritter.setSalt(salt);

        Cleaner cleaner = buildCleaner();
        Snowplow sp = buildSnowplow(cleaner);
        sp.changeAttachment(gritter);
        sp.setCurrentNode(n1);

        performSnowplowMove(sp, n1);
    }

    // =========================================================================
    // TC21  Sárkányfej (Flamethrower) használata
    // =========================================================================
    private static void testFlamethrower() {
        Object[] road = buildBasicRoad();
        Node n1 = (Node) road[0];
        Lane l  = (Lane) road[3];
        l.changeState(new IcyState(l, "icyState"));

        BioKerosene kerosene = new BioKerosene(50, "kerozin", 10);
        FlamethrowerAttachment flame = new FlamethrowerAttachment("sarkanyFej", 50);
        flame.setBioKerosene(kerosene);

        Cleaner cleaner = buildCleaner();
        Snowplow sp = buildSnowplow(cleaner);
        sp.changeAttachment(flame);
        sp.setCurrentNode(n1);

        performSnowplowMove(sp, n1);
    }

    // =========================================================================
    // TC22  Hókotró fejcserélése az állomáson
    // =========================================================================
    private static void testHeadChange() {
        Node n1 = new Node("n1");
        SnowplowStation station = new SnowplowStation("station", n1);

        Cleaner cleaner = buildCleaner();
        Snowplow sp = buildSnowplow(cleaner);

        station.acceptSnowplow(sp);
    }

    // =========================================================================
    // TC23  Roncsautók elszállítása
    // =========================================================================
    private static void testWreckRemoval() {
        Node n1 = new Node("n1");
        Node n2 = new Node("n2");
        Street st = new Street("st", false);
        Lane l = new Lane(n1, n2, "l");
        l.changeState(new AccidentState(l, "accidentState"));
        st.addLane(l);

        Car c1 = new Car("c1", n1, n2);
        l.enterLane(c1);
        c1.setCurrentLane(l);

        st.snowLogic();
    }

    // =========================================================================
    // TC24  Hókotró elakadása balesetes úton
    // =========================================================================
    private static void testSnowplowOnAccident() {
        Object[] road = buildBasicRoad();
        Node n1 = (Node) road[0];
        Lane l  = (Lane) road[3];
        l.changeState(new AccidentState(l, "accidentState"));

        Cleaner cleaner = buildCleaner();
        Snowplow sp = buildSnowplow(cleaner);
        sp.changeAttachment(new SweeperAttachment("sweeper", 20));
        sp.setCurrentNode(n1);

        performSnowplowMove(sp, n1);
    }

    // =========================================================================
    // TC25  Sikertelen takarítás (söprő jeges úton)
    // =========================================================================
    private static void testFailedCleaning() {
        Object[] road = buildBasicRoad();
        Node n1 = (Node) road[0];
        Lane l  = (Lane) road[3];
        l.changeState(new IcyState(l, "icyState"));

        Cleaner cleaner = buildCleaner();
        Snowplow sp = buildSnowplow(cleaner);
        sp.changeAttachment(new SweeperAttachment("sweeper", 20));
        sp.setCurrentNode(n1);

        performSnowplowMove(sp, n1);
    }

    // =========================================================================
    // TC26  Új kotrófej vásárlása, cserélése és takarítás
    // =========================================================================
    private static void testAttachmentPurchaseAndChangeAndClean() {
        Object[] road = buildBasicRoad();
        Node n1 = (Node) road[0];
        Lane l  = (Lane) road[3];

        SnowplowStation station = new SnowplowStation("station", n1);
        Cleaner cleaner = buildCleaner();
        Snowplow sp = buildSnowplow(cleaner);

        station.acceptSnowplow(sp);

        l.changeState(new IcyState(l, "icyState"));
        sp.setCurrentNode(n1);
        performSnowplowMove(sp, n1);
    }

    // =========================================================================
    // Reflection segédek (privát mezők eléréséhez)
    // =========================================================================

    private static void setField(Object obj, Class<?> clazz, String fieldName, Object value) {
        try {
            Field f = clazz.getDeclaredField(fieldName);
            f.setAccessible(true);
            f.set(obj, value);
        } catch (Exception e) {
            // silently ignore
        }
    }

    @SuppressWarnings("unchecked")
    private static void clearAutoSnowplow(Cleaner c) {
        try {
            Field f = Cleaner.class.getDeclaredField("snowplows");
            f.setAccessible(true);
            ((List<Snowplow>) f.get(c)).clear();
        } catch (Exception e) {
            // silently ignore
        }
    }

    private static void initOwnedBuses(BusDriver bd) {
        try {
            Field f = BusDriver.class.getDeclaredField("ownedBuses");
            f.setAccessible(true);
            if (f.get(bd) == null) f.set(bd, new ArrayList<Bus>());
        } catch (Exception e) {
            // silently ignore
        }
    }
}
