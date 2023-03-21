package circuitryapp.components;

public abstract class AbstractComponent {
    String identifier;
    enum ComponentType {
        Battery, Resistor, Conductor, Inductor
    }
    ComponentType type;

    public AbstractComponent(String id, ComponentType ctype) {
        this.identifier = id;
        this.type = ctype;
    }
}
