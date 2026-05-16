package observers;

import model.vehicles.Vehicle;
import model.environment.lane.Lane;

public interface AccidentObserver {
    void onCollisionEvent(Vehicle v, Lane location);
}
