package circuitryapp;
import circuitryapp.components.*;
//import java.util.*;

public class CircuitTest {
    public static void main(String [] args) {
        Circuit c = new Circuit();
        Battery b = new Battery("Battery",10.0);
        c.addNode(b);
        c.traverseGraph();
    }
}
