package circuitryapp.components;
import java.util.*;

public abstract class Component {
    public enum ComponentType {
        Battery, Resistor, Conductor, Inductor
    }

    String identifier;
    ComponentType type;
    public ArrayList<Wire> incomingWires;
    public ArrayList<Wire> outgoingWires;

    public Component(String id, ComponentType ctype) {
        this.identifier = id;
        this.type = ctype;
        incomingWires = new ArrayList<Wire>();
        outgoingWires = new ArrayList<Wire>();
    }
    public ComponentType getType(){
        return type;
    }
    public ArrayList<Wire> getInWires(){
        return incomingWires;
    }
    public ArrayList<Wire> getOutWires(){
        return outgoingWires;
    }

}
