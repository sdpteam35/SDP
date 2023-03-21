package circuitryapp;
import circuitryapp.components.*;
import java.util.*;

public class Circuit {
    public static void main(String [] args) {
        
    }
    public ArrayList<String> nodes;
    public ArrayList<Wire> wires;

    public void addNode() {
        //adds node
        updateWires(); // change to circuit, check on wires
    }
    public void addWire(Resistor start, Resistor end) {
        //start and end are supposed to be Nodes (abstract part class)
        //adds wire (edge) connecting between a start and end part (node)
        //perhaps coordinates on the grid?
        Wire w = new Wire(start, end);
        wires.add(w);
    }
    public void updateWires() {
        //every time a new node/edge is added, update the math in the wires in the circuit 
    }
    public boolean isValidCircuit() {
        //true if circuit is valid
        return false;
    }
    public boolean hasConnection() {
        //more of a Node function?
        //checks to see if a node is connected to the circuit
        return false;
    }
    public int getCoordinates() {
        //Get's coords of a node/edge
        return -1;
    }
    
}
