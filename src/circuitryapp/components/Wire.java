package circuitryapp.components;

public class Wire extends Component {
    public double current;
    public double voltageChange;
    public Component start;
    public Component end;

    public Wire() {
        super("wire", ComponentType.Wire);
    }

    public void setStart(Component c) {
        start = c;
    }

    public void setEnd(Component c) {
        end = c;
    }

    public Component getStart(){
        return start;
    }
    public Component getEnd(){
        return end;
    }
}
