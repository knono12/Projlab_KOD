package skeleton;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import accessories.attachments.Attachment;
import accessories.attachments.FlamethrowerAttachment;
import accessories.attachments.GritterAttachment;
import accessories.attachments.IceBrakerAttachment;
import accessories.attachments.SnowBladeAttachment;
import accessories.attachments.SweeperAttachment;
import accessories.fuels.BioKerosene;
import accessories.fuels.Salt;
import environment.Graph;
import environment.lane.Lane;
import environment.lane.lanestates.AccidentState;
import environment.lane.lanestates.IcyState;
import environment.lane.lanestates.LightSnowyState;
import environment.lane.lanestates.SaltedState;
import environment.nodes.Node;
import environment.nodes.structures.BusStop;
import environment.nodes.structures.SnowplowStation;
import environment.road.Street;
import environment.road.Tunnel;
import finance.Wallet;
import players.BusDriver;
import players.Cleaner;
import vehicles.Bus;
import vehicles.Car;
import vehicles.Snowplow;

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

    /** Az aktuális hívási mélység (behúzás szintje). */
    private static int depth = 0;
    /** A szabványos bemenetről olvasó szkenner. */
    private static Scanner scanner = new Scanner(System.in);

    /**
     * Egy metódushívás belépési pontját naplózza.
     * @param msg A naplózandó metódushívás leírása.
     */
    public static void call(String msg) {
        for (int i = 0; i < depth; i++)
            System.out.print("   ");
        System.out.println("-> " + msg);
        depth++;
    }

    /**
     * Egy metódushívás visszatérési pontját naplózza.
     * @param msg A visszatérési érték szöveges reprezentációja.
     */
    public static void ret(String msg) {
        depth--;
        for (int i = 0; i < depth; i++)
            System.out.print("   ");
        System.out.println("<- " + msg);
    }

    /**
     * Igen/nem kérdést tesz fel a tesztelőnek a konzolon keresztül.
     * @param question A feltett kérdés szövege.
     * @return {@code true}, ha a felhasználó {@code I}-vel válaszolt.
     */
    public static boolean ask(String question) {
        for (int i = 0; i < depth; i++)
            System.out.print("   ");
        System.out.print("? " + question + " (I/N): ");
        System.out.flush();
        String answer = scanner.nextLine().trim().toUpperCase();
        return answer.equals("I");
    }

    /**
     * Megjeleníti a tesztelő menüt, beolvassa a választást,
     * majd futtatja a kiválasztott tesztesetet.
     */
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

        boolean exit = false;
        while(!exit){
            System.out.print("\nVálasszon tesztesetet (1-26): ");
            String input = scanner.nextLine().trim();
            int choice;
            try {
                choice = Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("Érvénytelen bemenet.");
                return;
            }
            if(choice == 0){
                exit = true;
                break;
            }

            System.out.println();
            skeletonLogic(choice);
        }
    }

    /**
     * A kiválasztott teszteset logikáját futtatja.
     * @param testCase A teszteset sorszáma.
     */
    public static void skeletonLogic(int testCase) {
        
        switch (testCase) {
            case 1:  testGameInit();           break;
            case 2:  testCarOnClearLane();     break;
            case 3:  testBusArrival();         break;
            case 4:  testCarGetStuck();        break;
            case 5:  testBusRepair();          break;
            case 6:  testBiokerosene();        break;
            case 7:  testAttachemntPurchase();   break;
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
            default: System.out.println("Ismeretlen teszteset: " + testCase); break;
        }
    }

    // =========================================================================
    // Segédmetódus: alap pályainfrastruktúra felépítése
    // =========================================================================

    /**
     * Létrehoz és összeköt egy alap útszakaszt tesztelési célra.
     * <p>Felépítés: n1 → st (Street) → n2, egyetlen {@code l} sávval (ClearState).</p>
     * @return Object[] tömb: [0]=n1 (Node), [1]=n2 (Node), [2]=st (Street), [3]=l (Lane)
     */
    private static Object[] buildBasicRoad() {
        Node n1 = new Node("n1");
        Node n2 = new Node("n2");
        Street st = new Street("st", false);
        Lane l = new Lane(n1, n2, "l");
        st.addLane(l);
        st.addNode(n1);
        st.addNode(n2);
        n1.addRoad(st);
        return new Object[]{n1, n2, st, l};
    }

    /**
     * Létrehoz egy alapértelmezett Cleaner példányt 500 egyenlegű pénztárcával.
     * @return Az elkészített Cleaner objektum.
     */
    private static Cleaner buildCleaner() {
        Wallet w = new Wallet("wallet", 500);
        return new Cleaner("cl", "Kovács János", w);
    }

    /**
     * Létrehoz egy Snowplow-t, amelyet a megadott Cleaner vásárol meg.
     * @param cleaner A hókotrót megvásárló takarító.
     * @return A Cleaner tulajdonában lévő Snowplow objektum.
     */
    private static Snowplow buildSnowplow(Cleaner cleaner) {
        Snowplow sp = new Snowplow(100, "sp");
        sp.boughtByCleaner(cleaner);
        return sp;
    }

    // =========================================================================
    // TC1  Játék inicializálása
    // =========================================================================
    /**
     * Létrehozza a legegyszerűbb pályát: Graph, két Node és egy Street.
     * A Graph tárolja a csomópontokat és az utat, a Street tárolja a csomópontokat.
     */
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
    }

    // =========================================================================
    // TC2  Autó haladása tiszta sávon
    // =========================================================================
    /**
     * Egy autó move() hívásra belép egy tiszta sávra, a sáv engedélyezi a haladást.
     */
    private static void testCarOnClearLane() {
        Object[] road = buildBasicRoad();
        Node n1 = (Node) road[0];

        Car c1 = new Car("c1", n1);
        c1.move();
    }

    // =========================================================================
    // TC3  Busz megérkezik végállomására
    // =========================================================================
    /**
     * A busz move() hatására halad, beérkezik a BusStop végállomásra,
     * befejezi a körét, és új végállomást sorsol.
     */
    private static void testBusArrival() {
        Object[] road = buildBasicRoad();
        Node n1 = (Node) road[0];
        Node n2 = (Node) road[1];

        BusStop bs1 = new BusStop("bs1");
        BusStop bs2 = new BusStop("bs2");
        List<BusStop> allStops = new ArrayList<>();
        allStops.add(bs1);
        allStops.add(bs2);
        bs1.setAllBusStops(allStops);
        bs2.setAllBusStops(allStops);
        n2.addStructure(bs2);

        BusDriver bd = new BusDriver("bd");
        Bus bus = new Bus("bus", bd, n1);
        bus.setCurrentTerminal(bs1);
        bus.setDestinationTerminal(bs2);

        bus.move();
    }

    // =========================================================================
    // TC4  Autó elakad
    // =========================================================================
    /**
     * Az autó move() hatására elindul, de a sáv baleseti állapota megakadályozza.
     * Az autó újratervezi az útvonalát – sikeres esetben beállítja, sikertelenül megáll.
     */
    private static void testCarGetStuck() {
        Object[] road = buildBasicRoad();
        Node n1 = (Node) road[0];
        Node n2 = (Node) road[1];
        Lane l  = (Lane) road[3];

        l.changeState(new AccidentState(l, "accidentState"));

        Car c1 = new Car("c1", n1, n2);
        c1.move();
    }

    // =========================================================================
    // TC5  Busz javítása
    // =========================================================================
    /**
     * Sérült busz nem tud elindulni a move() hatására.
     * A repair() javítja, utána start() révén ismét mozgásra képes.
     */
    private static void testBusRepair() {
        BusDriver bd = new BusDriver("bd");
        Bus bus = new Bus("bus", bd);

        bus.sufferCollision();   // sérült állapotba kerül
        bus.move();              // nem tud elindulni
        bus.repair();            // megjavítják, start() hívódik
    }

    // =========================================================================
    // TC6  Biokerozin vásárlása
    // =========================================================================
    /**
     * Takarító biokerozint vesz. Ha van elég pénze és hely az inventoryban,
     * a vásárlás sikeres és bekerül az inventoryba.
     */
    private static void testBiokerosene() {
        Cleaner cleaner = buildCleaner();
        BioKerosene kerosene = new BioKerosene(10, "kerozin", 50);

        boolean hasFunds = ask("Van-e elég pénze a takarítónak?");
        if (hasFunds) {
            cleaner.purchaseItem(kerosene);
        }
    }

    // =========================================================================
    // TC7  Hókotrófej vásárlása
    // =========================================================================
    /**
     * Takarító hókotrófejet vesz. Ha van elég pénze, sikeres a vásárlás.
     */
    private static void testAttachemntPurchase() {
        Cleaner cleaner = buildCleaner();
        //Snowplow sp = new Snowplow(200, "sp");
        Attachment att = new IceBrakerAttachment("IceBrajkerAttachemnt", 20);

        boolean hasFunds = ask("Van-e elég pénze a takarítónak?");
        if (hasFunds) {
            cleaner.purchaseItem(att);
        }
    }

    // =========================================================================
    // TC8  Busz vásárlása
    // =========================================================================
    /**
     * Buszsofőr buszt vesz. Ha van elég pénze (200 egység), a vásárlás sikeres.
     */
    private static void testBusPurchase() {
        BusDriver bd = new BusDriver("bd");   // 500 egyenleg
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
    /**
     * Várakozó autó újraszámolja útvonalát, majd ráhajt a sávra.
     */
    private static void testCarRestart() {
        Object[] road = buildBasicRoad();
        Node n1 = (Node) road[0];
        Node n2 = (Node) road[1];

        Car c1 = new Car("c1", n1, n2);
        c1.findPath(n1, n2);   // útvonal újraszámolása
        c1.move();             // elindul a friss útvonalon
    }

    // =========================================================================
    // TC10  Havazás az alagútban
    // =========================================================================
    /**
     * Az alagút snowLogic()-ja nem terjeszti tovább a havazást a sávokra.
     */
    private static void testSnowInTunnel() {
        Tunnel tunnel = new Tunnel("tunnel");
        tunnel.snowLogic();
    }

    // =========================================================================
    // TC11  Havazás a tiszta utcán
    // =========================================================================
    /**
     * Havazás hatására a tiszta sáv (ClearState) enyhén havasra (LightSnowyState) vált.
     */
    private static void testSnowOnClearStreet() {
        Node n1 = new Node("n1");
        Node n2 = new Node("n2");
        Street st = new Street("st", false);
        Lane l = new Lane(n1, n2, "l");  // alapból ClearState
        st.addLane(l);

        st.snowLogic();
    }

    // =========================================================================
    // TC12  Havazás az enyhén havas utcán
    // =========================================================================
    /**
     * Havazás hatására az enyhén havas sáv (LightSnowyState) erősen havasra (HeavySnowyState) vált.
     */
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
    /**
     * A busz megérkezik a végállomásra, majd a rendszer új véletlenszerű
     * célmegállót sorsol a többi megálló közül.
     */
    private static void testBusNewDestination() {
        BusStop bs1 = new BusStop("bs1");
        BusStop bs2 = new BusStop("bs2");
        List<BusStop> allStops = new ArrayList<>();
        allStops.add(bs1);
        allStops.add(bs2);
        bs1.setAllBusStops(allStops);
        bs2.setAllBusStops(allStops);

        BusDriver bd = new BusDriver("bd");
        Bus bus = new Bus("bus", bd);
        bus.setCurrentTerminal(bs1);
        bus.setDestinationTerminal(bs2);

        bs2.acceptBus(bus);   // megérkezés → completeRound + drawNewDestination
    }

    // =========================================================================
    // TC14  Enyhén havas út megfagyása
    // =========================================================================
    /**
     * Autók haladnak az enyhén havas sávon; elegendő áthajtás után
     * a sáv jeges (IcyState) állapotba kerül.
     */
    private static void testLightSnowyFreeze() {
        Object[] road = buildBasicRoad();
        Node n1 = (Node) road[0];
        Node n2 = (Node) road[1];
        Lane l  = (Lane) road[3];
        l.changeState(new LightSnowyState(l, "lightSnowyState"));

        Car c1 = new Car("c1", n1, n2);
        l.handleVehicle(c1);   // ? Elég autó hajtott rá → jeges lesz
    }

    // =========================================================================
    // TC15  Só hatásának elmúlása
    // =========================================================================
    /**
     * A sózott sávon (SaltedState) az idő múlásával (snowLogic) lejár a só hatása,
     * és a sáv visszatér tiszta (ClearState) állapotba.
     */
    private static void testSaltDisappear() {
        Node n1 = new Node("n1");
        Node n2 = new Node("n2");
        Street st = new Street("st", false);
        Lane l = new Lane(n1, n2, "l");
        l.changeState(new SaltedState(l, "saltedState"));
        st.addLane(l);

        st.snowLogic();   // ? Lejárt már a só → ClearState
    }

    // =========================================================================
    // TC16  Autó haladása / ütközése jeges úton
    // =========================================================================
    /**
     * Jeges sávon az autó megcsúszik (slip), majd kiértékeli az ütközést.
     * Baleset esetén minden jármű sérül és a sáv AccidentState-be kerül.
     */
    private static void testCarOnIcyLane() {
        Object[] road = buildBasicRoad();
        Node n1 = (Node) road[0];
        Node n2 = (Node) road[1];
        Lane l  = (Lane) road[3];
        l.changeState(new IcyState(l, "icyState"));

        Car c2 = new Car("c2", n1, n2);
        l.enterLane(c2);   // c2 már a sávon van

        Car c1 = new Car("c1", n1, n2);
        c1.move();         // megcsúszik → evaluateCollisions
    }

    // =========================================================================
    // TC17  Söprőfej használata
    // =========================================================================
    /**
     * Hókotró söprőfejjel halad enyhén havas sávon, és megtisztítja azt.
     */
    private static void testSweeperHead() {
        Object[] road = buildBasicRoad();
        Node n1 = (Node) road[0];
        Lane l  = (Lane) road[3];
        l.changeState(new LightSnowyState(l, "lightSnowyState"));

        Cleaner cleaner = buildCleaner();
        Snowplow sp = buildSnowplow(cleaner);
        sp.changeAttachment(new SweeperAttachment("sweeper", 20));
        sp.setCurrentNode(n1);

        sp.move();
    }

    // =========================================================================
    // TC18  Hányófej használata
    // =========================================================================
    /**
     * Hókotró hányófejjel (SnowBladeAttachment) halad enyhén havas sávon,
     * és 2 sávnyi tolással megtisztítja azt.
     */
    private static void testSnowBladeHead() {
        Object[] road = buildBasicRoad();
        Node n1 = (Node) road[0];
        Lane l  = (Lane) road[3];
        l.changeState(new LightSnowyState(l, "lightSnowyState"));

        Cleaner cleaner = buildCleaner();
        Snowplow sp = buildSnowplow(cleaner);
        sp.changeAttachment(new SnowBladeAttachment("hanyofej", 15));
        sp.setCurrentNode(n1);

        sp.move();
    }

    // =========================================================================
    // TC19  Jégtörő használata
    // =========================================================================
    /**
     * Hókotró jégtörő fejjel feltöri a jeget (IcyState → BrokenIceState).
     */
    private static void testIceBreaker() {
        Object[] road = buildBasicRoad();
        Node n1 = (Node) road[0];
        Lane l  = (Lane) road[3];
        l.changeState(new IcyState(l, "icyState"));

        Cleaner cleaner = buildCleaner();
        Snowplow sp = buildSnowplow(cleaner);
        sp.changeAttachment(new IceBrakerAttachment("jegoro", 25));
        sp.setCurrentNode(n1);

        sp.move();
    }

    // =========================================================================
    // TC20  Út sózása
    // =========================================================================
    /**
     * Hókotró sószóró fejjel besózza a sávot (→ SaltedState).
     */
    private static void testGritter() {
        Object[] road = buildBasicRoad();
        Node n1 = (Node) road[0];

        Salt salt = new Salt(10, "so", 5);
        GritterAttachment gritter = new GritterAttachment("soszoro", 30);
        gritter.setSalt(salt);

        Cleaner cleaner = buildCleaner();
        Snowplow sp = buildSnowplow(cleaner);
        sp.changeAttachment(gritter);
        sp.setCurrentNode(n1);

        sp.move();
    }

    // =========================================================================
    // TC21  Sárkányfej használata
    // =========================================================================
    /**
     * Hókotró sárkányfejjel (FlamethrowerAttachment) biokerozin segítségével
     * elolvasztja a jeget vagy havat (→ ClearState).
     */
    private static void testFlamethrower() {
        Object[] road = buildBasicRoad();
        Node n1 = (Node) road[0];
        Lane l  = (Lane) road[3];
        l.changeState(new IcyState(l, "icyState"));

        BioKerosene kerosene = new BioKerosene(10, "kerozin", 50);
        FlamethrowerAttachment flame = new FlamethrowerAttachment("sarkanyFej", 50);
        flame.setBioKerosene(kerosene);

        Cleaner cleaner = buildCleaner();
        Snowplow sp = buildSnowplow(cleaner);
        sp.changeAttachment(flame);
        sp.setCurrentNode(n1);

        sp.move();
    }

    // =========================================================================
    // TC22  Hókotró fejcserélése
    // =========================================================================
    /**
     * A hókotró az állomásra érkezve fejet cserélhet a megvásárolt fejek közül.
     */
    private static void testHeadChange() {
        Node n1 = new Node("n1");
        SnowplowStation station = new SnowplowStation("station", n1);

        Cleaner cleaner = buildCleaner();
        Snowplow sp = buildSnowplow(cleaner);

        station.acceptSnowplow(sp);  // ? Akar fejet cserélni → onStation → purchaseItem
    }

    // =========================================================================
    // TC23  Roncsautók elszállítása
    // =========================================================================
    /**
     * Az AccidentState snowLogic()-a eltávolítja a balesetet szenvedett autókat,
     * és a sáv IcyState-be kerül.
     */
    private static void testWreckRemoval() {
        Node n1 = new Node("n1");
        Node n2 = new Node("n2");
        Street st = new Street("st", false);
        Lane l = new Lane(n1, n2, "l");
        l.changeState(new AccidentState(l, "accidentState"));
        st.addLane(l);

        Car c1 = new Car("c1", n1, n2);
        l.enterLane(c1);
        c1.setCurrentLane(l);   // szükséges az accidentOverAction → removeVehicle() híváshoz

        st.snowLogic();   // ? Vége a balesetnek → autók elszállítva, IcyState
    }

    // =========================================================================
    // TC24  Hókotró elakadása balesetes úton
    // =========================================================================
    /**
     * A hókotró megpróbál ráhajtani egy balesetes sávra, de az AccidentState megtagadja
     * a belépést és a takarítást; a hókotró nem kap juttatást.
     */
    private static void testSnowplowOnAccident() {
        Object[] road = buildBasicRoad();
        Node n1 = (Node) road[0];
        Lane l  = (Lane) road[3];
        l.changeState(new AccidentState(l, "accidentState"));

        Cleaner cleaner = buildCleaner();
        Snowplow sp = buildSnowplow(cleaner);
        sp.changeAttachment(new SweeperAttachment("sweeper", 20));
        sp.setCurrentNode(n1);

        sp.move();   // handleVehicle → AccidentState → false, nincs takarítás, nincs fizetség
    }

    // =========================================================================
    // TC25  Sikertelen takarítás
    // =========================================================================
    /**
     * Hókotró söprőfejjel halad jeges (IcyState) sávon. A fizikai áthaladás
     * lehetséges, de a söprés hatástalan jeges úton – a takarítás sikertelen,
     * a hókotró nem kap fizetséget.
     */
    private static void testFailedCleaning() {
        Object[] road = buildBasicRoad();
        Node n1 = (Node) road[0];
        Lane l  = (Lane) road[3];
        l.changeState(new IcyState(l, "icyState"));

        Cleaner cleaner = buildCleaner();
        Snowplow sp = buildSnowplow(cleaner);
        sp.changeAttachment(new SweeperAttachment("sweeper", 20));
        sp.setCurrentNode(n1);

        sp.move();   // belép a sávra, de IcyState.sweep → false → nincs fizetség
    }

    // =========================================================================
    // TC26  Új kotrófej vásárlása, cserélése és takarítás
    // =========================================================================
    /**
     * Takarító az állomáson hókotrófejet vásárol és felszereli a hókotrójára,
     * majd a hókotró elindul takarítani. Ha a sáv jeges, a söprés sikertelen.
     */
    private static void testAttachmentPurchaseAndChangeAndClean() {
        Object[] road = buildBasicRoad();
        Node n1 = (Node) road[0];
        Lane l  = (Lane) road[3];

        SnowplowStation station = new SnowplowStation("station", n1);
        Cleaner cleaner = buildCleaner();
        Snowplow sp = buildSnowplow(cleaner);

        station.acceptSnowplow(sp);
        
        l.changeState(new IcyState(l, "icyState"));

        //sp.changeAttachment(new IceBrakerAttachment("jegoro", 25));
        sp.setCurrentNode(n1);

        sp.move();
    }
}
