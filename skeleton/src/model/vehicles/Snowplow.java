package model.vehicles;

import java.util.ArrayList;
import java.util.List;

import model.accessories.attachments.Attachment;
import model.accessories.attachments.IceBreakerAttachment;
import model.accessories.attachments.SweeperAttachment;
import model.environment.lane.Lane;
import model.environment.nodes.structures.Structure;
import model.environment.road.Road;
import model.finance.Purchasable;
import model.players.BusDriver;
import model.players.Cleaner;
import observers.AttachmentObserver;
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
    protected Cleaner cleaner;

    protected List<AttachmentObserver> attachmentObservers = new ArrayList<>();

    /**
     * A Snowplow osztály konstruktora.
     * 
     * @param price A hókotró ára.
     * @param sName A hókotró azonosító neve a szkeletonhoz.
     */
    public Snowplow(int price, String sName) {
        super(sName);
        this.price = price;
        currentAttachment = new SweeperAttachment("sweeperAttachment", 5);
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
        s.acceptSnowplow(this);
    }

    /**
     * Elhagyja az aktuális épületet vagy állomást.
     * 
     * @param s Az elhagyandó épület.
     */
    @Override
    public void departFromStructure(Structure s) {
        s.removeSnowplow(this);
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
        if (isActionSuccess) {
            this.cleaner.receiveMoney(50);
            enterNextNode();
        }
        // lehet kell this.nextLana = null;
    }

    @Override
    public void stop() {
    }

    @Override
    public void start() {
    }

    @Override
    public void accidentOverAction() {
    }

    // -------------------------------------------------------------------------------------

    /**
     * Lecseréli a hókotróra felszerelt takarítófejet a létezőre, ha a játékos
     * rendelkezik vele a készletében, vagy egyszerűen felszereli az új fejet, ha
     * nincs még ilyen típusú fej a készletben.
     * 
     * @param newAttachment Az új felszerelendő fej.
     */
    public void changeAttachment(Attachment newAttachment) {
        Purchasable item = cleaner.hasItem(newAttachment);
        if (item != null) {
            this.currentAttachment = (Attachment) item;
        } else {
            this.currentAttachment = newAttachment;
        }
        notifyAttachmentChanged();
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
            cleaner.purchaseItem(new IceBreakerAttachment("iceBrakerAttachment", 10));
        boolean change = SkeletonManager.ask("Szeretne-e cserélni hókotrófejet? ");
        if (change) {
            boolean inInventory = SkeletonManager.ask("Benne van inventory-ban? ");
            if (inInventory)
                changeAttachment(new IceBreakerAttachment("iceBrakerAttachment", 10));
        }

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
        c.addSnowplow(this);
    }

    /**
     * A buszsofőr általi vásárlás metódusa.
     * 
     * @param b A vásárlást megkísérlő buszsofőr.
     */
    @Override
    public void boughtByBusDriver(BusDriver b) {
        // Buszsofőr nem vásárol hókotrót, így ez a metódus üres marad.
    }

    public void addAttachmentObserver(AttachmentObserver o) {
        if (!attachmentObservers.contains(o)) {
            attachmentObservers.add(o);
            o.onEquipmentChanged(this);
        }
    }

    protected void notifyAttachmentChanged() {
        for (AttachmentObserver o : attachmentObservers) {
            o.onEquipmentChanged(this);
        }
    }

}
