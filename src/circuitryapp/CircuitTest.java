package circuitryapp;
import circuitryapp.components.*;
//import java.util.*;

public class CircuitTest {
    public static void main(String [] args) {
        Circuit c = new Circuit();
        Battery b = new Battery("Battery",10.0);
        Resistor r1 = new Resistor("res1", 5.0);
        Resistor r2 = new Resistor("res2", 7.0);
        Resistor r3 = new Resistor("res3", 3.0);
        c.addNode(b);
        c.addNode(r1);
        c.addNode(r2);
        c.addNode(r3);
        Node n1 = new Node("node1");
        Node n2 = new Node("node2");
        c.addNode(n1);
        c.addNode(n2);

        b.setOutComp(n1);
        n1.getInParts().add(b);
        n1.getOutParts().add(r1);
        n1.getOutParts().add(r2);

        r1.setInComp(n1);
        r1.setOutComp(n2);
        r2.setInComp(n1);
        r2.setOutComp(n2);

        n2.getInParts().add(r1);
        n2.getInParts().add(r2);
        n2.getOutParts().add(r3);

        r3.setInComp(n2);
        r3.setOutComp(b);
        b.setInComp(r3);

        double totalRes = 0.0;
        totalRes = c.totalResistanceNode();

        /*Wire w1 = new Wire(b,r1);
        b.getOutWires().add(w1);
        Wire w2 = new Wire(r1,r2);
        r1.getOutWires().add(w2);
        Wire w3 = new Wire(r2,r3);
        r2.getOutWires().add(w3);
        Wire w4 = new Wire(r3,b);
        r3.getOutWires().add(w4);
        
        double totalRes = c.totalResistanceWires();*/
        System.out.println("Total Resistance in Circuit: " + totalRes);
    }
}
