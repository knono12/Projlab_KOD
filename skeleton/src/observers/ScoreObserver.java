package observers;

import model.players.Player;

public interface ScoreObserver {
    void onScoreChanged(Player player, int newScore);
}
