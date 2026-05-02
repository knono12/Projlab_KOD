package testhandler;

import java.io.*;
import java.util.*;

import accessories.attachments.*;
import accessories.fuels.*;
import environment.Graph;
import environment.lane.Lane;
import environment.lane.lanestates.*;
import environment.nodes.Node;
import environment.nodes.structures.*;
import environment.road.*;
import finance.Purchasable;
import players.*;
import vehicles.*;

/**
 * Szöveges parancsokat értelmező és végrehajtó osztály.
 * <p>
 * A parancsértelmező soronként olvassa a bemenetet, és szöveges utasítások alapján
 * építi fel a szimulációs pályát (csomópontok, utak, sávok, épületek, játékosok,
 * járművek), állítja be azok állapotát, és vezérli a szimulációs lépéseket.
 * A {@code save} parancs visszaadja az aktuális állapotot újra betölthető formában,
 * az objektumok létrehozási sorrendjét megőrizve.
 * </p>
 * <p>
 * Támogatott parancscsoportok:
 * <ul>
 *   <li>Pályaépítés: {@code node}, {@code road}, {@code lane}, {@code busstop},
 *       {@code station}, {@code building}, {@code busdriver}, {@code cleaner},
 *       {@code bus}, {@code car}, {@code snowplow}</li>
 *   <li>Állapotbeállítás: {@code set-lane-state}, {@code set-snow},
 *       {@code set-bus-route}, {@code set-attachment}, {@code set-money},
 *       {@code set-damaged}</li>
 *   <li>Szimuláció: {@code step}, {@code snow}, {@code move}</li>
 *   <li>Vezérlés: {@code random}, {@code seed}, {@code save}, {@code load},
 *       {@code status}, {@code exit}</li>
 * </ul>
 * </p>
 */
public class CommandInterpreter {

    private final Graph graph = new Graph();

    // Typed object registries
    private final Map<String, Node>      nodes      = new LinkedHashMap<>();
    private final Map<String, Road>      roads      = new LinkedHashMap<>();
    private final Map<String, Lane>      lanes      = new LinkedHashMap<>();
    private final Map<String, Structure> structures = new LinkedHashMap<>();
    private final Map<String, Player>    players    = new LinkedHashMap<>();
    private final Map<String, Vehicle>   vehicles   = new LinkedHashMap<>();

    // Metadata for save reconstruction
    private final Map<String, String>   roadTypes    = new HashMap<>(); // id → "street"/"tunnel"/"bridge"
    private final Map<String, String[]> roadNodeIds  = new HashMap<>(); // id → [n1id, n2id]
    private final Map<String, String[]> laneInfo     = new HashMap<>(); // id → [roadId, fromId, toId]
    private final Map<String, String[]> structInfo   = new HashMap<>(); // id → [type, nodeId]
    private final Map<String, String>   playerTypes  = new HashMap<>(); // id → "busdriver"/"cleaner"
    private final Map<String, String>   vehicleTypes = new HashMap<>(); // id → "car"/"bus"/"snowplow"
    private final Map<String, String>   vehicleOwner = new HashMap<>(); // vehicleId → playerId

    // Reverse lookups (object → id)
    private final Map<Node, String>      nodeById      = new IdentityHashMap<>();
    private final Map<Structure, String> structureById = new IdentityHashMap<>();

    /**
     * Az összes objektum létrehozási sorrendben, a {@code save} kimenet helyes
     * sorrendjének meghatározásához.
     * Minden bejegyzés {@code [típus, azonosító]} alakú.
     */
    private final List<String[]> objectOrder = new ArrayList<>();

    /**
     * Azok a jármű-azonosítók, amelyekre explicit {@code set-damaged} parancs érkezett,
     * az utoljára beállított értékkel. A {@code save} kimenetbe akkor kerül {@code set-damaged false},
     * ha a jármű nem sérült, de korábban explicit parancs vonatkozott rá.
     */
    private final Map<String, Boolean> explicitDamaged = new HashMap<>();

    /** Ha {@code false}, a {@code save} kimenetben szerepel a {@code random off} sor. */
    private boolean randomMode = true;
    /** A {@code seed} paranccsal beállított véletlenszám-mag, vagy {@code null}, ha nem volt megadva. */
    private Long seed = null;

    private PrintStream out;

    // ----------------------------------------------------------------------------------
    // Entry point
    // ----------------------------------------------------------------------------------

    /**
     * Elindítja a parancsértelmezőt, és soronként dolgozza fel a bemenetet.
     *
     * @param in  a parancsokat tartalmazó bemeneti adatfolyam
     * @param out a kimenet, amelyre a {@code save} és {@code status} parancsok írnak
     */
    public void run(InputStream in, PrintStream out) {
        this.out = out;
        try (Scanner sc = new Scanner(in)) {
            while (sc.hasNextLine()) {
                String line = sc.nextLine().trim();
                if (processLine(line)) break;
            }
        }
    }

    /**
     * Feldolgoz egy szöveges parancsot.
     * Üres sorokat és {@code #}-tel kezdődő kommenteket figyelmen kívül hagy.
     * Ismeretlen parancsokra {@code ERROR: ismeretlen parancs: <cmd>} kerül a kimenetre.
     *
     * @param line a feldolgozandó sor
     * @return {@code true}, ha az értelmezőnek le kell állnia ({@code exit} parancs)
     */
    public boolean processLine(String line) {
        if (line.isEmpty() || line.startsWith("#")) return false;
        String[] p = line.split("\\s+");
        String cmd = p[0].toLowerCase();
        try {
            switch (cmd) {
                case "node":           doNode(p);          break;
                case "road":           doRoad(p);          break;
                case "lane":           doLane(p);          break;
                case "busstop":        doBusstop(p);       break;
                case "station":        doStation(p);       break;
                case "building":       doBuilding(p);      break;
                case "busdriver":      doBusdriver(p);     break;
                case "cleaner":        doCleaner(p);       break;
                case "bus":            doBus(p);           break;
                case "car":            doCar(p);           break;
                case "snowplow":       doSnowplow(p);      break;
                case "set-lane-state": doSetLaneState(p);  break;
                case "set-snow":       doSetSnow(p);       break;
                case "set-bus-route":  doSetBusRoute(p);   break;
                case "set-attachment": doSetAttachment(p); break;
                case "set-money":      doSetMoney(p);      break;
                case "set-damaged":    doSetDamaged(p);    break;
                case "move":           doMove(p);          break;
                case "snow":           doSnow(p);          break;
                case "step":           graph.step();       break;
                case "random":         doRandom(p);        break;
                case "seed":           doSeed(p);          break;
                case "status":         doStatus(p);        break;
                case "save":           doSave();           break;
                case "load":           doLoad(p);          break;
                case "exit":           return true;
                default:
                    out.println("ERROR: ismeretlen parancs: " + cmd);
            }
        } catch (Exception e) {
            String msg = e.getMessage();
            out.println("ERROR: " + (msg != null ? msg : e.getClass().getSimpleName()));
        }
        return false;
    }

    // ----------------------------------------------------------------------------------
    // Pályaépítő parancsok
    // ----------------------------------------------------------------------------------

    /**
     * Létrehoz egy új csomópontot és hozzáadja a gráfhoz.
     * <p>Szintaxis: {@code node <id>}</p>
     *
     * @param p parancsszavak tömbje
     */
    private void doNode(String[] p) {
        String id = p[1];
        Node n = new Node(id);
        nodes.put(id, n);
        nodeById.put(n, id);
        graph.addNode(n);
        objectOrder.add(new String[]{"node", id});
    }

    /**
     * Létrehoz egy utat, és összeköti a két megadott csomópontot.
     * <p>Szintaxis: {@code road <id> street|tunnel|bridge <node1-id> <node2-id>}</p>
     *
     * @param p parancsszavak tömbje
     */
    private void doRoad(String[] p) {
        String id    = p[1];
        String type  = p[2].toLowerCase();
        String n1id  = p[3];
        String n2id  = p[4];
        Node n1 = req(nodes, n1id, "ismeretlen azonosito: " + n1id);
        Node n2 = req(nodes, n2id, "ismeretlen azonosito: " + n2id);

        Road road;
        switch (type) {
            case "bridge":  road = new Street(id, true);  break;
            case "tunnel":  road = new Tunnel(id);        break;
            default:        road = new Street(id, false); type = "street"; break;
        }
        road.addNode(n1);
        road.addNode(n2);
        n1.addRoad(road);
        n2.addRoad(road);
        roads.put(id, road);
        roadTypes.put(id, type);
        roadNodeIds.put(id, new String[]{n1id, n2id});
        graph.addRoad(road);
        objectOrder.add(new String[]{"road", id});
    }

    /**
     * Létrehoz egy sávot a megadott úton a megadott irányban.
     * <p>Szintaxis: {@code lane <id> <road-id> <from-node-id> <to-node-id>}</p>
     *
     * @param p parancsszavak tömbje
     */
    private void doLane(String[] p) {
        String id     = p[1];
        String roadId = p[2];
        String fromId = p[3];
        String toId   = p[4];
        Road road = req(roads, roadId, "ismeretlen azonosito: " + roadId);
        Node from = req(nodes, fromId, "ismeretlen azonosito: " + fromId);
        Node to   = req(nodes, toId,   "ismeretlen azonosito: " + toId);

        Lane lane = new Lane(from, to, id);
        road.addLane(lane);
        lanes.put(id, lane);
        laneInfo.put(id, new String[]{roadId, fromId, toId});
        objectOrder.add(new String[]{"lane", id});
    }

    /**
     * Létrehoz egy buszmegállót és hozzárendeli a megadott csomóponthoz.
     * <p>Szintaxis: {@code busstop <id> <node-id>}</p>
     *
     * @param p parancsszavak tömbje
     */
    private void doBusstop(String[] p) {
        String id     = p[1];
        String nodeId = p[2];
        Node node = req(nodes, nodeId, "ismeretlen azonosito: " + nodeId);
        BusStop bs = new BusStop(id);
        node.addStructure(bs);
        structures.put(id, bs);
        structureById.put(bs, id);
        structInfo.put(id, new String[]{"busstop", nodeId});
        graph.addBusStop(bs);
        objectOrder.add(new String[]{"busstop", id});
    }

    /**
     * Létrehoz egy hóekéállomást és hozzárendeli a megadott csomóponthoz.
     * <p>Szintaxis: {@code station <id> <node-id>}</p>
     *
     * @param p parancsszavak tömbje
     */
    private void doStation(String[] p) {
        String id     = p[1];
        String nodeId = p[2];
        Node node = req(nodes, nodeId, "ismeretlen azonosito: " + nodeId);
        SnowplowStation st = new SnowplowStation(id, node);
        node.addStructure(st);
        structures.put(id, st);
        structureById.put(st, id);
        structInfo.put(id, new String[]{"station", nodeId});
        objectOrder.add(new String[]{"station", id});
    }

    /**
     * Létrehoz egy épületet és hozzárendeli a megadott csomóponthoz.
     * <p>Szintaxis: {@code building <id> <node-id>}</p>
     *
     * @param p parancsszavak tömbje
     */
    private void doBuilding(String[] p) {
        String id     = p[1];
        String nodeId = p[2];
        Node node = req(nodes, nodeId, "ismeretlen azonosito: " + nodeId);
        Building b = new Building(id, false, node);
        node.addStructure(b);
        structures.put(id, b);
        structureById.put(b, id);
        structInfo.put(id, new String[]{"building", nodeId});
        objectOrder.add(new String[]{"building", id});
    }

    /**
     * Létrehoz egy buszvezetőt a megadott kezdeti egyenleggel.
     * <p>Szintaxis: {@code busdriver <id> <money>}</p>
     * <p>A {@code BusDriver.ownedBuses} mező reflectionnel kerül inicializálásra.</p>
     *
     * @param p parancsszavak tömbje
     */
    private void doBusdriver(String[] p) {
        String id    = p[1];
        int money    = Integer.parseInt(p[2]);
        BusDriver bd = new BusDriver(id, id);
        ReflectionHelper.initBusDriverOwnedBuses(bd);
        ReflectionHelper.setBusDriverMoney(bd, money);
        players.put(id, bd);
        playerTypes.put(id, "busdriver");
        objectOrder.add(new String[]{"busdriver", id});
    }

    /**
     * Létrehoz egy takarítót a megadott kezdeti egyenleggel.
     * <p>Szintaxis: {@code cleaner <id> <money>}</p>
     * <p>A konstruktor által automatikusan létrehozott hóeke reflectionnel törlésre kerül.</p>
     *
     * @param p parancsszavak tömbje
     */
    private void doCleaner(String[] p) {
        String id  = p[1];
        int money  = Integer.parseInt(p[2]);
        Cleaner cl = new Cleaner(id, id, new ArrayList<Purchasable>());
        ReflectionHelper.clearCleanerSnowplows(cl);
        cl.receiveMoney(money);
        players.put(id, cl);
        playerTypes.put(id, "cleaner");
        objectOrder.add(new String[]{"cleaner", id});
    }

    /**
     * Létrehoz egy buszt, hozzárendeli a sofőrhöz, és elhelyezi a megadott csomópontban.
     * <p>Szintaxis: {@code bus <id> <driver-id> <node-id>}</p>
     * <p>Ha a sofőrnek nincs elég pénze (kevesebb mint 200), a parancs hibával tér vissza.</p>
     *
     * @param p parancsszavak tömbje
     */
    private void doBus(String[] p) {
        String id       = p[1];
        String driverId = p[2];
        String nodeId   = p[3];
        BusDriver driver = (BusDriver) req(players, driverId, "ismeretlen azonosito: " + driverId);
        Node node = req(nodes, nodeId, "ismeretlen azonosito: " + nodeId);

        Bus bus = new Bus(id, driver, node);
        ReflectionHelper.setBusGraph(bus, graph);
        ReflectionHelper.initBusDriverOwnedBuses(driver);
        driver.addBus(bus);
        vehicles.put(id, bus);
        vehicleTypes.put(id, "bus");
        vehicleOwner.put(id, driverId);
        graph.addVehicle(bus);
        objectOrder.add(new String[]{"bus", id});
    }

    /**
     * Létrehoz egy autót, opcionálisan célcsomóponttal, és elhelyezi a megadott csomópontban.
     * <p>Szintaxis: {@code car <id> <node-id> [dest-node-id]}</p>
     *
     * @param p parancsszavak tömbje
     */
    private void doCar(String[] p) {
        String id     = p[1];
        String nodeId = p[2];
        Node node = req(nodes, nodeId, "ismeretlen azonosito: " + nodeId);

        Car car;
        if (p.length >= 4) {
            String destId = p[3];
            Node dest = req(nodes, destId, "ismeretlen azonosito: " + destId);
            car = new Car(id, node, dest);
            car.findPath(node, dest);
        } else {
            car = new Car(id, node);
        }
        vehicles.put(id, car);
        vehicleTypes.put(id, "car");
        graph.addVehicle(car);
        objectOrder.add(new String[]{"car", id});
    }

    /**
     * Létrehoz egy hóekét, hozzárendeli a takarítóhoz, és elhelyezi a megadott csomópontban.
     * <p>Szintaxis: {@code snowplow <id> <cleaner-id> <node-id>}</p>
     * <p>A {@code Snowplow.cleaner} mező reflectionnel kerül beállításra.</p>
     *
     * @param p parancsszavak tömbje
     */
    private void doSnowplow(String[] p) {
        String id        = p[1];
        String cleanerId = p[2];
        String nodeId    = p[3];
        Cleaner cleaner = (Cleaner) req(players, cleanerId, "ismeretlen azonosito: " + cleanerId);
        Node node = req(nodes, nodeId, "ismeretlen azonosito: " + nodeId);

        Snowplow sp = new Snowplow(0, id);
        sp.setCurrentNode(node);
        ReflectionHelper.setSnowplowCleaner(sp, cleaner);
        cleaner.addSnowplow(sp);
        vehicles.put(id, sp);
        vehicleTypes.put(id, "snowplow");
        vehicleOwner.put(id, cleanerId);
        graph.addVehicle(sp);
        objectOrder.add(new String[]{"snowplow", id});
    }

    // ----------------------------------------------------------------------------------
    // Állapotbeállító parancsok
    // ----------------------------------------------------------------------------------

    /**
     * Beállítja a megadott sáv állapotát.
     * <p>Szintaxis: {@code set-lane-state <lane-id> clear|light-snowy|heavy-snowy|icy|salted|broken-ice|accident|gravel}</p>
     *
     * @param p parancsszavak tömbje
     */
    private void doSetLaneState(String[] p) {
        String laneId = p[1];
        String state  = p[2].toLowerCase();
        Lane lane = req(lanes, laneId, "ismeretlen azonosito: " + laneId);
        lane.changeState(buildLaneState(lane, state));
    }

    /**
     * Beállítja a sáv hóvastagságát.
     * <p>Szintaxis: {@code set-snow <lane-id> <thickness>}</p>
     *
     * @param p parancsszavak tömbje
     */
    private void doSetSnow(String[] p) {
        String laneId = p[1];
        int thickness = Integer.parseInt(p[2]);
        Lane lane = req(lanes, laneId, "ismeretlen azonosito: " + laneId);
        lane.setSnowThickness(thickness);
    }

    /**
     * Beállítja a busz aktuális és célmegállóját.
     * <p>Szintaxis: {@code set-bus-route <bus-id> <current-stop-id> <dest-stop-id>}</p>
     *
     * @param p parancsszavak tömbje
     */
    private void doSetBusRoute(String[] p) {
        String busId      = p[1];
        String curStopId  = p[2];
        String destStopId = p[3];
        Bus bus = (Bus) req(vehicles, busId, "ismeretlen azonosito: " + busId);
        BusStop cur  = (BusStop) req(structures, curStopId,  "ismeretlen azonosito: " + curStopId);
        BusStop dest = (BusStop) req(structures, destStopId, "ismeretlen azonosito: " + destStopId);
        bus.setCurrentTerminal(cur);
        bus.setDestinationTerminal(dest);
    }

    /**
     * Beállítja a hóeke szerszámát (felszerelését).
     * <p>Szintaxis: {@code set-attachment <snowplow-id> sweeper|snowblade|gritter|icebreaker|flamethrower|gravel}</p>
     *
     * @param p parancsszavak tömbje
     */
    private void doSetAttachment(String[] p) {
        String spId = p[1];
        String type = p[2].toLowerCase();
        Snowplow sp = (Snowplow) req(vehicles, spId, "ismeretlen azonosito: " + spId);
        sp.changeAttachment(buildAttachment(type));
    }

    /**
     * Beállítja a játékos pénzegyenlegét.
     * <p>Szintaxis: {@code set-money <player-id> <amount>}</p>
     *
     * @param p parancsszavak tömbje
     */
    private void doSetMoney(String[] p) {
        String playerId = p[1];
        int amount      = Integer.parseInt(p[2]);
        Player player = req(players, playerId, "ismeretlen azonosito: " + playerId);
        setMoney(player, amount);
    }

    /**
     * Beállítja a jármű sérült állapotát és nyilvántartja az explicit beállítást.
     * <p>Szintaxis: {@code set-damaged <vehicle-id> true|false}</p>
     *
     * @param p parancsszavak tömbje
     */
    private void doSetDamaged(String[] p) {
        String vehicleId = p[1];
        boolean damaged  = Boolean.parseBoolean(p[2]);
        Vehicle v = req(vehicles, vehicleId, "ismeretlen azonosito: " + vehicleId);
        ReflectionHelper.setVehicleDamaged(v, damaged);
        explicitDamaged.put(vehicleId, damaged);
    }

    // ----------------------------------------------------------------------------------
    // Szimulációs parancsok
    // ----------------------------------------------------------------------------------

    /**
     * Mozgat egy járművet egy sávra, majd a következő csomópontba.
     * <p>Szintaxis:</p>
     * <ul>
     *   <li>{@code move <vehicle-id>} – automatikus sávválasztás</li>
     *   <li>{@code move <vehicle-id> <lane-id>} – meghatározott sáv, út a sávból következik</li>
     *   <li>{@code move <vehicle-id> <road-id> <lane-id>} – explicit út és sáv</li>
     * </ul>
     *
     * @param p parancsszavak tömbje
     */
    private void doMove(String[] p) {
        String vehicleId = p[1];
        Vehicle v = req(vehicles, vehicleId, "ismeretlen azonosito: " + vehicleId);
        if (p.length == 3) {
            // move <vehicle> <lane-id>  — road derived from laneInfo
            String laneId = p[2];
            Lane lane = req(lanes, laneId, "ismeretlen azonosito: " + laneId);
            String roadId = laneInfo.get(laneId)[0];
            Road road = req(roads, roadId, "ismeretlen azonosito: " + roadId);
            v.setNextRoad(road);
            v.setNextLane(lane);
        } else if (p.length >= 4) {
            // move <vehicle> <road-id> <lane-id>
            Road road = req(roads, p[2], "ismeretlen azonosito: " + p[2]);
            Lane lane = req(lanes, p[3], "ismeretlen azonosito: " + p[3]);
            v.setNextRoad(road);
            v.setNextLane(lane);
        }
        v.moveOntoLane();
        v.moveOntoNode();
    }

    /**
     * Lefuttatja a megadott út hólogikáját.
     * <p>Szintaxis: {@code snow <road-id>}</p>
     *
     * @param p parancsszavak tömbje
     */
    private void doSnow(String[] p) {
        String roadId = p[1];
        Road road = req(roads, roadId, "ismeretlen azonosito: " + roadId);
        road.snowLogic();
    }

    // ----------------------------------------------------------------------------------
    // Véletlenszerűség-vezérlő parancsok
    // ----------------------------------------------------------------------------------

    /**
     * Beállítja, hogy a szimuláció véletlenszerű-e.
     * <p>Szintaxis: {@code random on|off}</p>
     *
     * @param p parancsszavak tömbje
     */
    private void doRandom(String[] p) {
        randomMode = p[1].equalsIgnoreCase("on");
    }

    /**
     * Beállítja a véletlenszám-generátor magját ({@code save} kimenetbe kerül).
     * <p>Szintaxis: {@code seed <n>}</p>
     *
     * @param p parancsszavak tömbje
     */
    private void doSeed(String[] p) {
        seed = Long.parseLong(p[1]);
    }

    // ----------------------------------------------------------------------------------
    // Lekérdező parancsok
    // ----------------------------------------------------------------------------------

    /**
     * Kiírja egy vagy összes elem aktuális állapotát.
     * <p>Szintaxis: {@code status [id]}</p>
     *
     * @param p parancsszavak tömbje
     */
    private void doStatus(String[] p) {
        if (p.length >= 2) {
            printStatus(p[1]);
        } else {
            nodes.keySet().forEach(this::printStatus);
            roads.keySet().forEach(this::printStatus);
            lanes.keySet().forEach(this::printStatus);
            structures.keySet().forEach(this::printStatus);
            players.keySet().forEach(this::printStatus);
            vehicles.keySet().forEach(this::printStatus);
        }
    }

    /**
     * Kiírja egyetlen elem állapotát azonosítója alapján.
     *
     * @param id a lekérdezendő elem azonosítója
     * @throws IllegalArgumentException ha az azonosító nem található
     */
    private void printStatus(String id) {
        if (nodes.containsKey(id)) {
            Node n = nodes.get(id);
            out.println("node " + id + ": vehicles=" + nodeVehicleIds(n) + " structure=" + structureAt(n));
        } else if (roads.containsKey(id)) {
            String[] ns = roadNodeIds.get(id);
            out.println("road " + id + " (" + roadTypes.get(id) + "): " + ns[0] + "-" + ns[1]);
        } else if (lanes.containsKey(id)) {
            Lane l = lanes.get(id);
            String[] li = laneInfo.get(id);
            out.println("lane " + id + ": " + li[0] + " " + li[1] + "->" + li[2]
                    + " state=" + laneStateCmd(l) + " snow=" + l.getSnowThickness());
        } else if (structures.containsKey(id)) {
            String[] si = structInfo.get(id);
            out.println(si[0] + " " + id + " node=" + si[1]);
        } else if (players.containsKey(id)) {
            Player pl = players.get(id);
            out.println(playerTypes.get(id) + " " + id + " money=" + getMoney(pl));
        } else if (vehicles.containsKey(id)) {
            Vehicle v = vehicles.get(id);
            Node cur = ReflectionHelper.getVehicleCurrentNode(v);
            String curId = cur != null ? nodeById.get(cur) : "?";
            String extra = "";
            if (v instanceof Car) {
                Node dest = ReflectionHelper.getCarDestination((Car) v);
                extra = " dest=" + (dest != null ? nodeById.get(dest) : "none");
            }
            out.println(vehicleTypes.get(id) + " " + id + " node=" + curId
                    + " damaged=" + isDamaged(v) + extra);
        } else {
            throw new IllegalArgumentException("ismeretlen azonosito: " + id);
        }
    }

    // ----------------------------------------------------------------------------------
    // Save
    // ----------------------------------------------------------------------------------

    /**
     * Kiírja az aktuális szimulációs állapotot újra betölthető parancsformátumban,
     * az objektumok létrehozási sorrendjét megőrizve.
     * <p>A kimenet sorrendje: {@code random off} (ha kell), {@code seed},
     * objektumok létrehozási sorrendben, {@code set-bus-route}, {@code set-attachment},
     * {@code set-lane-state} (nem-clear), {@code set-damaged}, {@code set-snow} (nem-nulla).</p>
     */
    private void doSave() {
        if (!randomMode) out.println("random off");
        if (seed != null) out.println("seed " + seed);

        // 1. Objektumok létrehozási sorrendben
        for (String[] entry : objectOrder) {
            String type = entry[0];
            String id   = entry[1];
            switch (type) {
                case "node":
                    out.println("node " + id);
                    break;
                case "road": {
                    String[] ns = roadNodeIds.get(id);
                    out.println("road " + id + " " + roadTypes.get(id) + " " + ns[0] + " " + ns[1]);
                    break;
                }
                case "lane": {
                    String[] li = laneInfo.get(id);
                    out.println("lane " + id + " " + li[0] + " " + li[1] + " " + li[2]);
                    break;
                }
                case "building":
                case "busstop":
                case "station": {
                    String[] si = structInfo.get(id);
                    out.println(type + " " + id + " " + si[1]);
                    break;
                }
                case "busdriver":
                case "cleaner": {
                    Player pl = players.get(id);
                    // Skip if player was removed due to accident or similar
                    if (pl == null) break;
                    out.println(type + " " + id + " " + getMoney(pl));
                    break;
                }
                case "car": {
                    Vehicle v = vehicles.get(id);
                    if (v == null) break; // removed vehicle (accident)
                    Node cur = ReflectionHelper.getVehicleCurrentNode(v);
                    String curId = cur != null ? nodeById.get(cur) : "null";
                    Node dest = ReflectionHelper.getCarDestination((Car) v);
                    if (dest != null) {
                        out.println("car " + id + " " + curId + " " + nodeById.get(dest));
                    } else {
                        out.println("car " + id + " " + curId);
                    }
                    break;
                }
                case "bus": {
                    Vehicle v = vehicles.get(id);
                    if (v == null) break;
                    Node cur = ReflectionHelper.getVehicleCurrentNode(v);
                    String curId = cur != null ? nodeById.get(cur) : "null";
                    String ownerId = vehicleOwner.get(id);
                    out.println("bus " + id + " " + ownerId + " " + curId);
                    break;
                }
                case "snowplow": {
                    Vehicle v = vehicles.get(id);
                    if (v == null) break;
                    Node cur = ReflectionHelper.getVehicleCurrentNode(v);
                    String curId = cur != null ? nodeById.get(cur) : "null";
                    String ownerId = vehicleOwner.get(id);
                    out.println("snowplow " + id + " " + ownerId + " " + curId);
                    break;
                }
            }
        }

        // 2. set-bus-route – buszok aktuális útvonala
        for (String[] entry : objectOrder) {
            if (!"bus".equals(entry[0])) continue;
            String id = entry[1];
            Vehicle v = vehicles.get(id);
            if (v == null) continue;
            Bus bus = (Bus) v;
            BusStop cur  = ReflectionHelper.getBusCurrentTerminal(bus);
            BusStop dest = ReflectionHelper.getBusDestinationTerminal(bus);
            if (cur != null && dest != null) {
                String curId  = structureById.get(cur);
                String destId = structureById.get(dest);
                if (curId != null && destId != null) {
                    out.println("set-bus-route " + id + " " + curId + " " + destId);
                }
            }
        }

        // 3. set-attachment – nem-sweeper hóekefejek
        for (String[] entry : objectOrder) {
            if (!"snowplow".equals(entry[0])) continue;
            String id = entry[1];
            Vehicle v = vehicles.get(id);
            if (v == null) continue;
            String attCmd = attachmentCmd((Snowplow) v);
            if (attCmd != null) {
                out.println("set-attachment " + id + " " + attCmd);
            }
        }

        // 4. set-lane-state – nem-clear sávállapotok
        for (String[] entry : objectOrder) {
            if (!"lane".equals(entry[0])) continue;
            String id = entry[1];
            Lane lane = lanes.get(id);
            if (lane == null) continue;
            String stateCmd = laneStateCmd(lane);
            if (stateCmd != null) {
                out.println("set-lane-state " + id + " " + stateCmd);
            }
        }

        // 5. set-damaged – sérült vagy explicit beállított járművek
        for (String[] entry : objectOrder) {
            String type = entry[0];
            if (!type.equals("car") && !type.equals("bus") && !type.equals("snowplow")) continue;
            String id = entry[1];
            Vehicle v = vehicles.get(id);
            if (v == null) continue;
            if (isDamaged(v)) {
                out.println("set-damaged " + id + " true");
            } else if (explicitDamaged.containsKey(id) && !explicitDamaged.get(id)) {
                out.println("set-damaged " + id + " false");
            }
        }

        // 6. set-snow – nem-nulla hóvastagság
        for (String[] entry : objectOrder) {
            if (!"lane".equals(entry[0])) continue;
            String id = entry[1];
            Lane lane = lanes.get(id);
            if (lane != null && lane.getSnowThickness() > 0) {
                out.println("set-snow " + id + " " + lane.getSnowThickness());
            }
        }
    }

    // ----------------------------------------------------------------------------------
    // Load
    // ----------------------------------------------------------------------------------

    /**
     * Betölt és végrehajt egy parancsfájlt soronként.
     * <p>Szintaxis: {@code load <filename>}</p>
     *
     * @param p parancsszavak tömbje
     */
    private void doLoad(String[] p) {
        String filename = p[1];
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (processLine(line.trim())) break;
            }
        } catch (IOException e) {
            out.println("ERROR: load hiba: " + e.getMessage());
        }
    }

    // ----------------------------------------------------------------------------------
    // Helpers
    // ----------------------------------------------------------------------------------

    /**
     * Megkeres egy elemet a megadott térképben; ha nem találja, kivételt dob.
     *
     * @param <V>     az elem típusa
     * @param map     a keresendő térkép
     * @param id      a keresett azonosító
     * @param errMsg  a kivétel üzenete, ha nem található
     * @return a megtalált elem
     * @throws IllegalArgumentException ha az azonosító nem szerepel a térképben
     */
    private <V> V req(Map<String, V> map, String id, String errMsg) {
        V v = map.get(id);
        if (v == null) throw new IllegalArgumentException(errMsg);
        return v;
    }

    /**
     * Létrehozza a megfelelő {@link LaneState} példányt a szöveges állapotnév alapján.
     *
     * @param lane  a sáv, amelyhez az állapot tartozik
     * @param state az állapot neve (pl. {@code "accident"}, {@code "icy"})
     * @return az új állapotobjektum
     * @throws IllegalArgumentException ha az állapotnév ismeretlen
     */
    private LaneState buildLaneState(Lane lane, String state) {
        switch (state) {
            case "clear":       return new ClearState(lane,       "clearState");
            case "light-snowy": return new LightSnowyState(lane,  "lightSnowyState");
            case "heavy-snowy": return new HeavySnowyState(lane,  "heavySnowyState");
            case "icy":         return new IcyState(lane,         "icyState");
            case "salted":      return new SaltedState(lane,      "saltedState");
            case "broken-ice":  return new BrokenIceState(lane,   "brokenIceState");
            case "accident":    return new AccidentState(lane,     "accidentState");
            case "gravel":      return new GravelState(lane,      "gravelState");
            default: throw new IllegalArgumentException("ervenytelen allapot: " + state);
        }
    }

    /**
     * Létrehozza a megfelelő {@link Attachment} példányt a típusnév alapján.
     *
     * @param type a felszerelés neve (pl. {@code "sweeper"}, {@code "snowblade"})
     * @return az új felszerelésobjektum
     * @throws IllegalArgumentException ha a típusnév ismeretlen
     */
    private Attachment buildAttachment(String type) {
        switch (type) {
            case "sweeper":      return new SweeperAttachment("sweeper", 0);
            case "snowblade":    return new SnowBladeAttachment("snowblade", 0);
            case "gritter": {
                GritterAttachment g = new GritterAttachment("gritter", 0);
                g.setSalt(new Salt("salt", 1000000, 0));
                return g;
            }
            case "icebreaker":   return new IceBrakerAttachment("icebreaker", 0);
            case "flamethrower": {
                FlamethrowerAttachment f = new FlamethrowerAttachment("flamethrower", 0);
                f.setBioKerosene(new BioKerosene(1000000, "bioKerosene", 0));
                return f;
            }
            case "gravel": {
                GravelAttachment ga = new GravelAttachment("gravel", 0);
                ga.setSalt(new Gravel("gravel", 1000000, 0));
                return ga;
            }
            default: throw new IllegalArgumentException("ismeretlen feltarlas tipus: " + type);
        }
    }

    /**
     * Visszaadja a sáv aktuális állapotának {@code save}-kompatibilis parancsnevét.
     *
     * @param lane a lekérdezendő sáv
     * @return az állapot parancsneve, vagy {@code null}, ha az állapot clear
     */
    private String laneStateCmd(Lane lane) {
        LaneState ls = laneStateOf(lane);
        return getLaneStateName(ls);
    }

    /**
     * Visszaadja a {@link LaneState} példány {@code save}-kompatibilis parancsnevét.
     *
     * @param ls az állapotobjektum
     * @return az állapot parancsneve, vagy {@code null}, ha az állapot {@code ClearState} vagy null
     */
    private String getLaneStateName(LaneState ls) {
        if (ls == null) return null;
        String cn = ls.getClass().getSimpleName();
        switch (cn) {
            case "ClearState":       return null;
            case "LightSnowyState":  return "light-snowy";
            case "HeavySnowyState":  return "heavy-snowy";
            case "IcyState":         return "icy";
            case "SaltedState":      return "salted";
            case "BrokenIceState":   return "broken-ice";
            case "AccidentState":    return "accident";
            case "GravelState":      return "gravel";
            default:                 return cn.toLowerCase();
        }
    }

    /**
     * Visszaadja a hóeke aktuális felszerelésének {@code save}-kompatibilis nevét.
     * A sweeper az alapértelmezett fej, és nem kerül a kimenetbe.
     *
     * @param sp a lekérdezendő hóeke
     * @return a felszerelés parancsneve, vagy {@code null}, ha sweeper (alapértelmezett)
     */
    private String attachmentCmd(Snowplow sp) {
        Attachment a = sp.getCurrentAttachment();
        if (a == null) return null;
        String cn = a.getClass().getSimpleName();
        switch (cn) {
            case "SweeperAttachment":      return null;
            case "SnowBladeAttachment":    return "snowblade";
            case "GritterAttachment":      return "gritter";
            case "IceBrakerAttachment":    return "icebreaker";
            case "FlamethrowerAttachment": return "flamethrower";
            case "GravelAttachment":       return "gravel";
            default:                       return cn.toLowerCase();
        }
    }

    /**
     * Visszaadja a játékos aktuális pénzegyenlegét.
     * {@link BusDriver} saját {@code money} mezőt használ; többi játékos a {@code wallet}-et.
     *
     * @param p a lekérdezendő játékos
     * @return a játékos pénzegyenlege
     */
    private int getMoney(Player p) {
        if (p instanceof BusDriver) {
            return ReflectionHelper.getBusDriverMoney((BusDriver) p);
        }
        return ReflectionHelper.getPlayerWalletBalance(p);
    }

    /**
     * Beállítja a játékos pénzegyenlegét.
     *
     * @param p      a módosítandó játékos
     * @param amount az új egyenleg
     */
    private void setMoney(Player p, int amount) {
        if (p instanceof BusDriver) {
            ReflectionHelper.setBusDriverMoney((BusDriver) p, amount);
        } else {
            ReflectionHelper.setPlayerWalletBalance(p, amount);
        }
    }

    /**
     * Visszaadja, hogy a jármű sérült-e (reflection segítségével).
     *
     * @param v a lekérdezendő jármű
     * @return {@code true}, ha a jármű sérült
     */
    private boolean isDamaged(Vehicle v) {
        try {
            java.lang.reflect.Field f = Vehicle.class.getDeclaredField("damaged");
            f.setAccessible(true);
            return (boolean) f.get(v);
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Visszaadja a csomóponton lévő járművek azonosítóit szögletes zárójelben.
     *
     * @param n a lekérdezendő csomópont
     * @return pl. {@code "[c1,bus2]"}, vagy {@code "[]"}, ha üres
     */
    private String nodeVehicleIds(Node n) {
        StringBuilder sb = new StringBuilder("[");
        for (Vehicle v : n.getVehicles()) {
            sb.append(v.getSName()).append(",");
        }
        if (sb.length() > 1) sb.setLength(sb.length() - 1);
        sb.append("]");
        return sb.toString();
    }

    /**
     * Visszaadja a csomóponton lévő épület/megálló azonosítóját.
     *
     * @param n a lekérdezendő csomópont
     * @return az épület azonosítója, {@code "none"} ha nincs, {@code "?"} ha ismeretlen
     */
    private String structureAt(Node n) {
        Structure s = n.getStructure();
        if (s == null) return "none";
        for (Map.Entry<String, Structure> e : structures.entrySet()) {
            if (e.getValue() == s) return e.getKey();
        }
        return "?";
    }

    /**
     * Visszaadja a sáv aktuális {@link LaneState} példányát reflection segítségével.
     * <p>A {@code Lane.laneState} mező csomag-láthatóságú, nincs publikus getter.</p>
     *
     * @param lane a lekérdezendő sáv
     * @return az aktuális állapotobjektum, vagy {@code null}, ha nem érhető el
     */
    private LaneState laneStateOf(Lane lane) {
        try {
            java.lang.reflect.Field f = Lane.class.getDeclaredField("laneState");
            f.setAccessible(true);
            return (LaneState) f.get(lane);
        } catch (Exception e) {
            return null;
        }
    }
}
