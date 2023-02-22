package circuitryapp;

public class Circuit {
    public static void main(String [] args) {

    }
    public int [] nodes;
    public int [] wires;

    public void addNode() {
        //adds node
    }
    public void addWire(int start, int end) {
        //adds wire (edge) connecting between a start and end part (node)
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
    public int getCoordinates(){
        //Get's coords of a node/edge
        return -1;
    }
    
}
