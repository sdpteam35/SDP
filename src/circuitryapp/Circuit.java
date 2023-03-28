package circuitryapp;
import circuitryapp.components.*;
import java.util.*;
import circuitryapp.components.Component.ComponentType;

public class Circuit {
    public static void main(String [] args) {
        
    }
    public ArrayList<Component> parts;   
    public ArrayList<Wire> wires;

    public Circuit(){
        parts = new ArrayList<Component>();
    }

    public void addNode(Component o) {
        //adds node
        parts.add(o);
        //updateWires(); // change to circuit, check on wires
    }
    public void addWire(Component start, Component end) {
        //start and end are supposed to be Nodes (abstract part class)
        //adds wire (edge) connecting between a start and end part (node)
        //perhaps coordinates on the grid?
        Wire w = new Wire(start, end);
        wires.add(w);
    }
    public void updateWires() {
        //every time a new node/edge is added, update the math in the wires in the circuit  
        for (Wire w : wires) {
            
        }
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
    public void traverseGraph() {
        //updateWires replacement
        //step 1: find battery
        for (int i=0; i<parts.size(); ++i){
            //ComponentType ctype = ComponentType.Battery;
            if (parts.get(i).getType().equals( ComponentType.Battery)) {
                System.out.println(parts.get(i).getType());
            }
            //System.out.println(parts.get(i).getType());
        }
    }
    
}
