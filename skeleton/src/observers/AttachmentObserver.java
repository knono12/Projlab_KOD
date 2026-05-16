package observers;
import model.vehicles.Snowplow;

public interface AttachmentObserver {
    void onEquipmentChanged(Snowplow s);
}
