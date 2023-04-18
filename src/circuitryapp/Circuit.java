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
        wires = new ArrayList<Wire>();
    }

    public void addNode(Component o) {
        //adds node
        parts.add(o);
        //updateWires(); // change to circuit, check on wires
    }
    public void addWire(Wire w, Component start, Component end) {
        //start and end are supposed to be Nodes (abstract part class)
        //adds wire (edge) connecting between a start and end part (node)
        //perhaps coordinates on the grid?
        Wire w = new Wire();
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

    public double getTotalVoltage(){
        double totalVoltage = 0;
        for (int i=0; i<parts.size(); ++i){
            //ComponentType ctype = ComponentType.Battery;
            if (parts.get(i).getType().equals(ComponentType.Battery)) {
                Battery bat = (Battery)parts.get(i);
                totalVoltage += bat.getVoltage();
            }
        }
        return totalVoltage;
    }

    public double getTotalResistance(){
        double totalResistance = 0;
        for (int i=0; i<parts.size(); ++i){
            //ComponentType ctype = ComponentType.Battery;
            if (parts.get(i).getType().equals(ComponentType.Resistor)) {
                Resistor res = (Resistor)parts.get(i);
                totalResistance += res.getResistance();
            }
        }
        return totalResistance;
    }
    public double getTotalCurrent(){
        return getTotalVoltage() / getTotalResistance();
    }

    public double totalResistanceNode(){
        double totalResistance = 0;
        int indexOfBattery = findBattery();
        if (indexOfBattery == -1) {
            System.out.println("No battery found in circuit");
            return totalResistance;
        }
        Component startBattery = parts.get(indexOfBattery);
        Component curr = startBattery.getOutComp();
        while (curr != startBattery) {
            System.out.println(curr.getID());
            boolean visitedNode = false; // have we visisted a node
            if (curr.getType().equals(ComponentType.Node)){
                Node ncurr = (Node)curr;
                ncurr.UpdateNode(); // call Node update function - get voltage and resistance
                totalResistance += ncurr.getNodeResistance(); // TotalResistance += incomingResistance();
                curr = ncurr.getOutParts().get(0); // grab first outoging connection
                visitedNode = true; // we've set current 
            }
            /*if (curr.getType().equals(ComponentType.Resistor)){
                Resistor rcurr = (Resistor)curr;
                totalResistance += rcurr.getResistance();
            }*/
            if (visitedNode != true) { // have we set current
                curr = curr.getOutComp();
            }
        }
        // updateNode but for startBattery
        Component currentComponent = startBattery.getInComp();
        while (true) {
            if (currentComponent.getType() == ComponentType.Node) {
                break; 
            }
            if (currentComponent.getType() == ComponentType.Resistor) {
                Resistor r = (Resistor)currentComponent;
                totalResistance += r.getResistance();
            }
            currentComponent = currentComponent.getInComp();
        }
        return totalResistance;
    }

    public void NodeSpace(){

    }




    public double totalResistanceWires() {
        double totalResistance = 0;
        //updateWires replacement
        //step 1: find battery
        int indexOfBattery = findBattery();
        if (indexOfBattery == -1) {
            System.out.println("No battery found in circuit");
            return totalResistance;
        }
        Component startBattery = parts.get(indexOfBattery);
        //step 2, check wires
        for (int i=0; i<startBattery.getOutWires().size(); ++i){
            //start traversing wires
        }
        Wire next = startBattery.getOutWires().get(0);
        Component curr = next.getEnd();
        while (curr != startBattery) {
            if (curr.getType().equals(ComponentType.Resistor)) {
                Resistor rcurr = (Resistor)curr;
                totalResistance += rcurr.getResistance();
            }
            next = curr.getOutWires().get(0);
            curr = next.getEnd();
        }
        return totalResistance;
    }
    public int findBattery() {
        for (int i=0; i<parts.size(); ++i){
            //ComponentType ctype = ComponentType.Battery;
            if (parts.get(i).getType().equals(ComponentType.Battery)) {
                System.out.println(parts.get(i).getType());
                return i;
            }
        }
        return -1;
    }
    
}
