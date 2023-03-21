package circuitryapp.components;

public abstract class AbstractComponent {
    enum ComponentType {
        Battery, Resistor, Conductor, Inductor
    }

    String identifier;
    ComponentType type;

    public AbstractComponent(String id, ComponentType ctype) {
        this.identifier = id;
        this.type = ctype;
    }

}
