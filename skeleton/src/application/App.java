package application;

import model.players.Cleaner;
import skeleton.SkeletonManager;
import view.popups.ShopFrame;
import model.vehicles.Snowplow;

public class App {
    public static void main(String[] args) {
        ShopFrame shopFrame = new ShopFrame(new Cleaner(null, null), new Snowplow(15, null));
        shopFrame.setVisible(true);
    }
}
