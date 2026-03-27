package vehicles;

import java.util.List;

import accessories.attachments.Attachment;
import accessories.attachments.GritterAttachment;
import accessories.attachments.SnowBladeAttachment;
import environment.lane.Lane;
import environment.nodes.structures.Structure;
import environment.road.Road;
import finance.Purchasable;
import players.BusDriver;
import players.Cleaner;
import skeleton.SkeletonManager;

/**
 * A hókotrót reprezentáló osztály, amely a {@link Vehicle} ősosztályból
 * származik.
 * A hókotró feladata a város útjainak (sávjainak) tisztítása. Működése a
 * rászerelt fej ({@link Attachment}) típusától és a sáv állapotától függ.
 * A hókotró jelenléte egy sávon megakadályozza a balesetek kialakulását.
 */
public class Snowplow extends Vehicle implements Purchasable {

    protected int price;
    protected Attachment currentAttachment;
    protected Cleaner claner;

    /**
     * A Snowplow osztály konstruktora.
     * 
     * @param price A hókotró ára.
     * @param sName A hókotró azonosító neve a szkeletonhoz.
     */
    public Snowplow(int price, String sName) {
        super(sName);
        this.price = price;
    }

    @Override
    public int getPrice() {
        return price;
    }

    @Override
    public void setPrice(int price) {
        this.price = price;
    }

    @Override
    public String getSName() {
        return super.getSName();
    }

    @Override
    public String setSName(String sName) {
        return super.setSName(sName);
    }

    /**
     * Lekérdezi, hogy a hókotró tud-e ütközni/balesetet szenvedni.
     * A szabályok szerint a hókotró jelenléte megakadályozza a balesetet, ezért
     * mindig hamis.
     * 
     * @return Mindig false.
     */
    @Override
    public boolean canCollide() {
        SkeletonManager.call(getSName() + ".canCollide()");
        SkeletonManager.ret("false");
        return false;
    }

    @Override
    public void sufferCollision() {
    }

    /**
     * Interakcióba lép egy épülettel vagy állomással.
     * Átadja magát az épületnek, ami eldönti, hogy mit csinál vele.
     * 
     * @param s Az épület, amivel a hókotró interakcióba lép.
     */
    @Override
    public void interactWithStructure(Structure s) {
        SkeletonManager.call(getSName() + ".interactWithStructure(" + s.getName() + ")");

        s.acceptSnowplow(this);

        SkeletonManager.ret("void");
    }

    /**
     * Elhagyja az aktuális épületet vagy állomást.
     * 
     * @param s Az elhagyandó épület.
     */
    @Override
    public void departFromStructure(Structure s) {
        SkeletonManager.call(getSName() + ".departFromStructure(" + s.getName() + ")");
        s.removeSnowplow(this);
        SkeletonManager.ret("void");
    }

    // Később kelleni fog külön imlplementálni
    // chooseNextRoad()
    // choosenextLane()

    @Override
    public void move() {
        SkeletonManager.call(getSName() + ".move()");

        Road road = chooseNextRoad();
        List<Lane> freeLanes = road.getFreeLanes(this.currentNode);
        Lane nextLane = chooseNextLane(freeLanes);

        boolean isSuccessClean = false;
        if (nextLane != null) {
            isSuccessClean = nextLane.handleVehicle(this);
        }

        if (isSuccessClean) {
            this.claner.receiveMoney(1);
            toNode.enterNode(this);
        }

        SkeletonManager.ret("void");
    }

    @Override
    public void slip() {
    }

    @Override
    public void evaluateCollisions() {
    }

    @Override
    public void stop() {
    }

    @Override
    public void start() {
    }

    @Override
    public void accidentOver() {
    }

    // -------------------------------------------------------------------------------------

    /**
     * Lecseréli a hókotróra felszerelt takarítófejet egy újra.
     * 
     * @param newAttachment Az új felszerelendő fej.
     */
    public void changeAttachment(Attachment newAttachment) {
        SkeletonManager.call(getSName() + ".changeAttachment(" + newAttachment.getName() + ")");
        this.currentAttachment = newAttachment;
        SkeletonManager.ret("void");
    }

    /**
     * Visszaadja a hókotróra aktuálisan felszerelt takarítófejet.
     * 
     * @return Az aktuális fej.
     */
    public Attachment getCurrentAttachment() {
        return currentAttachment;
    }

    /**
     * A hókotró állomáson (SnowplowStation) történő tartózkodáskor lefutó logika.
     * Itt van lehetőség új takarítófej vásárlására és felszerelésére.
     * A Skeletonban most csak hányófejet tud cserélni, azért mert a valós játékban
     * majd felhasználó választhat, most csak a folyamatot mutatja be.
     */
    public void onStation() {
        SkeletonManager.call(getSName() + ".onStation()");
        boolean shop = SkeletonManager.ask("Szeretne-e vásárolni hókotrófejet? ");
        if (shop)
            claner.purchase(new SnowBladeAttachment("snowBladeAttachment", 10));
        SkeletonManager.ret("void");
    }

    // ----------------------------------------------------------------------------------------------------

    /**
     * A takarító megvásárolja a hókotrót.
     * A hókotró eltárolja a takarító referenciáját, hogy később a sikeres
     * takarításért neki adhassa a jutalmat, a takarító pedig felveszi a hókotró
     * listájába.
     * 
     * @param c A vásárlást végző takarító.
     */
    @Override
    public void boughtByCleaner(Cleaner c) {
        SkeletonManager.call(sName + ".boughtByCleaner(" + c.getSName() + ")");

        this.claner = c;
        c.addSnowplow(this);

        SkeletonManager.ret("void");
    }

    /**
     * A buszsofőr általi vásárlás metódusa.
     * 
     * @param b A vásárlást megkísérlő buszsofőr.
     */
    @Override
    public void boughtByBusDriver(BusDriver b) {
    }

}
