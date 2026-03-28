package environment.nodes.structures;

import environment.nodes.Node;
import skeleton.SkeletonManager;
import vehicles.*;

public class SnowplowStation extends Structure {
    String sName;

    Node node;

    public SnowplowStation(String sName, Node node) {
        this.sName = sName;
        this.node = node;
    }

    // Átgondoltam és szerintem nem kell eneternode függvény, mert csak meghívja az
    // acceptValamit aztán beletesszük az épületbe (szerintem)
    // @Override
    // public void enterStructure(Vehicle vehicle) {
    // call("SnowplowStation.enterStructure(" + vehicle + ")");

    // ret("");
    // }

    // Ugyanez
    // @Override
    // public void leaveStructure(Vehicle vehicle) {
    // call("SnowplowStation.leaveStructure(" + vehicle + ")");

    // ret("");
    // }

    /**
     * Method to accept a snowplow, the station can accept a snowplow when it
     * arrives at the station.
     * 
     * @param snowplow The snowplow to accept at the station.
     */
    @Override
    public void acceptSnowplow(Snowplow snowplow) {
        SkeletonManager.call("SnowplowStation.acceptSnowplow(" + snowplow + ")");

        boolean arrived = SkeletonManager.ask("Does the snnowplow want to enter the station?");
        if (arrived) {
            snowplow.onStation();
        }

        SkeletonManager.ret("");
    }

    /**
     * Method to remove a snowplow from the station, the station can remove a
     * snowplow when it departs from the station.
     * 
     * @param snowplow The snowplow to remove from the station.
     */
    @Override
    public void removeSnowplow(Snowplow snowplow) {
        SkeletonManager.call("SnowplowStation.removeSnowplow(" + snowplow + ")");

        snowplow.departFromStructure(this);

        SkeletonManager.ret("");
    }

}
