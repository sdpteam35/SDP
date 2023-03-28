package circuitryapp.components;

public abstract class Component {
    public enum ComponentType {
        Battery, Resistor, Conductor, Inductor
    }

    String identifier;
    ComponentType type;

    public Component(String id, ComponentType ctype) {
        this.identifier = id;
        this.type = ctype;
    }
    public ComponentType getType(){
        return type;
    }

}
