package circuitryapp.components;

public class Wire {
    public double current;
    public double voltageChange;
    public Component start;
    public Component end;

    public Wire(Component  _start, Component _end) {
        start = _start;
        end = _end;
    }

    public Wire(double curr, double vChange, Component  _start, Component _end) {
        current = curr;
        voltageChange = vChange;
        start = _start;
        end = _end;
    }
    public Component getStart(){
        return start;
    }
    public Component getEnd(){
        return end;
    }
}
