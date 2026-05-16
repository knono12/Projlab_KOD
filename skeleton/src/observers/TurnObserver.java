package observers;

import model.players.Player;
import model.vehicles.Vehicle;
import model.turn.TurnState;

public interface TurnObserver {
    void onPlayerChanged(Player p);
    void onVehicleChanged(Vehicle v);
    void onStateChanged(TurnState state);
}
