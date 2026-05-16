package observers;

import model.environment.nodes.Node;
import model.vehicles.Vehicle;

public interface MovementObserver {
    void onVehicleMoved(Vehicle v, Node from, Node to);
}