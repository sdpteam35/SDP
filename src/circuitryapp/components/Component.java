package circuitryapp.components;
import java.util.*;

public abstract class Component {
    public enum ComponentType {
        Battery, Resistor, Conductor, Inductor, Node, Wire
    }

    String identifier;
    ComponentType type;
    public ArrayList<Wire> incomingWires;
    public ArrayList<Wire> outgoingWires;
    public Component inComponent;
    public Component outComponent;

    public Component(String id, ComponentType ctype) {
        this.identifier = id;
        this.type = ctype;
        incomingWires = new ArrayList<Wire>();
        outgoingWires = new ArrayList<Wire>();
    }
    public ComponentType getType(){
        return type;
    }
    public Component getInComp(){
        return inComponent;
    }
    public Component getOutComp(){
        return outComponent;
    }
    public void setInComp(Component nin){
        inComponent = nin;
    }
    public void setOutComp(Component nout){
        outComponent = nout;
    }
    public String getID() {
        return identifier;
    }

    public ArrayList<Wire> getInWires(){
        return incomingWires;
    }
    public ArrayList<Wire> getOutWires(){
        return outgoingWires;
    }
}
