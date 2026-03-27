package environment.nodes.structures;

import environment.nodes.Node;
import static application.Skeleton.*;
import vehicles.*;

public class SnowplowStation extends Structure {
    String sName;

    Node node;

    public SnowplowStation(String sName, Node node) {
        this.sName = sName;
        this.node = node;
    }

    // Átgondoltam és szerintem nem kell eneternode függvény, mert csak meghívja az acceptValamit aztán beletesszük az épületbe (szerintem)
    // @Override
    // public void enterStructure(Vehicle vehicle) {
    //     call("SnowplowStation.enterStructure(" + vehicle + ")");

    //     ret("");
    // }
    
    // Ugyanez
    // @Override
    // public void leaveStructure(Vehicle vehicle) {
    //     call("SnowplowStation.leaveStructure(" + vehicle + ")");

    //     ret("");
    // }
    
    @Override
    public void acceptSnowplow(Snowplow snowplow) {
        call("SnowplowStation.acceptSnowplow(" + snowplow + ")");

        ret("");
    }

    @Override
    public void removeSnowplow(Snowplow snowplow) {
        call("SnowplowStation.removeSnowplow(" + snowplow + ")");

        ret("");
    }

}
