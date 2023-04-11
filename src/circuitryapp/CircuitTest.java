package circuitryapp;
import circuitryapp.components.*;
//import java.util.*;

public class CircuitTest {
    public static void main(String [] args) {
        Circuit c = new Circuit();
        Battery b = new Battery("Battery",10.0);
        Resistor r1 = new Resistor("res1", 5.0);
        Resistor r2 = new Resistor("res1", 7.0);
        Resistor r3 = new Resistor("res1", 3.0);
        c.addNode(b);
        c.addNode(r1);
        c.addNode(r2);
        c.addNode(r3);
        Wire w1 = new Wire(b,r1);
        b.getOutWires().add(w1);
        Wire w2 = new Wire(r1,r2);
        r1.getOutWires().add(w2);
        Wire w3 = new Wire(r2,r3);
        r2.getOutWires().add(w3);
        Wire w4 = new Wire(r3,b);
        r3.getOutWires().add(w4);
        
        double totalRes = c.traverseGraph();
        System.out.println("Total Resistance in Circuit: " + totalRes);
    }
}
